
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Duration {

    @SerializedName("startTimestamp")
    @Expose
    private String startTimestampMs;
    @SerializedName("endTimestamp")
    @Expose
    private String endTimestampMs;

    public String getStartTimestampMs() {
        return startTimestampMs;
    }

    public void setStartTimestampMs(String startTimestampMs) {
        this.startTimestampMs = startTimestampMs;
    }

    public String getEndTimestampMs() {
        return endTimestampMs;
    }

    public void setEndTimestampMs(String endTimestampMs) {
        this.endTimestampMs = endTimestampMs;
    }

}
