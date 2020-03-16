package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;
import Result.EventResults;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * interacts with the Dao to find events
 */
public class EventService {
    /**
     * finds a single event
     *
     * @param idIn id of the event
     * @param check AuthToken of the current user
     * @return the event
     */
    public EventResult event(String idIn, String check) throws DataAccessException {
        Database db = new Database();
        EventResult out = null;
        try {
            Connection conn = db.openConnection();
            EventDao e = new EventDao(conn);
            AuthTokenDao a = new AuthTokenDao(conn);
            AuthToken auth = a.getAuthToken(check);
            if(auth == null) {
                throw new DataAccessException("error: Invalid auth token");
            }
            Event event = e.getEvent(idIn);
            if(event == null) {
                throw new DataAccessException("error: Invalid eventID parameter");
            }
            if(!event.getAssociatedUsername().equals(auth.getUser())) {
                throw new DataAccessException("error: Requested event does not belong to this user");
            }
            out = new EventResult(event.getID(), event.getAssociatedUsername(), event.getPerson(),
                    event.getLatitude(), event.getLongitude(), event.getCountry(),
                    event.getCity(), event.getEventType(), event.getYear());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            out = new EventResult(e.getMessage());
            db.closeConnection(false);
        }
       return out;
    }

    /**
     * finds all events related to this user
     *
     * @param check Authtoken of current user
     * @return the list of events
     */
    public EventResults event(String check) throws DataAccessException {
        Database db = new Database();
        EventResults out = null;
        try {
            Connection conn = db.openConnection();
            EventDao e = new EventDao(conn);
            AuthTokenDao a = new AuthTokenDao(conn);
            AuthToken auth = a.getAuthToken(check);
            if(auth == null) {
                throw new DataAccessException("error: Invalid auth token");
            }
            ArrayList<Event> events = e.getEvents(auth.getUser());
            out = new EventResults(events);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            out = new EventResults(e.getMessage());
            db.closeConnection(false);
        }
        return out;
    }
}
