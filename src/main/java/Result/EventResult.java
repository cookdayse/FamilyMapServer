package Result;

import java.util.Objects;

/**
 * holds information for return of EventService event(String id)
 */
public class EventResult {
    /**
     * username
     */
    private String associatedUsername;
    /**
     * event
     */
    private String eventID;
    /**
     * person
     */
    private String personID;
    /**
     * latitude
     */
    private float latitude;
    /**
     * longitude
     */
    private float longitude;
    /**
     * country
     */
    private String country;
    /**
     * city
     */
    private String city;
    /**
     * event type
     */
    private String eventType;
    /**
     * year
     */
    private int year;
    /**
     * indicates success of result
     */
    private boolean success;
    /**
     * error message, if needed
     */
    private String message;

    /**
     * creates a successful EventResult
     *
     * @param userIn user
     * @param eventIn event
     * @param personIn person
     * @param lat latitude
     * @param lon longitude
     * @param countryIn country
     * @param cityIn city
     * @param eventTypeIn event type
     * @param yearIn year
     */
    public EventResult(String userIn, String eventIn, String personIn, float lat, float lon, String countryIn, String cityIn, String eventTypeIn, int yearIn) {
        associatedUsername = userIn;
        eventID = eventIn;
        personID = personIn;
        latitude = lat;
        longitude = lon;
        country = countryIn;
        city = cityIn;
        eventType = eventTypeIn;
        year = yearIn;
        success = true;
        message = null;
    }

    /**
     * creates an unsuccessful EventResult
     *
     * @param message error message
     */
    public EventResult(String message) {
        super();
        this.message = message;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResult result = (EventResult) o;
        return Float.compare(result.latitude, latitude) == 0 &&
                Float.compare(result.longitude, longitude) == 0 &&
                year == result.year &&
                success == result.success &&
                Objects.equals(associatedUsername, result.associatedUsername) &&
                Objects.equals(eventID, result.eventID) &&
                Objects.equals(personID, result.personID) &&
                Objects.equals(country, result.country) &&
                Objects.equals(city, result.city) &&
                Objects.equals(eventType, result.eventType) &&
                Objects.equals(message, result.message);
    }
}
