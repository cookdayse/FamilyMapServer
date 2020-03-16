package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;
import Result.PersonsResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Interacts with the Dao to return person files
 */
public class PersonService {
    /**
     * interacts with the Dao to create a PersonResult to return
     *
     * @param idIn id of the person to find
     * @param check AuthToken of current user
     * @return information of the person
     */
    public PersonResult person(String idIn, String check) throws DataAccessException {
        Database db = new Database();
        PersonResult out = null;
        try {
            Connection conn = db.openConnection();
            PersonDao p = new PersonDao(conn);
            Person person = p.getPerson(idIn);
            if(person == null) {
                throw new DataAccessException("error: Invalid personID parameter");
            }
            AuthTokenDao a = new AuthTokenDao(conn);
            assert check != null;
            AuthToken auth = a.getAuthToken(check);
            if(auth == null) {
                throw new DataAccessException("error: Invalid auth token");
            }
            if(!person.getAssociatedUsername().equals(auth.getUser())) {
                out = new PersonResult("error: Requested person does not belong to this user");
            }
            else {
                out = new PersonResult(person.getAssociatedUsername(), person.getID(), person.getFirstName(),
                        person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID());
            }
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            out = new PersonResult(e.getMessage());
        }
        return out;
    }

    /**
     * returns all family members of the current user
     *
     * @param check AuthToken of current user
     * @return the family members
     */
    public PersonsResult person(String check) throws DataAccessException {
        Database db = new Database();
        PersonsResult out = null;
        try {
            Connection conn = db.openConnection();
            PersonDao d = new PersonDao(conn);
            AuthTokenDao a = new AuthTokenDao(conn);
            AuthToken auth = a.getAuthToken(check);
            if(auth == null) {
                throw new DataAccessException("error: Invalid auth token");
            }
            ArrayList<Person> people = d.getPeople(auth.getUser());
            out = new PersonsResult(people);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            out = new PersonsResult(e.getMessage());
            db.closeConnection(false);
        }
        return out;
    }
}
