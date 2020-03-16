package DataAccess;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person bestPerson;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        bestPerson = new Person("lovely", "gamer420", "Me", "Stupid", "m", null, null, null);
    }

    @AfterEach
    public void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void insertPass() throws Exception {
        Person compareTest = null;

        try {
            //open a connection and make a new dao
            Connection conn = db.openConnection();
            PersonDao eDao = new PersonDao(conn);
            //insert a person
            eDao.addPerson(bestPerson);
            //get back that person, put it into a new person object
            compareTest = eDao.getPerson(bestPerson.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //if we have something, something was put into our database
        assertNotNull(compareTest);
        //make sure that something was what we actually put into our database
        assertEquals(compareTest,bestPerson);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try{
            Connection conn = db.openConnection();
            PersonDao eDao = new PersonDao(conn);
            eDao.addPerson(bestPerson);
            //try and add it again, and it should throw an exception
            eDao.addPerson(bestPerson);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);

        //to make sure both Persons we tried to add are not in the database,
        //first set compareTest to an actual person object
        Person compareTest = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao eDao = new PersonDao(conn);
            //then try to find it in my database (this should return null)
            compareTest = eDao.getPerson(bestPerson.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    @Test
    public void retrievePass() throws Exception {
        Person compare = null;
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.addPerson(bestPerson);
            compare = pDao.getPerson(bestPerson.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(compare, bestPerson);
    }

    @Test
    public void retrieveFail() throws Exception {
        Person compare = bestPerson;
        boolean didItWork = false;
        try{
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //bestPerson is not in the database, so this should return null
            compare = pDao.getPerson(bestPerson.getID());
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
        Person check = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //add a person to the database table
            pDao.addPerson(bestPerson);
            //then clear the database table
            pDao.clearPeople();
            //if clear failed, this person would still be in the table
            check = pDao.getPerson(bestPerson.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }

    @Test
    public void deletePass() throws Exception {
        Person check = bestPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //add a person to the database table
            pDao.addPerson(bestPerson);
            //then delete this person
            pDao.deletePerson(bestPerson.getID());
            //if delete failed, this person would still be in the table
            check = pDao.getPerson(bestPerson.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(check);
    }

    @Test
    public void editPersonPass() throws Exception {
        Person check = null;
        Person extraCheck = null;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            pDao.addPerson(bestPerson);
            pDao.editPerson(bestPerson.getID(), "myspouse", "fatehr", "mother");
            extraCheck = new Person(bestPerson.getID(), bestPerson.getAssociatedUsername(), bestPerson.getFirstName(), bestPerson.getLastName(), bestPerson.getGender(),
                    "fatehr", "mother", "myspouse");
            check = pDao.getPerson(bestPerson.getID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(check);
        assertNotEquals(check, bestPerson);
        assertEquals(check, extraCheck);
    }

    @Test
    public void editPersonFail() throws Exception {
        Person check = null;
        boolean extraCheck = true;
        try {
            Connection conn = db.openConnection();
            PersonDao pDao = new PersonDao(conn);
            //this will fail bc nothing is in the database to edit and it will skip the next line of code
            if(pDao.editPerson(bestPerson.getID(), bestPerson.getSpouseID(), bestPerson.getFatherID(), bestPerson.getMotherID())) {
                check = bestPerson;
                extraCheck = false;
            }
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            extraCheck = false;
        }
        assertNull(check);
        assertTrue(extraCheck);
    }

    @Test
    public void getPeoplePass() throws Exception {
        Person death = new Person("a;ldj;a", "gamer420", "notme", "20", "43", "england", "london", "death");
        Person otherPerson = new Person("al;skj", "Notg420", "Me", "33", "42", "not", "It", "mawwiage");
        ArrayList<Person> Persons = null;
        try {
            Connection conn = db.openConnection();
            PersonDao eDao = new PersonDao(conn);
            eDao.addPerson(bestPerson);
            eDao.addPerson(death);
            eDao.addPerson(otherPerson);
            Persons = eDao.getPeople(bestPerson.getAssociatedUsername());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        boolean good = true;
        if(Persons.size() != 2) good = false;
        if(!Persons.contains(bestPerson)) { good = false; }
        if(Persons.contains(otherPerson)) { good = false; }
        assertTrue(good);
    }

    @Test
    public void getPeopleFail() throws Exception {
        boolean good = true;
        Person death = new Person("a;ldj;a", "gamer420", "notme", "20", "43", "england", "london", "death");
        Person otherPerson = new Person("al;skj", "Notg420", "Me", "33", "42", "not", "It", "mawwiage");
        ArrayList<Person> Persons = null;
        try {
            Connection conn = db.openConnection();
            PersonDao eDao = new PersonDao(conn);
            eDao.addPerson(bestPerson);
            eDao.addPerson(death);
            eDao.addPerson(otherPerson);
            Persons = eDao.getPeople("a");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            good = false;
        }
        if(Persons.contains(bestPerson)) { good = false; }
        if(Persons.contains(death)) { good = false; }
        if(Persons.contains(otherPerson)) { good = false; }
        assertTrue(good);
    }
}
