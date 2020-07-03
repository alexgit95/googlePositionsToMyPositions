
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndLocation {

    @SerializedName("latitudeE7")
    @Expose
    private Integer latitudeE7;
    @SerializedName("longitudeE7")
    @Expose
    private Integer longitudeE7;
    @SerializedName("sourceInfo")
    @Expose
    private SourceInfo_ sourceInfo;

    public Integer getLatitudeE7() {
        return latitudeE7;
    }

    public void setLatitudeE7(Integer latitudeE7) {
        this.latitudeE7 = latitudeE7;
    }

    public Integer getLongitudeE7() {
        return longitudeE7;
    }

    public void setLongitudeE7(Integer longitudeE7) {
        this.longitudeE7 = longitudeE7;
    }

    public SourceInfo_ getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(SourceInfo_ sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

}
