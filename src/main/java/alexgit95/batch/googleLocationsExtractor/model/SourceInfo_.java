
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SourceInfo_ {

    @SerializedName("deviceTag")
    @Expose
    private Integer deviceTag;

    public Integer getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(Integer deviceTag) {
        this.deviceTag = deviceTag;
    }

}
