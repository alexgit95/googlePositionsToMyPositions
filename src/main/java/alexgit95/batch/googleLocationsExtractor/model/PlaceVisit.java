
package alexgit95.batch.googleLocationsExtractor.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceVisit {

    @SerializedName("location")
    @Expose
    private Location_ location;
    @SerializedName("duration")
    @Expose
    private Duration_ duration;
    @SerializedName("placeConfidence")
    @Expose
    private String placeConfidence;
    @SerializedName("centerLatE7")
    @Expose
    private Integer centerLatE7;
    @SerializedName("centerLngE7")
    @Expose
    private Integer centerLngE7;
    @SerializedName("visitConfidence")
    @Expose
    private Integer visitConfidence;
    @SerializedName("otherCandidateLocations")
    @Expose
    private List<OtherCandidateLocation> otherCandidateLocations = null;
    @SerializedName("editConfirmationStatus")
    @Expose
    private String editConfirmationStatus;

    public Location_ getLocation() {
        return location;
    }

    public void setLocation(Location_ location) {
        this.location = location;
    }

    public Duration_ getDuration() {
        return duration;
    }

    public void setDuration(Duration_ duration) {
        this.duration = duration;
    }

    public String getPlaceConfidence() {
        return placeConfidence;
    }

    public void setPlaceConfidence(String placeConfidence) {
        this.placeConfidence = placeConfidence;
    }

    public Integer getCenterLatE7() {
        return centerLatE7;
    }

    public void setCenterLatE7(Integer centerLatE7) {
        this.centerLatE7 = centerLatE7;
    }

    public Integer getCenterLngE7() {
        return centerLngE7;
    }

    public void setCenterLngE7(Integer centerLngE7) {
        this.centerLngE7 = centerLngE7;
    }

    public Integer getVisitConfidence() {
        return visitConfidence;
    }

    public void setVisitConfidence(Integer visitConfidence) {
        this.visitConfidence = visitConfidence;
    }

    public List<OtherCandidateLocation> getOtherCandidateLocations() {
        return otherCandidateLocations;
    }

    public void setOtherCandidateLocations(List<OtherCandidateLocation> otherCandidateLocations) {
        this.otherCandidateLocations = otherCandidateLocations;
    }

    public String getEditConfirmationStatus() {
        return editConfirmationStatus;
    }

    public void setEditConfirmationStatus(String editConfirmationStatus) {
        this.editConfirmationStatus = editConfirmationStatus;
    }

}
