package ServiceTest;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.PersonResult;
import Result.PersonsResult;
import Service.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    Database db;
    Person person;
    Person mom;
    User user;
    Person nonRelated;
    AuthToken authToken;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        person = new Person("nananabooboo", "lovely", "lo", "well", "m",
                null, "momo", null);
        user = new User("nananabooboo", "lovely", "yup", "nope", "lo",
                "well", "m");
        mom = new Person("momo", "lovely", "ve", "well", "f",
                null, null, null);
        nonRelated = new Person("yup", "beaurigul", "beau", "regards", "f",
                null, null, null);
        authToken = new AuthToken("lovely", "iden");
        Connection conn = db.openConnection();
        PersonDao p = new PersonDao(conn);
        UserDao u = new UserDao(conn);
        AuthTokenDao a = new AuthTokenDao(conn);
        u.addUser(user);
        p.addPerson(person);
        p.addPerson(nonRelated);
        p.addPerson(mom);
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
    public void personWithIDPass() throws Exception {
        PersonResult personResult = null;
        try {
            personResult = new PersonService().person("momo", "iden");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(personResult);
        assertTrue(personResult.isSuccess());
    }

    @Test
    public void personWithIDFail() throws Exception {
        PersonResult personResult = null;
        try {
            personResult = new PersonService().person("yup","iden");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(personResult);
        assertFalse(personResult.isSuccess());
    }

    @Test
    public void personPass() throws Exception {
        PersonsResult result = null;
        try {
            result = new PersonService().person("iden");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void personFail() throws Exception {
        PersonsResult result = null;
        try {
            result = new PersonService().person("lovely");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }
}
