
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location_ {

    @SerializedName("latitudeE7")
    @Expose
    private Integer latitudeE7;
    @SerializedName("longitudeE7")
    @Expose
    private Integer longitudeE7;
    @SerializedName("placeId")
    @Expose
    private String placeId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("semanticType")
    @Expose
    private String semanticType;
    @SerializedName("sourceInfo")
    @Expose
    private SourceInfo__ sourceInfo;
    @SerializedName("locationConfidence")
    @Expose
    private Double locationConfidence;
    @SerializedName("name")
    @Expose
    private String name;

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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(String semanticType) {
        this.semanticType = semanticType;
    }

    public SourceInfo__ getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(SourceInfo__ sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public Double getLocationConfidence() {
        return locationConfidence;
    }

    public void setLocationConfidence(Double locationConfidence) {
        this.locationConfidence = locationConfidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
