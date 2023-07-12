package alexgit95.batch.googleLocationsExtractor.dao.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@DynamoDBTable(tableName="IgnorePlaces")
@Entity
public class IgnorePlace {

	@Column
	private double lattitude;
	@Column
	private double longitude;
	@Id
	@Column
	private String libelle;
	

	@DynamoDBAttribute(attributeName="lattitude")
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	@DynamoDBAttribute(attributeName="longitude")
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLibelle() {
		return libelle;
	}
	
	@DynamoDBAttribute(attributeName="libelle")
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public IgnorePlace(double lattitude, double longitude, String libelle) {
		super();
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.libelle = libelle;
	}
	public IgnorePlace() {
		super();
	}
	@Override
	public String toString() {
		return "IgnorePlace [lattitude=" + lattitude + ", longitude=" + longitude + ", libelle="
				+ libelle + "]";
	}
	
	

}