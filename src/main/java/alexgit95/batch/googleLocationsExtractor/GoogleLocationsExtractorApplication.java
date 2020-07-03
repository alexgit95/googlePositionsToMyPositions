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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;

import alexgit95.batch.googleLocationsExtractor.model.LocationsInput;
import alexgit95.batch.googleLocationsExtractor.model.LocationsOutput;
import alexgit95.batch.googleLocationsExtractor.model.Point;
import alexgit95.batch.googleLocationsExtractor.model.TimelineObject;
import alexgit95.batch.googleLocationsExtractor.services.DaoServices;

@SpringBootApplication
public class GoogleLocationsExtractorApplication {
	
	public static List<Point> doNoTrackList;
	
	@Value("${ignoreFilePath}")
	public File ignoreFile;
	
	@Value("${sourceFolderPath}")
	public File srcFolder;
	@Autowired
	private DaoServices daoServices;
	
	private Set<Date> existingDates = new TreeSet<Date>();

	public static void main(String[] args) {
		SpringApplication.run(GoogleLocationsExtractorApplication.class, args);
		
		
		
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			List<LocationsOutput> result = new LinkedList<LocationsOutput>();
			
			System.out.println("Chargement des date existantes");
			loadExistingPlace();
			System.out.println("Fin du chargement : "+existingDates.size()+" elements");
			
			Collection<File> listFiles = FileUtils.listFiles(srcFolder, new String[] {"json"}, true);
			for (File file : listFiles) {
				processFile(file, result);
			}
			
			System.out.println("NB Ajout total : " + result.size());
			for(int i=0;i<result.size();i++) {
				System.out.println("Sauvegarde "+i+"/"+result.size());
				daoServices.save(result.get(i));
			}
			
			System.out.println("Sauvegarde effectuee");
		};
	}


	private  void processFile(final File src, List<LocationsOutput> result) {
		try {

			System.out.println("Lecture du fichier "+src.getAbsolutePath());
			String fileContent = FileUtils.readFileToString(src, Charset.defaultCharset());
			Gson gson = new Gson();
			LocationsInput fromJson = gson.fromJson(fileContent, LocationsInput.class);
			System.out.println("Fichier Chargé, nb ligne : " + fromJson.getTimelineObjects().size());
			for (TimelineObject input : fromJson.getTimelineObjects()) {
				if (input.getPlaceVisit() != null) {
					if (input.getPlaceVisit().getLocation().getName() != null) {
						LocationsOutput output = new LocationsOutput();
						output.setAddress(input.getPlaceVisit().getLocation().getAddress().replaceAll("\\n", " "));
						output.setName(input.getPlaceVisit().getLocation().getName());
						output.setBegin(
								new Date(Long.parseLong(input.getPlaceVisit().getDuration().getStartTimestampMs())));
						output.setEnd(
								new Date(Long.parseLong(input.getPlaceVisit().getDuration().getEndTimestampMs())));

						if(input.getPlaceVisit().getLocation().getLatitudeE7()==null||input.getPlaceVisit().getLocation().getLongitudeE7()==null) {
							continue;
						}
						output.setLattitude(((double) input.getPlaceVisit().getLocation().getLatitudeE7() / 10000000));
						output.setLongitude(((double) input.getPlaceVisit().getLocation().getLongitudeE7() / 10000000));
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(output.getBegin());
						cal.add(Calendar.MINUTE, 30);

						if (output.getEnd().after(cal.getTime())
								&& !shouldBeIgnore(output.getLattitude(), output.getLongitude())&&!existingDates.contains(output.getBegin())) {
							result.add(output);
							
						}

					}
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  boolean shouldBeIgnore(double lat, double longi) {
		final int distanceApprox = 500;
		List<Point> doNotTrackList = getDoNotTrackList();
		for (Point point : doNotTrackList) {
			double distance = distance(((double) point.getLatE7() / 10000000), lat,
					((double) point.getLngE7() / 10000000), longi);
			if (distance < distanceApprox) {
				return true;
			}

		}
		return false;
	}

	public  List<Point> getDoNotTrackList() {
		if(doNoTrackList==null) {
			try {
				System.out.println("Initialisation de la do not track list");
				List<String> readLines = FileUtils.readLines(ignoreFile,Charset.defaultCharset());
				doNoTrackList=new ArrayList<Point>();
				for (String line : readLines) {
					String[] split = line.split(",");
					Point toAdd=new Point();
					int latE7 = (int)(Double.parseDouble(split[0])*10000000);
					toAdd.setLatE7(latE7);
					int lngE7 = (int)(Double.parseDouble(split[1])*10000000);
					toAdd.setLngE7(lngE7);
					doNoTrackList.add(toAdd);
				}
			
				return doNoTrackList;
			} catch (IOException e) {
				System.out.println("Fichier ignore specifie : "+ignoreFile.getAbsolutePath());
				e.printStackTrace();
				System.out.println("Liste ignoree par defaut : vide");
				doNoTrackList=new ArrayList<Point>();
				return doNoTrackList;
			}
		}else {
			return doNoTrackList;
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
	
	private void loadExistingPlace() {
		List<LocationsOutput> all = daoServices.getAll();
		
		for (LocationsOutput location : all) {
			existingDates.add(location.getBegin());
		}
	}
	
	

}
