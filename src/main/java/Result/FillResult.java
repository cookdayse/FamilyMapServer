package Result;

/**
 * Holds information for the result of the fill service
 */
public class FillResult {
    /**
     * error message or "Successfully added X persons and Y events to the database."
     */
    public String message;
    /**
     * informs whether the service succeeded or not
     */
    public boolean success;

    /**
     * Creates a FillResult object
     *
     * @param messageIn error message or "Successfully added X persons and Y events to the database."
     * @param successIn informs whether the service succeeded or not
     */
    public FillResult(String messageIn, boolean successIn) {
        message = messageIn;
        success = successIn;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != FillResult.class) return false;
        if(!((FillResult) obj).message.equals(message)) return false;
        if(((FillResult) obj).success != (success)) return false;
        return true;
    }
}
