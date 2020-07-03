
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineObject {

    @SerializedName("activitySegment")
    @Expose
    private ActivitySegment activitySegment;
    @SerializedName("placeVisit")
    @Expose
    private PlaceVisit placeVisit;

    public ActivitySegment getActivitySegment() {
        return activitySegment;
    }

    public void setActivitySegment(ActivitySegment activitySegment) {
        this.activitySegment = activitySegment;
    }

    public PlaceVisit getPlaceVisit() {
        return placeVisit;
    }

    public void setPlaceVisit(PlaceVisit placeVisit) {
        this.placeVisit = placeVisit;
    }

}
