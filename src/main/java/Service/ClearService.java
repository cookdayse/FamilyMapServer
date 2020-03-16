package Service;

import DataAccess.*;
import Result.ClearResult;

import java.sql.Connection;

/**
 * Used to interact with the Daos to clear the database
 */
public class ClearService {
    /**
     * Clears the database by interacting with all of the Daos
     * CLEARS LITERALLY EVERYTHING
     *
     * @return result of the clear
     */
    public ClearResult clear() throws DataAccessException {
        Database db = new Database();
        ClearResult result = null;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao a = new AuthTokenDao(conn);
            a.clearAuthTokens();
            EventDao e = new EventDao(conn);
            e.clearEvents();
            PersonDao d = new PersonDao(conn);
            d.clearPeople();
            UserDao u = new UserDao(conn);
            u.clearUsers();
            db.closeConnection(true);
            result = new ClearResult("Clear succeeded.", true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            result = new ClearResult(e.getMessage(), false);
        }
        return result;
    }
}
