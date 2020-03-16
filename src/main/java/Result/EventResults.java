package Result;

import Model.Event;

import java.util.List;
import java.util.Objects;

/**
 * used to hold information for the result of the EventService event()
 */
public class EventResults {
    private List<Event> data;
    private boolean success;
    private String message;

    /**
     * creates a successful EventResults object
     *
     * @param in list of events
     */
    public EventResults(List<Event> in) {
        data = in;
        success = true;
        message = null;
    }

    /**
     * creates an unsuccessful EventResults object
     *
     * @param in error message
     */
    public EventResults(String in) {
        message = in;
        success = false;
        data = null;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResults results = (EventResults) o;
        return success == results.success &&
                Objects.equals(data, results.data) &&
                Objects.equals(message, results.message);
    }
}
