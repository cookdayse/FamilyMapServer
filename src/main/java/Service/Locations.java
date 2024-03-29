package Service;

public class Locations {
    private String country;
    private String city;
    private float latitude;
    private float longitude;
    public Locations(String country, String city, float latitude, float longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public float getLatitude() { return latitude; }
    public float getLongitude() { return longitude; }
}
