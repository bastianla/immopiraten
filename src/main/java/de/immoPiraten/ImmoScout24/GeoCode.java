package de.immoPiraten.ImmoScout24;

public class GeoCode {

	private String latitude;
	private String longitude;

	public GeoCode() {
	}

	public GeoCode(String latitude, String longitude) {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
