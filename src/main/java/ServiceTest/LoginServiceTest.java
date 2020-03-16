package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.Person;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    Database db;
    Person person;
    User user;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        person = new Person("nananabooboo", "lovely", "lo", "well", "m",
                null, "momo", null);
        user = new User("nananabooboo", "lovely", "yup", "nope", "lo",
                "well", "m");
        Connection conn = db.openConnection();
        UserDao u = new UserDao(conn);
        PersonDao p = new PersonDao(conn);
        u.addUser(user);
        p.addPerson(person);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loginPass() throws Exception {
        LoginRequest r = new LoginRequest("lovely", "yup");
        LoginResult l = null;
        try {
            l = new LoginService().login(r);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(l);
        assertTrue(l.isSuccess());
    }

    @Test
    public void loginFail() throws Exception {
        LoginRequest r = new LoginRequest("lovely", "yu");
        LoginResult l = null;
        LoginResult q = new LoginResult("error: Incorrect Password");
        try {
            l = new LoginService().login(r);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(l);
        assertFalse(l.isSuccess());
        assertEquals(l,q);
    }
}
