package alexgit95.batch.googleLocationsExtractor.repositories;

import alexgit95.batch.googleLocationsExtractor.dao.entities.IgnorePlace;
import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;
import org.springframework.data.repository.CrudRepository;

public interface IgnorePlaceRepository extends CrudRepository<IgnorePlace, String> {
}
