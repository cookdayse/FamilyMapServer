package Result;

import java.util.Objects;

/**
 * Used to hold information for the result of the login service
 */
public class LoginResult {
    /**
     * authorization token
     */
    private String authToken;
    /**
     * username
     */
    private String userName;
    /**
     * person id of this username
     */
    private String personID;
    /**
     * tells whether service was successful or not
     */
    private boolean success;
    /**
     * error message
     */
    private String message;

    /**
     * Creates successful LoginResult
     *
     * @param tokenIn authorization token
     * @param nameIn username
     * @param IDIn person id
     */
    public LoginResult(String tokenIn, String nameIn, String IDIn) {
        super();
        authToken = tokenIn;
        userName = nameIn;
        personID = IDIn;
        success = true;
    }

    /**
     * Creates unsuccessful LoginResult
     *
     * @param messageIn error message
     */
    public LoginResult(String messageIn) {
        super();
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
        LoginResult that = (LoginResult) o;
        return success == that.success &&
                Objects.equals(authToken, that.authToken) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(message, that.message);
    }
}
