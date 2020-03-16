package Result;

import Model.Person;

import java.util.List;
import java.util.Objects;

/**
 * used to store information for result of /person url path
 */
public class PersonsResult {
    /**
     * list of people
     */
    private List<Person> data;
    /**
     * indicates success of result
     */
    private boolean success;
    /**
     * error message, if needed
     */
    private String message;

    /**
     * creates a successful PersonsResult
     *
     * @param list list of people
     */
    public PersonsResult(List<Person> list) {
        data = list;
        success = true;
    }

    /**
     * creates an unsuccessful PersonsResult
     *
     * @param messageIn error message
     */
    public PersonsResult(String messageIn) {
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
        PersonsResult that = (PersonsResult) o;
        return success == that.success &&
                Objects.equals(data, that.data) &&
                Objects.equals(message, that.message);
    }
}
