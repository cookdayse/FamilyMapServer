package Request;

/**
 * Used to hold information for the initialization of the register service
 */
public class RegisterRequest {
    /**
     * username
     */
    private String userName;
    /**
     * password
     */
    private String password;
    /**
     * email
     */
    private String email;
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
     * Creates a RegisterRequest object with all parameters
     *
     * @param userIn username
     * @param passwordIn password
     * @param emailIn email
     * @param firstNameIn first name
     * @param lastNameIn last name
     * @param genderIn gender, either 'm' or 'f'
     */
    public RegisterRequest(String userIn, String passwordIn, String emailIn, String firstNameIn, String lastNameIn, String genderIn) {
        userName = userIn;
        password = passwordIn;
        email = emailIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        gender = genderIn;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
