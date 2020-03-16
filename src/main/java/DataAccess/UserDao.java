package DataAccess;

import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used to access User table in database.
 */
public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a user to the database.
     *
     * @param userIn user to add to the database.
     */
    public void addUser(User userIn) throws DataAccessException {
        String sql = "INSERT INTO user (person_id, username, password, email, first_name, last_name, gender)" +
                "VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userIn.getPerson());
            stmt.setString(2, userIn.getAssociatedUsername());
            stmt.setString(3, userIn.getPassword());
            stmt.setString(4, userIn.getEmail());
            stmt.setString(5, userIn.getFirstName());
            stmt.setString(6, userIn.getLastName());
            stmt.setString(7, userIn.getGender());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userIn user id of the user to delete.
     */
    public boolean deleteUser(String userIn) throws DataAccessException {
        String sql = "DELETE FROM user WHERE username =?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userIn);
            if(stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting a person");
        }
        return false;
    }

    /**
     * clears the User table in the database
     */
    public void clearUsers() throws DataAccessException {
        String sql = "DELETE FROM user";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all people");
        }}

    public User getUser(String userIn) throws DataAccessException {
        User person;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userIn);
            rs = stmt.executeQuery();
            if(rs.next()) {
                person = new User(rs.getString("person_id"), rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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
