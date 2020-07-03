
package alexgit95.batch.googleLocationsExtractor.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaypointPath {

    @SerializedName("waypoints")
    @Expose
    private List<Waypoint> waypoints = null;

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

}
