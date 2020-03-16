package DataAccess;

import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User bestUser;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestUser = new User("whydoIcare", "gamer420", "Me", "Stupid", "my", "name", "f");
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        User compareTest = null;

        try {
            //open a connection and make a new dao
            Connection conn = db.openConnection();
            UserDao eDao = new UserDao(conn);
            //insert a person
            eDao.addUser(bestUser);
            //get back that person, put it into a new person object
            compareTest = eDao.getUser(bestUser.getAssociatedUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //if we have something, something was put into our database
        assertNotNull(compareTest);
        //make sure that something was what we actually put into our database
        assertEquals(compareTest,bestUser);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try{
            Connection conn = db.openConnection();
            UserDao eDao = new UserDao(conn);
            eDao.addUser(bestUser);
            //try and add it again, and it should throw an exception
            eDao.addUser(bestUser);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        //to make sure both events we tried to add are not in the database,
        //first set compareTest to an actual person object
        User compareTest = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDao eDao = new UserDao(conn);
            //then try to find it in my database (this should return null)
            compareTest = eDao.getUser(bestUser.getPerson());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void retrievePass() throws Exception {
        User compare = null;
        try{
            Connection conn = db.openConnection();
            UserDao pDao = new UserDao(conn);
            pDao.addUser(bestUser);
            compare = pDao.getUser(bestUser.getAssociatedUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(compare, bestUser);
    }

    @Test
    public void retrieveFail() throws Exception {
        User compare = bestUser;
        boolean didItWork = false;
        try{
            Connection conn = db.openConnection();
            UserDao pDao = new UserDao(conn);
            //bestPerson is not in the database, so this should return null
            compare = pDao.getUser(bestUser.getPerson());
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
        User check = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDao pDao = new UserDao(conn);
            //add a person to the database table
            pDao.addUser(bestUser);
            //then clear the database table
            pDao.clearUsers();
            //if clear failed, this person would still be in the table
            check = pDao.getUser(bestUser.getPerson());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }

    @Test
    public void deletePass() throws Exception {
        User check = bestUser;
        try {
            Connection conn = db.openConnection();
            UserDao pDao = new UserDao(conn);
            //add a person to the database table
            pDao.addUser(bestUser);
            //then delete this person
            pDao.deleteUser(bestUser.getPerson());
            //if delete failed, this person would still be in the table
            check = pDao.getUser(bestUser.getPerson());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }
}
