
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Waypoint {

    @SerializedName("latE7")
    @Expose
    private Integer latE7;
    @SerializedName("lngE7")
    @Expose
    private Integer lngE7;

    public Integer getLatE7() {
        return latE7;
    }

    public void setLatE7(Integer latE7) {
        this.latE7 = latE7;
    }

    public Integer getLngE7() {
        return lngE7;
    }

    public void setLngE7(Integer lngE7) {
        this.lngE7 = lngE7;
    }

}
