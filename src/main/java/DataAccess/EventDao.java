package DataAccess;

import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Used to access the event table in the database.
 */
public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds event to the database, and sets the event id.
     *
     * @param event event to be added.
     */
    public void addEvent(Event event) throws DataAccessException{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO event (id, user_id, person_id, latitude, longitude, " +
                "country, city, event_type, year) VALUES(?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPerson());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Deletes the event.
     *
     * @param eventIn the id of the event to delete.
     */
    public boolean deleteEvent(String eventIn) throws DataAccessException {
        String sql = "DELETE FROM event WHERE id = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventIn);
            if(stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting a person");
        }
        return false;
    }

    /**
     * clears event table in database
     */
    public void clearEvents() throws DataAccessException {
        String sql = "DELETE FROM event;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all people");
        }
    }

    public Event getEvent(String eventIn) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventIn);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("id"), rs.getString("user_id"),
                        rs.getString("person_id"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("event_type"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public ArrayList<Event> getEvents(String userIn) throws DataAccessException {
        ArrayList<Event> event = new ArrayList<Event>();
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE user_id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userIn);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event.add(new Event(rs.getString("id"), rs.getString("user_id"),
                        rs.getString("person_id"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("event_type"),
                        rs.getInt("year")));
            }
            return event;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public ArrayList<Event> getPersonEvents(String personIn) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<Event>();
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE person_id = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personIn);
            rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getString("id"), rs.getString("user_id"),
                        rs.getString("person_id"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("event_type"),
                        rs.getInt("year")));
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding events");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
