package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;
import java.util.List;

/**
 * Used to interact with the Daos to clear all data and then loads posted user, person and even data into the database
 */
public class LoadService {
    /**
     * Clears the database and loads posted material into the database
     *
     * @param r request
     * @return result of the request
     */
    public LoadResult load(LoadRequest r) throws DataAccessException {
        Database db = new Database();
        LoadResult out = null;
        try {
            Connection conn = db.openConnection();
            UserDao u = new UserDao(conn);
            PersonDao p = new PersonDao(conn);
            AuthTokenDao a = new AuthTokenDao(conn);
            EventDao e = new EventDao(conn);

            u.clearUsers();
            p.clearPeople();
            a.clearAuthTokens();
            e.clearEvents();

            List<Event> events = r.getEvents();
            List<User> users = r.getUsers();
            List<Person> people = r.getPersons();
            errorCheckEvents(events);
            errorCheckPeople(people);
            errorCheckUsers(users);

            int eventNum = 0;
            int userNum = 0;
            int personNum = 0;

            for(Event event: events) {
                e.addEvent(event);
                eventNum += 1;
            }
            for(User user: users) {
                u.addUser(user);
                userNum += 1;
            }
            for(Person person: people) {
                p.addPerson(person);
                personNum += 1;
            }
            String message = "Successfully added " + Integer.toString(userNum) + " users, " + Integer.toString(personNum) +
                    " persons, and " + Integer.toString(eventNum) + " events to the database.";
            out = new LoadResult(message, true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            out = new LoadResult(e.getMessage(), false);
        }
        return out;
    }

    //Error checks!
    public void errorCheckEvents(List<Event> events) throws DataAccessException {
        for(Event event: events) {
            if(event.getAssociatedUsername() == null) {
                throw new DataAccessException("error: missing username");
            }
            if(event.getID() == null) {
                throw new DataAccessException("error: missing event id");
            }
            if(event.getPerson() == null) {
                throw new DataAccessException("error: missing person id");
            }
            if(event.getLatitude() > 90 || event.getLatitude() < -90) {
                throw new DataAccessException("error: Invalid latitude");
            }
            if(event.getLongitude() < -180 || event.getLongitude() > 180) {
                throw new DataAccessException("error: Invalid longitude");
            }
            if(event.getCountry() == null) {
                throw new DataAccessException("error: missing country");
            }
            if(event.getCity() == null) {
                throw new DataAccessException("error: missing city");
            }
            if(event.getEventType() == null) {
                throw new DataAccessException("error: missing event type");
            }
            if(event.getYear() > 2020) {
                throw new DataAccessException("error: Invalid year, no one can have events in the future.");
            }
        }
    }
    public void errorCheckUsers(List<User> users) throws DataAccessException {
        for(User user: users) {
            if(user.getPerson() == null) {
                throw new DataAccessException("error: missing person id");
            }
            if(user.getAssociatedUsername() == null) {
                throw new DataAccessException("error: missing username");
            }
            if(user.getPassword() == null) {
                throw new DataAccessException("error: missing password");
            }
            if(user.getEmail() == null) {
                throw new DataAccessException("error: missing email");
            }
            if(user.getFirstName() == null) {
                throw new DataAccessException("error: missing first name");
            }
            if(user.getLastName() == null) {
                throw new DataAccessException("error: missing last name");
            }
            if(user.getGender() == null) {
                throw new DataAccessException("error: missing gender");
            }
            if(!user.getGender().equals("m") && !user.getGender().equals("f")) {
                throw new DataAccessException("error: invalid gender input (not that your gender is invalid, we just only have two inputs allowed :)");
            }
        }
    }
    public void errorCheckPeople(List<Person> people) throws DataAccessException {
            for(Person person: people) {
                if(person.getAssociatedUsername() == null) {
                    throw new DataAccessException("error: missing username");
                }
                if(person.getID() == null) {
                    throw new DataAccessException("error: missing event id");
                }
                if(person.getFirstName() == null) {
                    throw new DataAccessException("error: missing person id");
                }
                if(person.getLastName() == null) {
                    throw new DataAccessException("error: missing last name");
                }
                if(person.getGender() == null) {
                    throw new DataAccessException("error: missing gender");
                }
                if(!person.getGender().equals("m") && !person.getGender().equals("f")) {
                    throw new DataAccessException("error: invalid gender input (not that your gender is invalid, we just only have two inputs allowed :)");
                }
            }
    }
}
