package ServiceTest;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;
import Service.FillService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    FillRequest r;
    FillRequest q;
    User u;
    Database db;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        r = new FillRequest("lovely",2);
        q = new FillRequest("lovely", 5);
        u = new User("yup", "lovely", "klsaj;", "asl;kdf", "lksdj", "kl","m");
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void fillPass() throws Exception {
        FillResult f = null;
        FillResult g = new FillResult("Successfully added 7 persons and 19 events to the database.", true);
        try {
            Connection conn = db.openConnection();
            UserDao user = new UserDao(conn);
            user.addUser(u);
            db.closeConnection(true);

            f = new FillService().fill(r);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(f);
        assertEquals(g,f);
        ArrayList<Person> people = null;
        ArrayList<Event> events = null;
        try {
            Connection conn = db.openConnection();
            PersonDao p = new PersonDao(conn);
            people = p.getPeople("lovely");
            EventDao e = new EventDao(conn);
            events = e.getEvents("lovely");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertTrue(people.size() == 7);
        assertTrue(events.size() == 19);
    }

    @Test
    public void fillPassBig() throws Exception {
        FillResult f = null;
        FillResult g = new FillResult("Successfully added 63 persons and 187 events to the database.", true);
        try {
            Connection conn = db.openConnection();
            UserDao user = new UserDao(conn);
            user.addUser(u);
            db.closeConnection(true);

            f = new FillService().fill(q);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(f);
        assertEquals(g,f);
        ArrayList<Person> people = null;
        ArrayList<Event> events = null;
        try {
            Connection conn = db.openConnection();
            PersonDao p = new PersonDao(conn);
            people = p.getPeople("lovely");
            EventDao e = new EventDao(conn);
            events = e.getEvents("lovely");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertTrue(people.size() == 63);
        assertTrue(events.size() == 187);
    }

    @Test
    public void fillFail() throws Exception {
        FillResult f = null;
        FillResult g = new FillResult("error: Invalid username", false);
        boolean good = true;
        try {
            //try to fill for an invalid username
            f = new FillService().fill(q);
        } catch (DataAccessException e) {
            e.printStackTrace();
            good = false;
        }
        assertNotNull(f);
        assertFalse(f.success);
        assertTrue(good);
        assertEquals(f,g);

        f = null;
        g = new FillResult("error: Invalid generations parameter", false);
        FillRequest x = new FillRequest("lovely", -1);
        try {
            Connection conn = db.openConnection();
            UserDao user = new UserDao(conn);
            user.addUser(u);
            db.closeConnection(true);

            f = new FillService().fill(x);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertNotNull(f);
        assertEquals(f,g);
    }
}
