package alexgit95.batch.googleLocationsExtractor.services.impl;

import alexgit95.batch.googleLocationsExtractor.dao.entities.IgnorePlace;
import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;
import alexgit95.batch.googleLocationsExtractor.model.*;
import alexgit95.batch.googleLocationsExtractor.dao.DaoServices;
import alexgit95.batch.googleLocationsExtractor.services.OrchestratorServices;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class OrchestratorServicesImpl implements OrchestratorServices {
    public static List<Point> ignorePlacesList;

    private static Set<Date> existingDates = new TreeSet<Date>();


    @Value("${radiusIgnoreZone}")
    private int radiusIgnoreZone;

    @Value("${ignoreFilePath}")
    private String ignoreFilePath;
    @Value("${sourceFolderPath}")
    public File srcFolder;



    @Autowired
    @Qualifier("LocalDB")
    private DaoServices daoServices;

    private Logger logger = LoggerFactory.getLogger(OrchestratorServicesImpl.class);

@Override
    public void processGoogleFiles(List<LocationsOutput> result) {

        Collection<File> listFiles = FileUtils.listFiles(srcFolder, new String[] { "json" }, true);
        for (File file : listFiles) {
            processFile(file, result);
        }
    }
    @Override
    public void processFile(final File src, List<LocationsOutput> result) {
        try {

            logger.debug("Lecture du fichier " + src.getAbsolutePath());
            String fileContent = FileUtils.readFileToString(src, Charset.defaultCharset());
            Gson gson = new Gson();
            LocationsInput fromJson = gson.fromJson(fileContent, LocationsInput.class);
            logger.debug("Fichier Chargé, nb ligne : " + fromJson.getTimelineObjects().size());
            for (TimelineObject input : fromJson.getTimelineObjects()) {
                if (input.getPlaceVisit() != null && input.getPlaceVisit().getLocation().getName() != null&&input.getPlaceVisit().getLocation().getLatitudeE7() != null&&input.getPlaceVisit().getLocation().getAddress()!=null) {
                    LocationsOutput output = outputBuilder(input);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(output.getBegin());
                    cal.add(Calendar.MINUTE, 30);

                    if (output.getEnd().after(cal.getTime())
                            && !shouldBeIgnore(output.getLattitude(), output.getLongitude())
                            && !existingDates.contains(output.getBegin())) {
                        result.add(output);

                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Erreur lors de la lecture du fichier source", e);
        }
    }






    public void getIgnorePlaceFromFile() {
        File toRead = new File(ignoreFilePath);
        if(!toRead.exists()) {
            logger.info("Pas de fichier ignore path ici {}", ignoreFilePath);
            return;
        }
        try {
            List<String> ignoreLines = FileUtils.readLines(toRead, Charset.defaultCharset());
            for (String line : ignoreLines){
                if(!line.isEmpty()) {
                    String[] split = line.split(";");

                    logger.info("Depuis le fichier ignore on charge :{}", split[0]);

                    IgnorePlace igToAdd = new IgnorePlace();
                    igToAdd.setLattitude(Double.parseDouble(split[1]));
                    igToAdd.setLongitude(Double.parseDouble(split[2]));
                    igToAdd.setLibelle(split[0]);
                    daoServices.addIgnorePlace(igToAdd);
                }

            }
            logger.info("NB Ignore places : {}",ignoreLines.size());

        }catch(Exception e){
            logger.error("Erreur lors de la recuperation des ignore place depuis le fichier ", e);
        }
    }



    @Override
    public void loadExistingPlace() {
        logger.info("Chargement des date existantes");
        List<LocationsOutput> all = daoServices.getAll();

        logger.info("all size {}", all.size());
        for (LocationsOutput location : all) {
            existingDates.add(location.getBegin());
        }

        logger.info("Fin du chargement : " + existingDates.size() + " elements");

    }



    @Override
    public void saveAll(List<LocationsOutput> result) {
        logger.info("NB Ajout total : " + result.size());

        for (int i = 0; i < result.size(); i++) {

            if(result.get(i).getId()==null) {
                result.get(i).setId(UUID.randomUUID().toString());
            }
            logger.debug("Sauvegarde " + i + "/" + result.size());
            daoServices.save(result.get(i));
        }

        logger.info("Sauvegarde effectuee");
    }

    private boolean shouldBeIgnore(double lat, double longi) {

        List<Point> doNotTrackList = getIgnorePlaceFromBDD();
        for (Point point : doNotTrackList) {
            double distance = distance(((double) point.getLatE7() / 10000000), lat,
                    ((double) point.getLngE7() / 10000000), longi);

            if (distance < radiusIgnoreZone) {
                return true;
            }

        }
        return false;
    }

    private static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    //Mapping entre la sserialisation de l'objet au format google et la forme stockée en BDD
    private LocationsOutput outputBuilder(TimelineObject input) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        logger.debug(input.getPlaceVisit().getLocation().getAddress());

        LocationsOutput output = new LocationsOutput();
        try {
            output.setAddress(input.getPlaceVisit().getLocation().getAddress().replaceAll("\\n", " "));
            output.setName(input.getPlaceVisit().getLocation().getName());
            output.setBegin(sdf.parse(input.getPlaceVisit().getDuration().getStartTimestampMs().substring(0, 19)));
            output.setEnd(sdf.parse(input.getPlaceVisit().getDuration().getEndTimestampMs().substring(0, 19)));
            output.setLattitude(((double) input.getPlaceVisit().getLocation().getLatitudeE7() / 10000000));
            output.setLongitude(((double) input.getPlaceVisit().getLocation().getLongitudeE7() / 10000000));

        }catch(ParseException pe){
            logger.error("Erreur parsing", pe);
        }
        return output;

    }

    private List<Point> getIgnorePlaceFromBDD() {
        if (ignorePlacesList == null) {


            logger.debug("Initialisation de la do not track list");
            ignorePlacesList = new ArrayList<Point>();


            List<IgnorePlace> allIgnorePlaces = daoServices.getAllIgnorePlaces();
            for (IgnorePlace ignorePlace : allIgnorePlaces) {
                logger.info("Depuis la base de données on ignore : {}", ignorePlace.getLibelle());
                Point toAdd = new Point();
                toAdd.setLatE7((int) (ignorePlace.getLattitude() * 10000000));
                toAdd.setLngE7((int) (ignorePlace.getLongitude() * 10000000));
                ignorePlacesList.add(toAdd);
            }
            logger.info(ignorePlacesList.size()+" lieu seront ignore");

            return ignorePlacesList;

        } else {
            return ignorePlacesList;
        }
    }
}
