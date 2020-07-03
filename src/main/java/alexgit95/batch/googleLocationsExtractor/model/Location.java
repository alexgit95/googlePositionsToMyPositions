
package alexgit95.batch.googleLocationsExtractor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("latitudeE7")
    @Expose
    private Integer latitudeE7;
    @SerializedName("longitudeE7")
    @Expose
    private Integer longitudeE7;
    @SerializedName("accuracyMetres")
    @Expose
    private Integer accuracyMetres;

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

    public Integer getAccuracyMetres() {
        return accuracyMetres;
    }

    public void setAccuracyMetres(Integer accuracyMetres) {
        this.accuracyMetres = accuracyMetres;
    }

}
