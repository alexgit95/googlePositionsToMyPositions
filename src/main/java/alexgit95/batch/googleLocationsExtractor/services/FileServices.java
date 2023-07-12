package alexgit95.batch.googleLocationsExtractor.services;

import alexgit95.batch.googleLocationsExtractor.dao.entities.LocationsOutput;

import java.io.File;
import java.util.List;

public interface FileServices {
    void processFile(File src, List<LocationsOutput> result);

    void saveAll(List<LocationsOutput> result);

    void loadExistingPlace();

    void getIgnorePlaceFromFile();
}
