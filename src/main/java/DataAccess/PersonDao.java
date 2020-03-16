package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Used to access the person and children tables in the database.
 */
public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }
    /**
     * Adds a person to the database
     *
     * @param personIn person to be added.
     */
    public void addPerson(Person personIn) throws DataAccessException {
        String sql = "INSERT INTO person (id, user_id, first_name, last_name, gender, father_id, mother_id, spouse_id)" +
                "VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personIn.getID());
            stmt.setString(2, personIn.getAssociatedUsername());
            stmt.setString(3, personIn.getFirstName());
            stmt.setString(4, personIn.getLastName());
            stmt.setString(5, personIn.getGender());
            stmt.setString(6, personIn.getFatherID());
            stmt.setString(7, personIn.getMotherID());
            stmt.setString(8, personIn.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Deletes a person from the database.
     *
     * @param personIn the id of the person to delete.
     */
    public boolean deletePerson(String personIn) throws DataAccessException {
        String sql = "DELETE FROM person WHERE id =?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personIn);
            if(stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting a person");
        }
        return false;
    }

    /**
     * clears the Person table in the database
     */
    public void clearPeople() throws DataAccessException {
        String sql = "DELETE FROM person";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while deleting all people");
        }
    }

    public Person getPerson(String idIn) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idIn);
            rs = stmt.executeQuery();
            if(rs.next()) {
                person = new Person(rs.getString("id"), rs.getString("user_id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("gender"), rs.getString("father_id"), rs.getString("mother_id"), rs.getString("spouse_id"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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

    public boolean editPerson(String id, String spouse, String father, String mother) throws DataAccessException {
        String sql = "UPDATE person SET spouse_id = ?, father_id = ?, mother_id = ? WHERE id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, spouse);
            stmt.setString(2, father);
            stmt.setString(3, mother);
            stmt.setString(4, id);
            if(stmt.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while editing person");
        }
        return false;
    }
    
   public ArrayList<Person> getPeople(String idIn) throws DataAccessException {
       ArrayList<Person> person = new ArrayList<Person>();
       ResultSet rs = null;
       String sql = "SELECT * FROM person WHERE user_id = ?";
       try(PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setString(1, idIn);
           rs = stmt.executeQuery();
           while(rs.next()) {
               person.add(new Person(rs.getString("id"), rs.getString("user_id"), rs.getString("first_name"),
                       rs.getString("last_name"), rs.getString("gender"), rs.getString("father_id"), rs.getString("mother_id"), rs.getString("spouse_id")));
           }
           return person;
       } catch (SQLException e) {
           e.printStackTrace();
           throw new DataAccessException("Error encountered while finding people");
       } finally {
           if(rs != null) {
               try{
                   rs.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
   }

}
