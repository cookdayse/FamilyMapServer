package Result;

import java.util.Objects;

/**
 * Used to hold information for the result of the register service
 */
public class RegisterResult {
    /**
     * authorization token, if applicable
     */
    private String authToken;
    /**
     * username, if applicable
     */
    private String userName;
    /**
     * person id, if applicable
     */
    private String personID;
    /**
     * informs whether or not request was successful
     */
    private boolean success;
    /**
     * if request was not successful, message to indicate problem
     */
    private String message;

    /**
     * Creates a successful RegisterResult
     *
     * @param authTokenIn authorization token
     * @param usernameIn  username
     * @param personIDIn  person id
     */
    public RegisterResult(String authTokenIn, String usernameIn, String personIDIn) {
        authToken = authTokenIn;
        userName = usernameIn;
        personID = personIDIn;
        success = true;
    }

    /**
     * Creates an unsuccessful RegisterResult
     *
     * @param messageIn error message
     */
    public RegisterResult(String messageIn) {
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
        RegisterResult that = (RegisterResult) o;
        return success == that.success &&
                Objects.equals(authToken, that.authToken) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(message, that.message);
    }
}
