package Request;

/**
 * Used to hold information for a fill request
 */
public class FillRequest {
    /**
     * username
     */
    private String username;
    /**
     * number of generations, defaults to 4 when not specified by user
     */
    private int generations;

    /**
     * Creates a FillRequest object with just username
     *
     * @param userIn username in
     */
    public FillRequest(String userIn) {
        username = userIn;
        generations = 4;
    }

    /**
     * Creates a FillRequest object with username and number of generations
     *
     * @param userIn username
     * @param genIn number of generations
     */
    public FillRequest(String userIn, int genIn) {
        this(userIn);
        generations = genIn;
    }

    public String getUsername() { return username; }

    public int getGenerations() { return generations; }
}
