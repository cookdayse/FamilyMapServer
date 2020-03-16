package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * Used to interact with the Daos to login
 */
public class LoginService {
    /**
     * Interacts with the UserDao and AuthTokenDao to login
     * Logs the user in and returns an AuthToken
     *
     * @param r request to login
     * @return the result of the login
     */
    public LoginResult login(LoginRequest r) throws DataAccessException {
        Database db = new Database();
        LoginResult out;
        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            User user = userDao.getUser(r.getUserName());

            //error check!
            if(user == null) {
                throw new DataAccessException("error: User not in database");
            }
            if(r.getUserName() == null) {
                throw new DataAccessException("error: Username value empty");
            }
            if(r.getPassword() == null) {
                throw new DataAccessException("error: Password value empty");
            }

            if(user.checkPassword(r.getPassword())) {
                AuthTokenDao a = new AuthTokenDao(conn);
                String uuid = UUID.randomUUID().toString();
                a.addAuthToken(new AuthToken(user.getAssociatedUsername(), uuid));
                out = new LoginResult(uuid, user.getAssociatedUsername(), user.getPerson());
            }
            else {
                out = new LoginResult("error: Incorrect Password");
            }
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            out = new LoginResult(e.getMessage());
        }
        return out;
    }
}
