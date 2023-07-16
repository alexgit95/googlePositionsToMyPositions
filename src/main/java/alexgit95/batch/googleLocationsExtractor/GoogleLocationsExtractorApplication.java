package alexgit95.batch.googleLocationsExtractor;

import java.util.LinkedList;
import java.util.List;

import alexgit95.batch.googleLocationsExtractor.services.OrchestratorServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;

@SpringBootApplication
public class GoogleLocationsExtractorApplication {

	@Autowired
	private OrchestratorServices orchestratorServices;

	private Logger logger = LoggerFactory.getLogger(GoogleLocationsExtractorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GoogleLocationsExtractorApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			List<LocationsOutput> result = new LinkedList<LocationsOutput>();

			//Recupere la liste des ignores places à ajouter dans la base des ignore places
			orchestratorServices.getIgnorePlaceFromFile();

			//Rceupere toutes les endrots qui sont deja stocké en BDD
			orchestratorServices.loadExistingPlace();


			//Recupere tous les endroits depuis l'historique google
			// en ne prenant pas ceux qui existe deja à une date donnee
			orchestratorServices.processGoogleFiles(result);

			//Sauvegarde des nouveau elements
			orchestratorServices.saveAll(result);

			orchestratorServices.generateAllLocationJson();
			logger.info("Fin du programme");
		};
	}




}
