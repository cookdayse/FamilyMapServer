package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.List;

/**
 * Used to hold information for LoadService
 */
public class LoadRequest {
    /**
     * list of users
     */
    private List<User> users;
    /**
     * List of people
     */
    private List<Person> persons;
    /**
     * List of events
     */
    private List<Event> events;

    /**
     * Creates a LoadRequest object with all parameters
     *
     * @param usersIn list of users
     * @param people list of people
     * @param eventsIn list of events
     */
    public LoadRequest(List<User> usersIn, List<Person> people, List<Event> eventsIn) {
        users = usersIn;
        persons = people;
        events = eventsIn;
    }

    public List<User> getUsers() { return users; }

    public List<Person> getPersons() { return persons; }

    public List<Event> getEvents() { return events; }
}
