package Result;

import java.util.Objects;

/**
 * Holds information for the return of the LoadService
 */
public class LoadResult {
    /**
     * message of the result
     */
    private String message;
    /**
     * indicates whether successful or not
     */
    private boolean success;

    /**
     * Creates a LoadResult object with all parameters
     *
     * @param messageIn error or success message
     * @param successIn true or false
     */
    public LoadResult(String messageIn, boolean successIn) {
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
        LoadResult that = (LoadResult) o;
        return success == that.success &&
                Objects.equals(message, that.message);
    }
}
