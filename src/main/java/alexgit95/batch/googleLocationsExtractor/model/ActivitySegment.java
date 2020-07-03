
package alexgit95.batch.googleLocationsExtractor.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivitySegment {

    @SerializedName("startLocation")
    @Expose
    private StartLocation startLocation;
    @SerializedName("endLocation")
    @Expose
    private EndLocation endLocation;
    @SerializedName("duration")
    @Expose
    private Duration duration;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("activityType")
    @Expose
    private String activityType;
    @SerializedName("confidence")
    @Expose
    private String confidence;
    @SerializedName("activities")
    @Expose
    private List<Activity> activities = null;
    @SerializedName("waypointPath")
    @Expose
    private WaypointPath waypointPath;
    @SerializedName("simplifiedRawPath")
    @Expose
    private SimplifiedRawPath simplifiedRawPath;
    @SerializedName("parkingEvent")
    @Expose
    private ParkingEvent parkingEvent;

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public WaypointPath getWaypointPath() {
        return waypointPath;
    }

    public void setWaypointPath(WaypointPath waypointPath) {
        this.waypointPath = waypointPath;
    }

    public SimplifiedRawPath getSimplifiedRawPath() {
        return simplifiedRawPath;
    }

    public void setSimplifiedRawPath(SimplifiedRawPath simplifiedRawPath) {
        this.simplifiedRawPath = simplifiedRawPath;
    }

    public ParkingEvent getParkingEvent() {
        return parkingEvent;
    }

    public void setParkingEvent(ParkingEvent parkingEvent) {
        this.parkingEvent = parkingEvent;
    }

}
