package Model;

/**
 * An authorization token.
 */
public class AuthToken {
    /**
     * Who the token is being used by.
     */
    private String user;
    /**
     * What the token is.
     */
    private String token;

    /**
     * creates a AuthToken object with all parameters
     *
     * @param userIn who the token is being used by.
     * @param iden what the token is.
     */
    public AuthToken(String userIn, String iden) {
        user = userIn;
        token = iden;
    }

    public String getUser() {
        return user;
    }

    public String getToken() { return token; }
    /**
     * Checks to see if a string passed in is equivalent to the token
     *
     * @param checkIn string to be checked
     * @return true if equal, false if not
     */
    public boolean checkIdentification(String checkIn) {
        if(token.equals(checkIn)) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if(!obj.getClass().equals(AuthToken.class)) return false;
        if(!((AuthToken) obj).getUser().equals(user)) return false;
        if(!((AuthToken) obj).getToken().equals(token)) return false;
        return true;
    }
}
