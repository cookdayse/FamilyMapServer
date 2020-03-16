package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.Event;
import Model.Person;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    RegisterRequest r;
    Database db;
    @BeforeEach
    public void setUp() throws Exception {
        r = new RegisterRequest("lovely", "whydo", "me@gmail.com", "bob", "check", "m");
        db = new Database();
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void registerPass() throws Exception {
        RegisterResult p = null;
        boolean good = true;
        try {
            p = new RegisterService().register(r);
        } catch (DataAccessException e) {
            e.printStackTrace();
            good = false;
        }
        assertNotNull(p);
        assertTrue(p.isSuccess());
        ArrayList<Person> people = null;
        ArrayList<Event> events = null;
        try {
            Connection conn = db.openConnection();
            PersonDao x = new PersonDao(conn);
            people = x.getPeople("lovely");
            EventDao e = new EventDao(conn);
            events = e.getEvents("lovely");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
        assertTrue(people.size() == 31);
        assertTrue(events.size() == 91);
    }

    @Test
    public void registerFail() throws Exception {
        RegisterResult q = null;
        boolean good = false;
        RegisterResult p = new RegisterResult("error: Username already taken by another user");
        try {
            new RegisterService().register(r);
            q = new RegisterService().register(r);
        } catch (DataAccessException e) {
            e.printStackTrace();
            good = true;
        }
        assertNotNull(q);
        assertFalse(q.isSuccess());
        assertEquals(q,p);
    }
}
