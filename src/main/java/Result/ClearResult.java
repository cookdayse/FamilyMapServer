package Result;

import java.util.Objects;

/**
 * Used to hold information for the result of clear service
 */
public class ClearResult {
    /**
     * error message or "Clear succeeded."
     */
    private String message;
    /**
     * used to determine whether clear succeeded or not
     */
    private boolean success;

    /**
     * Creates a ClearResult object
     *
     * @param messageIn error message or "Clear succeeded."
     * @param successIn indicates whether clear succeeded or not
     */
    public ClearResult(String messageIn, boolean successIn) {
        message = messageIn;
        success = successIn;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearResult that = (ClearResult) o;
        return success == that.success &&
                Objects.equals(message, that.message);
    }
}
