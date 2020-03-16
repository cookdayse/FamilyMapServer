package ServiceTest;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    LoadRequest r;
    Database db;
    User user;
    Person person;
    Event event;
    LoadRequest q;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        user = new User("blah", "notuser", "pass", "email", "me", "cook",
                "m");
        person = new Person("blah", "user", "me", "cook", "m",
                null, null, null);
        event = new Event("this", "user", "blah", 1, 2, "england",
                "london", "Birth", 2020);

        List<User> users = Arrays.asList(new User("me", "user", "no", "not", "haha", "idiot", "m"));
        List<Person> people = Arrays.asList(new Person("me", "user", "haha", "idiot", "m", null, null, null));
        List<Event> events = Arrays.asList(new Event("event", "user", "me", 60, 20, "why", "here", "birth", 2019));
        r = new LoadRequest(users,people, events);

        List<Event> events1 = Arrays.asList(new Event("eeeeee", "used", "bleh", 4040, 12, "no", "no", "nah", 2020));
        q = new LoadRequest(users, people, events1);

        Connection conn = db.openConnection();
        db.clearTables();
        new UserDao(conn).addUser(user);
        PersonDao pDao = new PersonDao(conn);
        pDao.addPerson(person);
        new EventDao(conn).addEvent(event);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loadPass() throws Exception {
        LoadResult res = null;
        LoadResult compare = new LoadResult("Successfully added 1 users, 1 persons, and 1 events to the database.", true);
        try {
            res = new LoadService().load(r);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(res);
        assertEquals(compare, res);

        //make sure the previous things were cleared from the database
        Person p = null;
        User u = null;
        Event ev = null;
        try {
            Connection conn = db.openConnection();
            p = new PersonDao(conn).getPerson(person.getID());
            u = new UserDao(conn).getUser(user.getAssociatedUsername());
            ev = new EventDao(conn).getEvent(event.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNull(p);
        assertNull(u);
        assertNull(ev);
    }

    @Test
    public void loadFail() throws Exception {
        LoadResult res = null;
        LoadResult compare = new LoadResult("error: Invalid latitude", false);
        try {
            res = new LoadService().load(q);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(res);
        assertEquals(res, compare);
    }
}
