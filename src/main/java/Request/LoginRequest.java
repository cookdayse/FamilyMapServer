package Request;

/**
 * Used to initialize login service
 */
public class LoginRequest {
    /**
     * username of request
     */
    private String userName;
    /**
     * password of request
     */
    private String password;

    /**
     * Creates a LoginRequest with all parameters set
     *
     * @param username username of request
     * @param passWord password of request
     */
    public LoginRequest(String username, String passWord) {
        userName = username;
        password = passWord;
    }

    public String getUserName() { return userName; }

    public String getPassword() { return password; }
}
