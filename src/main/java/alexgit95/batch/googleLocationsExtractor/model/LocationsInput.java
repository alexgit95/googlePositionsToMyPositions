
package alexgit95.batch.googleLocationsExtractor.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationsInput {

    @SerializedName("timelineObjects")
    @Expose
    private List<TimelineObject> timelineObjects = null;

    public List<TimelineObject> getTimelineObjects() {
        return timelineObjects;
    }

    public void setTimelineObjects(List<TimelineObject> timelineObjects) {
        this.timelineObjects = timelineObjects;
    }

}
