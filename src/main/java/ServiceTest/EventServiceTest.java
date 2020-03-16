package ServiceTest;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.User;
import Result.EventResult;
import Result.EventResults;
import Service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    Database db;
    Event Event;
    Event mom;
    User user;
    Event nonRelated;
    AuthToken authToken;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        Event = new Event("nono", "lovely", "nananabooboo", 10, 12,
                "love", "london", "birth", 2012);
        user = new User("nananabooboo", "lovely", "yup", "nope", "lo",
                "well", "m");
        mom = new Event("momo", "lovely", "ve", 16, 14,
                "Endland", "nowhere", "death", 2100);
        nonRelated = new Event("yup", "beaurigul", "beau", 1999, 6969,
                "party", "like", "marriage", 420);
        authToken = new AuthToken("lovely", "iden");
        Connection conn = db.openConnection();
        EventDao p = new EventDao(conn);
        UserDao u = new UserDao(conn);
        AuthTokenDao a = new AuthTokenDao(conn);
        u.addUser(user);
        p.addEvent(Event);
        p.addEvent(nonRelated);
        p.addEvent(mom);
        a.addAuthToken(authToken);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void EventWithIDPass() throws Exception {
        EventResult eventResult = null;
        try {
            eventResult = new EventService().event("momo", "iden");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(eventResult);
        assertTrue(eventResult.isSuccess());
    }

    @Test
    public void EventWithIDFail() throws Exception {
        EventResult eventResult = null;
        EventResult result = new EventResult("error: Requested event does not belong to this user");
        try {
            eventResult = new EventService().event("yup","iden");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(eventResult);
        assertFalse(eventResult.isSuccess());
        assertEquals(result, eventResult);
    }

    @Test
    public void EventPass() throws Exception {
        EventResults result = null;
        try {
            result = new EventService().event("iden");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void EventFail() throws Exception {
        EventResults result = null;
        EventResults results = new EventResults("error: Invalid auth token");
        try {
            result = new EventService().event("lovely");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals(results, result);
    }
}
