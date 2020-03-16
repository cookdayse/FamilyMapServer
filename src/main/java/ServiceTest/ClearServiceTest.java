package ServiceTest;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Result.ClearResult;
import Service.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    Database db;
    Person p;
    ClearResult clearResult;
    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        p = new Person("sa;klj", "kl", "sadflk", "as;kldf,", "m", null, null, null);
        clearResult = new ClearResult("Clear succeeded.", true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void clearPass() throws DataAccessException {
        ClearResult c = null;
        try {
            Connection conn = db.openConnection();
            PersonDao a = new PersonDao(conn);
            a.addPerson(p);
            db.closeConnection(true);
            c = new ClearService().clear();
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(c);
        assertEquals(clearResult, c);
    }
}
