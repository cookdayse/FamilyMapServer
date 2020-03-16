package Model;

/**
 * An event.
 */

public class Event {
    /**
     * AssociatedUsername associated with this event.
     */
    String associatedUsername;
    /**
     * Unique id.
     */
    String eventID;
    /**
     * Person associated with this event.
     */
    String personID;
    /**
     * Latitude of the event.
     */
    float latitude;
    /**
     * Longitude of the event.
     */
    float longitude;
    /**
     * Country of the event.
     */
    String country;
    /**
     * City of the event.
     */
    String city;
    /**
     * Type of the event.
     */
    String eventType;
    /**
     * Year of the event.
     */
    int year;

    /**
     * Creates an Event object with all parameters
     *
     * @param idIn unique id
     * @param AssociatedUsernameIn AssociatedUsername associated with this event
     * @param personIn person associated with this event
     * @param latitudeIn latitude of this event
     * @param longitudeIn longitude of this event
     * @param countryIn country of this event
     * @param cityIn city of this event
     * @param event_typeIn event type
     * @param yearIn year of this event
     */
    public Event(String idIn, String AssociatedUsernameIn, String personIn, float latitudeIn, float longitudeIn, String countryIn, String cityIn, String event_typeIn, int yearIn) {
        eventID = idIn;
        associatedUsername = AssociatedUsernameIn;
        personID = personIn;
        latitude = latitudeIn;
        longitude = longitudeIn;
        country = countryIn;
        city = cityIn;
        eventType = event_typeIn;
        year = yearIn;
    }

    public String getID() { return eventID; }

    public String getAssociatedUsername() { return  associatedUsername; }

    public String getPerson() { return personID; }

    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude; }

    public String getCountry() { return country; }

    public String getCity() { return city; }

    public String getEventType() { return eventType; }

    public int getYear() { return year; }

    public boolean equals(Object obj) {
        if(!obj.getClass().equals(Event.class)) return false;
        if(!((Event) obj).getID().equals(eventID)) return false;
        if(!((Event) obj).getAssociatedUsername().equals(associatedUsername)) return false;
        if(!((Event) obj).getPerson().equals(personID)) return false;
        if(((Event) obj).getLatitude() != (latitude)) return false;
        if(((Event) obj).getLongitude() != (longitude)) return false;
        if(!((Event) obj).getCountry().equals(country)) return false;
        if(!((Event) obj).getCity().equals(city)) return false;
        if(!((Event) obj).getEventType().equals(eventType)) return false;
        if(((Event) obj).getYear() != (year)) return false;
        return true;
    }
}
