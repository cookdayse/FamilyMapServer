package DataAccess;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Used to access AuthToken table in database.
 */
public class AuthTokenDao {
    private final Connection conn;

    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds an authorization token to the database.
     *
     * @param tokenIn token to be added.
     */
    public void addAuthToken(AuthToken tokenIn) throws DataAccessException {
        String sql = "INSERT INTO AuthToken (user_id, token) VALUES(?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tokenIn.getUser());
            stmt.setString(2, tokenIn.getToken());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Deletes an authorization token from the database.
     *
     * @param token token to delete.
     */
    public boolean deleteAuthToken(String token) throws DataAccessException {
        String sql = "DELETE FROM AuthToken WHERE user_id =?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            if(stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting a person");
        }
        return false;
    }

    /**
     * clears the AuthToken table in the database
     */
    public void clearAuthTokens() throws DataAccessException {
        String sql = "DELETE FROM AuthToken";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all people");
        }
    }

    public AuthToken getAuthToken(String token) throws DataAccessException {
        AuthToken person;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE token = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if(rs.next()) {
                person = new AuthToken(rs.getString("user_id"), rs.getString("token"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding AuthToken");
        } finally {
            if(rs != null) {
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
