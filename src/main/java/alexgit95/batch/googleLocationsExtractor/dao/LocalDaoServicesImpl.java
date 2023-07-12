package alexgit95.batch.googleLocationsExtractor.dao;

import alexgit95.batch.googleLocationsExtractor.dao.entities.IgnorePlace;
import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;
import alexgit95.batch.googleLocationsExtractor.model.Point;
import alexgit95.batch.googleLocationsExtractor.repositories.IgnorePlaceRepository;
import alexgit95.batch.googleLocationsExtractor.repositories.LocationOutputRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("LocalDB")
public class LocalDaoServicesImpl implements DaoServices {

	private Logger logger = LoggerFactory.getLogger(LocalDaoServicesImpl.class);



	@Autowired
	private LocationOutputRepository repository;

	@Autowired
	private IgnorePlaceRepository ignorePlaceRepository;



	@Override
	public void save(LocationsOutput toCreate) {
		repository.save(toCreate);
	}
	@Override
	public List<LocationsOutput> getAll() {
		List<LocationsOutput> result = new ArrayList<LocationsOutput>();

		logger.debug("entree getAll");
		Iterable<LocationsOutput> all = repository.findAll();

		for(LocationsOutput temp : all){
			result.add(temp);
		}


		logger.debug("sortie getAll");
		return result;
		
	}
	
	@Override
	public List<IgnorePlace> getAllIgnorePlaces() {
		List<IgnorePlace> result = new ArrayList<>();
		Iterable<IgnorePlace> all = ignorePlaceRepository.findAll();
		for(IgnorePlace temp : all){
			result.add(temp);
		}
		return result;
	}

	@Override
	public void addIgnorePlace(IgnorePlace toadd) {
		ignorePlaceRepository.save(toadd);
	}

}
