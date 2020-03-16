package Result;

import java.util.Objects;

/**
 * Holds information for the result of the PersonService
 */
public class PersonResult {
    /**
     * username
     */
    private String associatedUsername;
    /**
     * person
     */
    private String personID;
    /**
     * first name
     */
    private String firstName;
    /**
     * last name
     */
    private String lastName;
    /**
     * gender
     */
    private String gender;
    /**
     * father (OPTIONAL)
     */
    private String fatherID;
    /**
     * mother (OPTIONAL)
     */
    private String motherID;
    /**
     * spouse (OPTIONAL)
     */
    private String spouseID;
    /**
     * tells whether successful or not
     */
    private boolean success;
    /**
     * error message (OPTIONAL)
     */
    private String message;

    /**
     * Creates a successful PersonResult without father, mother or spouse IDs
     *
     * @param userIn username
     * @param personIn person
     * @param firstIn first name
     * @param lastIn last name
     * @param genderIn gender
     */
    public PersonResult(String userIn, String personIn, String firstIn, String lastIn, String genderIn, String fatherIn, String motherIn, String spouseIn) {
        associatedUsername = userIn;
        personID = personIn;
        firstName = firstIn;
        lastName = lastIn;
        gender = genderIn;
        fatherID = fatherIn;
        motherID = motherIn;
        spouseID = spouseIn;
        success = true;
    }
    /**
     * Creates an unsuccessful PersonResult
     *
     * @param messageIn error message
     */
    public PersonResult(String messageIn) {
        message = messageIn;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResult that = (PersonResult) o;
        return success == that.success &&
                Objects.equals(associatedUsername, that.associatedUsername) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(fatherID, that.fatherID) &&
                Objects.equals(motherID, that.motherID) &&
                Objects.equals(spouseID, that.spouseID) &&
                Objects.equals(message, that.message);
    }
}
