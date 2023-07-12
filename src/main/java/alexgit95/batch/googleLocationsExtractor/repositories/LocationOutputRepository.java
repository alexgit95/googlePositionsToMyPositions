package alexgit95.batch.googleLocationsExtractor.repositories;

import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;
import org.springframework.data.repository.CrudRepository;


public interface LocationOutputRepository extends CrudRepository<LocationsOutput, Long> {
}
