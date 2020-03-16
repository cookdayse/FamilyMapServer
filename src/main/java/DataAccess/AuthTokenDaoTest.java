package DataAccess;

import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {
    private Database db;
    private AuthToken bestAuthToken;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestAuthToken = new AuthToken("whydoIcare", "gamer420");
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        AuthToken compareTest = null;

        try {
            //open a connection and make a new dao
            Connection conn = db.openConnection();
            AuthTokenDao eDao = new AuthTokenDao(conn);
            //insert a person
            eDao.addAuthToken(bestAuthToken);
            //get back that person, put it into a new person object
            compareTest = eDao.getAuthToken(bestAuthToken.getToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //if we have something, something was put into our database
        assertNotNull(compareTest);
        //make sure that something was what we actually put into our database
        assertEquals(compareTest,bestAuthToken);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao eDao = new AuthTokenDao(conn);
            eDao.addAuthToken(bestAuthToken);
            //try and add it again, and it should throw an exception
            eDao.addAuthToken(bestAuthToken);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        //to make sure both events we tried to add are not in the database,
        //first set compareTest to an actual person object
        AuthToken compareTest = bestAuthToken;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao eDao = new AuthTokenDao(conn);
            //then try to find it in my database (this should return null)
            compareTest = eDao.getAuthToken(bestAuthToken.getUser());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void retrievePass() throws Exception {
        AuthToken compare = null;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao pDao = new AuthTokenDao(conn);
            pDao.addAuthToken(bestAuthToken);
            compare = pDao.getAuthToken(bestAuthToken.getToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(compare, bestAuthToken);
    }

    @Test
    public void retrieveFail() throws Exception {
        AuthToken compare = bestAuthToken;
        boolean didItWork = false;
        try{
            Connection conn = db.openConnection();
            AuthTokenDao pDao = new AuthTokenDao(conn);
            //bestPerson is not in the database, so this should return null
            compare = pDao.getAuthToken(bestAuthToken.getUser());
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
        AuthToken check = bestAuthToken;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao pDao = new AuthTokenDao(conn);
            //add a person to the database table
            pDao.addAuthToken(bestAuthToken);
            //then clear the database table
            pDao.clearAuthTokens();
            //if clear failed, this person would still be in the table
            check = pDao.getAuthToken(bestAuthToken.getUser());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }

    @Test
    public void deletePass() throws Exception {
        AuthToken check = bestAuthToken;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao pDao = new AuthTokenDao(conn);
            //add a person to the database table
            pDao.addAuthToken(bestAuthToken);
            //then delete this person
            pDao.deleteAuthToken(bestAuthToken.getUser());
            //if delete failed, this person would still be in the table
            check = pDao.getAuthToken(bestAuthToken.getUser());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }
}
