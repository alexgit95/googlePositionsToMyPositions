package alexgit95.batch.googleLocationsExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;

import alexgit95.batch.googleLocationsExtractor.model.IgnorePlace;
import alexgit95.batch.googleLocationsExtractor.model.LocationsInput;
import alexgit95.batch.googleLocationsExtractor.model.LocationsOutput;
import alexgit95.batch.googleLocationsExtractor.model.Point;
import alexgit95.batch.googleLocationsExtractor.model.TimelineObject;
import alexgit95.batch.googleLocationsExtractor.services.DaoServices;

@SpringBootApplication
public class GoogleLocationsExtractorApplication {

	public static List<Point> doNoTrackList;

	@Value("${sourceFolderPath}")
	public File srcFolder;
	@Autowired
	private DaoServices daoServices;
	
	@Value("${radiusIgnoreZone}")
	private int radiusIgnoreZone;

	private Set<Date> existingDates = new TreeSet<Date>();

	private Logger logger = LoggerFactory.getLogger(GoogleLocationsExtractorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GoogleLocationsExtractorApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			List<LocationsOutput> result = new LinkedList<LocationsOutput>();

			logger.info("Chargement des date existantes");
			loadExistingPlace();
			logger.info("Fin du chargement : " + existingDates.size() + " elements");

			Collection<File> listFiles = FileUtils.listFiles(srcFolder, new String[] { "json" }, true);
			for (File file : listFiles) {
				processFile(file, result);
			}

			logger.info("NB Ajout total : " + result.size());
			for (int i = 0; i < result.size(); i++) {
				logger.debug("Sauvegarde " + i + "/" + result.size());
				daoServices.save(result.get(i));
			}

			logger.info("Sauvegarde effectuee");
			logger.info("Fin du programme");
		};
	}

	private void processFile(final File src, List<LocationsOutput> result) {
		try {

			logger.debug("Lecture du fichier " + src.getAbsolutePath());
			String fileContent = FileUtils.readFileToString(src, Charset.defaultCharset());
			Gson gson = new Gson();
			LocationsInput fromJson = gson.fromJson(fileContent, LocationsInput.class);
			logger.debug("Fichier Chargé, nb ligne : " + fromJson.getTimelineObjects().size());
			for (TimelineObject input : fromJson.getTimelineObjects()) {
				if (input.getPlaceVisit() != null && input.getPlaceVisit().getLocation().getName() != null&&input.getPlaceVisit().getLocation().getLatitudeE7() != null) {

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

	private LocationsOutput outputBuilder(TimelineObject input) {
		LocationsOutput output = new LocationsOutput();
		output.setAddress(input.getPlaceVisit().getLocation().getAddress().replaceAll("\\n", " "));
		output.setName(input.getPlaceVisit().getLocation().getName());
		output.setBegin(new Date(Long.parseLong(input.getPlaceVisit().getDuration().getStartTimestampMs())));
		output.setEnd(new Date(Long.parseLong(input.getPlaceVisit().getDuration().getEndTimestampMs())));
		output.setLattitude(((double) input.getPlaceVisit().getLocation().getLatitudeE7() / 10000000));
		output.setLongitude(((double) input.getPlaceVisit().getLocation().getLongitudeE7() / 10000000));

		return output;

	}

	private boolean shouldBeIgnore(double lat, double longi) {
		
		List<Point> doNotTrackList = getDoNotTrackList();
		for (Point point : doNotTrackList) {
			double distance = distance(((double) point.getLatE7() / 10000000), lat,
					((double) point.getLngE7() / 10000000), longi);
			if (distance < radiusIgnoreZone) {
				return true;
			}

		}
		return false;
	}

	private List<Point> getDoNotTrackList() {
		if (doNoTrackList == null) {
		
				
				logger.debug("Initialisation de la do not track list");
				doNoTrackList = new ArrayList<Point>();
				
				
				List<IgnorePlace> allIgnorePlaces = daoServices.getAllIgnorePlaces();
				for (IgnorePlace ignorePlace : allIgnorePlaces) {
					Point toAdd = new Point();
					toAdd.setLatE7((int) (ignorePlace.getLattitude() * 10000000));
					toAdd.setLngE7((int) (ignorePlace.getLongitude() * 10000000));
					doNoTrackList.add(toAdd);
				}
				logger.info(doNoTrackList.size()+" lieu seront ignore");
				
				return doNoTrackList;
			
		} else {
			return doNoTrackList;
		}
	}
	
	private void loadExistingPlace() {
		List<LocationsOutput> all = daoServices.getAll();

		for (LocationsOutput location : all) {
			existingDates.add(location.getBegin());
		}
	}

	public static double distance(double lat1, double lat2, double lon1, double lon2) {
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

	public void setDaoServices(DaoServices daoServices) {
		this.daoServices = daoServices;
	}



}
