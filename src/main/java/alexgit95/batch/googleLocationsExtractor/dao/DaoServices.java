package alexgit95.batch.googleLocationsExtractor.dao;

import java.util.List;

import alexgit95.batch.googleLocationsExtractor.dao.entities.IgnorePlace;
import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;
import alexgit95.batch.googleLocationsExtractor.model.Point;

public interface DaoServices {
	
	public void save(LocationsOutput toCreate);
	
	public List<LocationsOutput> getAll();
	
	List<IgnorePlace> getAllIgnorePlaces();

	void addIgnorePlace(IgnorePlace toadd);

}
