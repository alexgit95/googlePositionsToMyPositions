package alexgit95.batch.googleLocationsExtractor;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import alexgit95.batch.googleLocationsExtractor.services.FileServices;
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

import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;

@SpringBootApplication
public class GoogleLocationsExtractorApplication {



	@Value("${sourceFolderPath}")
	public File srcFolder;

	@Autowired
	private FileServices fileServices;





	private Logger logger = LoggerFactory.getLogger(GoogleLocationsExtractorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GoogleLocationsExtractorApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			List<LocationsOutput> result = new LinkedList<LocationsOutput>();

			fileServices.getIgnorePlaceFromFile();

			fileServices.loadExistingPlace();


			Collection<File> listFiles = FileUtils.listFiles(srcFolder, new String[] { "json" }, true);
			for (File file : listFiles) {
				fileServices.processFile(file, result);
			}

			fileServices.saveAll(result);
			logger.info("Fin du programme");
		};
	}




}
