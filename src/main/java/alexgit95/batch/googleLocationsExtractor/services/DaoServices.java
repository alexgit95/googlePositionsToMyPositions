package alexgit95.batch.googleLocationsExtractor.services;

import java.util.List;

import alexgit95.batch.googleLocationsExtractor.model.LocationsOutput;

public interface DaoServices {
	
	public void save(LocationsOutput toCreate);
	
	public List<LocationsOutput> getAll();

}
