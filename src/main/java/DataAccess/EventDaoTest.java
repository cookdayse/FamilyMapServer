package DataAccess;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventDaoTest {
    private Database db;
    private Event bestEvent;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestEvent = new Event("whydoIcare", "gamer420", "Me", 5, 12, "name", "f", "birth", 2020);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        Event compareTest = null;

        try {
            //open a connection and make a new dao
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //insert a person
            eDao.addEvent(bestEvent);
            //get back that person, put it into a new person object
            compareTest = eDao.getEvent(bestEvent.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //if we have something, something was put into our database
        assertNotNull(compareTest);
        //make sure that something was what we actually put into our database
        assertEquals(compareTest,bestEvent);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try{
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.addEvent(bestEvent);
            //try and add it again, and it should throw an exception
            eDao.addEvent(bestEvent);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        //to make sure both events we tried to add are not in the database,
        //first set compareTest to an actual person object
        Event compareTest = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            //then try to find it in my database (this should return null)
            compareTest = eDao.getEvent(bestEvent.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void retrievePass() throws Exception {
        Event compare = null;
        try{
            Connection conn = db.openConnection();
            EventDao pDao = new EventDao(conn);
            pDao.addEvent(bestEvent);
            compare = pDao.getEvent(bestEvent.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(compare, bestEvent);
    }

    @Test
    public void retrieveFail() throws Exception {
        Event compare = bestEvent;
        boolean didItWork = false;
        try{
            Connection conn = db.openConnection();
            EventDao pDao = new EventDao(conn);
            //bestPerson is not in the database, so this should return null
            compare = pDao.getEvent(bestEvent.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            didItWork = true;
            db.closeConnection(false);
        }
        //it should not have thrown an error
        assertFalse(didItWork);
        //and it should have set compare to null
        assertNull(compare);
    }

    @Test
    public void clearPass() throws Exception {
        Event check = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDao pDao = new EventDao(conn);
            //add a person to the database table
            pDao.addEvent(bestEvent);
            //then clear the database table
            pDao.clearEvents();
            //if clear failed, this person would still be in the table
            check = pDao.getEvent(bestEvent.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }

    @Test
    public void deletePass() throws Exception {
        Event check = bestEvent;
        try {
            Connection conn = db.openConnection();
            EventDao pDao = new EventDao(conn);
            //add a person to the database table
            pDao.addEvent(bestEvent);
            //then delete this person
            pDao.deleteEvent(bestEvent.getID());
            //if delete failed, this person would still be in the table
            check = pDao.getEvent(bestEvent.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }

    @Test
    public void getEventsPass() throws Exception {
        Event death = new Event("a;ldj;a", "gamer420", "notme", 20, 43, "england", "london", "death", 420);
        Event otherEvent = new Event("al;skj", "Notg420", "Me", 33, 42, "not", "It", "mawwiage", 6969);
        ArrayList<Event> events = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.addEvent(bestEvent);
            eDao.addEvent(death);
            eDao.addEvent(otherEvent);
            events = eDao.getEvents(bestEvent.getAssociatedUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        boolean good = true;
        if(!events.contains(death)) { good = false; }
        if(!events.contains(bestEvent)) { good = false; }
        if(events.contains(otherEvent)) { good = false; }
        assertTrue(good);
    }

    @Test
    public void getEventsFail() throws Exception {
        boolean good = true;
        Event death = new Event("a;ldj;a", "gamer420", "notme", 20, 43, "england", "london", "death", 420);
        Event otherEvent = new Event("al;skj", "Notg420", "Me", 33, 42, "not", "It", "mawwiage", 6969);
        ArrayList<Event> events = null;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.addEvent(bestEvent);
            eDao.addEvent(death);
            eDao.addEvent(otherEvent);
            events = eDao.getEvents("a");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            good = false;
        }
        if(events.size() != 0) good = false;
        assertTrue(good);
    }

    @Test
    public void getPersonEventsPass() throws Exception {
        boolean good = true;
        Event death = new Event("a;ldj;a", "gamer420", "notme", 20, 43, "england", "london", "death", 420);
        Event otherEvent = new Event("alla", "Notg420", "Me", 33, 42, "not", "It", "mawwiage", 6969);
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.addEvent(bestEvent);
            eDao.addEvent(death);
            eDao.addEvent(otherEvent);
            events = eDao.getPersonEvents(otherEvent.getPerson());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            good = false;
        }
        if(events.size() != 2) good = false;
        if(!events.contains(otherEvent)) good = false;
        if(events.contains(death)) good = false;
        if(!events.contains(bestEvent)) good = false;
        assertTrue(good);
    }

    @Test
    public void getPersonEventsFail() throws Exception {
        boolean good = true;
        Event death = new Event("a;ldj;a", "gamer420", "notme", 20, 43, "england", "london", "death", 420);
        Event otherEvent = new Event("a;j;a", "Notg420", "Me", 33, 42, "not", "It", "mawwiage", 6969);
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.addEvent(bestEvent);
            eDao.addEvent(death);
            eDao.addEvent(otherEvent);
            //no exceptions should be thrown, the EventDao will just return an empty ArrayList
            events = eDao.getPersonEvents("a");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        if(events.size() != 0) good = false;
        assertTrue(good);
    }
}
