package Model;

/**
 * A Person.
 */
public class Person {
    /**
     * The associatedUsername personID of this person.
     */
    private String associatedUsername;
    /**
     * The unique personID for this person.
     */
    private String personID;
    /**
     * The first name.
     */
    private String firstName;
    /**
     * The last name.
     */
    private String lastName;
    /**
     * The gender.
     */
    private String gender;
    /**
     * fatherID, can be null.
     */
    private String fatherID;
    /**
     * motherID, can be null.
     */
    private String motherID;
    /**
     * The spouseID, can be null.
     */
    private String spouseID;


    /**
     * Creates a person object with the spouseID, motherID and fatherID null, but all other parameters set
     *
     * @param personIDIn unique personID
     * @param associatedUsernameIn unique associatedUsername personID
     * @param firstNameIn first name
     * @param lastNameIn last name
     * @param genderIn gender, either 'm' or 'f'
     */
    public Person(String personIDIn, String associatedUsernameIn, String firstNameIn, String lastNameIn, String genderIn, String fatherIDIn, String motherIDIn, String spouseIDIn) {
        personID = personIDIn;
        associatedUsername = associatedUsernameIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        gender = genderIn;
        spouseID = spouseIDIn;
        motherID = motherIDIn;
        fatherID = fatherIDIn;
    }


    public String getID() { return personID; }

    public String getAssociatedUsername() { return associatedUsername; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return  lastName; }

    public String getGender() { return gender; }

    public String getFatherID() { return fatherID; }

    public void setFatherID(String fatherIDIn) { fatherID = fatherIDIn; }

    public String getMotherID() { return motherID; }

    public void setMotherID(String motherIDIn) { motherID = motherIDIn; }

    public String getSpouseID() { return spouseID; }

    public void setSpouseID(String spouseIDIn) { spouseID = spouseIDIn; }

    public boolean equals(Object objectIn) {
        //if it's not a person, these aren't equal
        if(objectIn.getClass() != Person.class) { return false; }
        //if any of the information in it doesn't match, these aren't equal
        if(!((Person) objectIn).getID().equals(personID)) return false;
        if(!((Person) objectIn).getAssociatedUsername().equals(associatedUsername)) return false;
        if(!((Person) objectIn).getFirstName().equals(firstName)) return false;
        if(!((Person) objectIn).getLastName().equals(lastName)) return false;
        if(!((Person) objectIn).getGender().equals(gender)) return false;
        if(spouseID == null) {
            if(((Person) objectIn).getSpouseID() != null) return false;
        } else {
            if (((Person) objectIn).getSpouseID() == null) return false;
            if(!((Person) objectIn).getSpouseID().equals(spouseID)) return false;
        }
        if(fatherID == null) {
            if(((Person) objectIn).getFatherID() != null) return false;
        } else {
            if(((Person) objectIn).getFatherID() == null) return false;
            if(!((Person) objectIn).getFatherID().equals(fatherID)) return false;
        }
        if(motherID == null) {
            if(((Person) objectIn).getMotherID() != null) return false;
        } else {
            if(((Person) objectIn).getMotherID() == null) return false;
            if(!((Person) objectIn).getMotherID().equals(motherID)) return false;
        }
        //but otherwise, they are equal!
        return true;
    }
}