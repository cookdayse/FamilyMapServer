package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Used to interact with the Daos to register a user
 */
public class RegisterService {
    /**
     * Interacts with the PersonDao and UserDao and AuthTokenDao to register a user
     * Registers the user, generates 4 generations of data for the user, logs in and returns an AuthToken
     *
     * @param r the request
     * @return the result of the request
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException, IOException {
        Database db = new Database();
        RegisterResult out = null;
        try {
            Connection conn = db.openConnection();
            UserDao u = new UserDao(conn);
            AuthTokenDao a = new AuthTokenDao(conn);
            PersonDao p = new PersonDao(conn);
            EventDao e = new EventDao(conn);

            //create user
            String uuid = UUID.randomUUID().toString();
            //error check!
            if(r.getUserName() == null) {
                throw new DataAccessException("error: Username value missing");
            }
            if(u.getUser(r.getUserName()) != null) {
                throw new DataAccessException("error: Username already taken by another user");
            }
            if(!r.getGender().equals("m")) {
                if(!r.getGender().equals("f")) {
                    throw new DataAccessException("error: Invalid gender value");
                }
            }
            if(r.getGender() == null) {
                throw new DataAccessException("error: gender value missing");
            }
            if(r.getLastName() == null) {
                throw new DataAccessException("error: last name value missing");
            }
            if(r.getFirstName() == null) {
                throw new DataAccessException("error: first name value missing");
            }
            if(r.getEmail() == null) {
                throw new DataAccessException("error: email value missing");
            }
            if(r.getPassword() == null) {
                throw new DataAccessException("error: password value missing");
            }


            u.addUser(new User(uuid, r.getUserName(), r.getPassword(), r.getEmail(),
                    r.getFirstName(), r.getLastName(), r.getGender()));

            //register user as a person with the personId of the person you just made based on this user
            Person thisUser = new Person(uuid, r.getUserName(), r.getFirstName(), r.getLastName(),
                    r.getGender(), null, null, null);
            p.addPerson(thisUser);
            EventMaker maker = new EventMaker();
            Event birth = maker.randomBirth(r.getUserName(), uuid);
            e.addEvent(birth);

            ArrayList<Person> generation = new ArrayList<Person>();
            generation.add(thisUser);
            ArrayList<Person> nextGen = new ArrayList<Person>();
            for(int i = 0; i < 4; i++) {
                for(Person person: generation) {
                    ArrayList<Event> events = e.getPersonEvents(person.getID());
                    Event childBirth = null;
                    for(Event event: events) {
                        if(event.getEventType().equals("birth")) {
                            childBirth = event;
                            break;
                        }
                    }
                    Person mother = null;
                    Person father = null;
                    String motherUuid = UUID.randomUUID().toString();
                    String fatherUuid = UUID.randomUUID().toString();

                    person.setFatherID(fatherUuid);
                    person.setMotherID(motherUuid);
                    p.editPerson(person.getID(), person.getSpouseID(), person.getFatherID(), person.getMotherID());

                    PersonMaker make = new PersonMaker();
                    mother = make.createFemaleWithHusband(motherUuid, r.getUserName(), fatherUuid);
                    p.addPerson(mother);
                    father = make.createMaleWithWife(fatherUuid, r.getUserName(), motherUuid, person.getLastName());
                    p.addPerson(father);

                    Event momBirth = maker.makeBirth(childBirth, motherUuid);
                    e.addEvent(momBirth);
                    Event dadBirth = maker.makeBirth(childBirth, fatherUuid);
                    e.addEvent(dadBirth);

                    Event marriage = maker.makeMarriage(momBirth, childBirth);
                    e.addEvent(marriage);
                    Event dadMarriage = new Event(fatherUuid, marriage.getAssociatedUsername(), fatherUuid, marriage.getLatitude(),
                            marriage.getLongitude(), marriage.getCountry(), marriage.getCity(), marriage.getEventType(), marriage.getYear());
                    e.addEvent(dadMarriage);

                    Event death = maker.makeDeath(marriage, childBirth);
                    e.addEvent(death);
                    Event dadDeath = maker.makeDeath(dadMarriage, childBirth);
                    e.addEvent(dadDeath);

                    nextGen.add(mother);
                    nextGen.add(father);
                }
                generation.clear();
                generation = (ArrayList)nextGen.clone();
                nextGen.clear();
            }

            //create and return an authToken
            String newUuid = UUID.randomUUID().toString();
            AuthToken myToken = new AuthToken(r.getUserName(), newUuid);
            a.addAuthToken(myToken);
            out = new RegisterResult(newUuid, r.getUserName(), uuid);

            db.closeConnection(true);
        } catch (DataAccessException e) {
            out = new RegisterResult(e.getMessage());
            db.closeConnection(false);
        }
        return out;
    }
}
