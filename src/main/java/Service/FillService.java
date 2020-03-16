package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Used to interact with the Daos to fill a tree with people
 */
public class FillService {
    /**
     * Interacts with PersonDao and EventDao to first delete anyone associated with a username
     * Then fills in a tree with a number of generations, default being 4
     *
     * @param r request
     * @return result of the request
     */
    public FillResult fill(FillRequest r) throws DataAccessException {
        Database db = new Database();
        FillResult result = null;
        try {
            Connection conn = db.openConnection();
            UserDao u = new UserDao(conn);
            PersonDao p = new PersonDao(conn);
            EventDao e = new EventDao(conn);

            //get the events and people
            ArrayList<Event> eventList = e.getEvents(r.getUsername());
            ArrayList<Person> people = p.getPeople(r.getUsername());

            //iterate through the events and people to delete them all
            for(Event event: eventList) {
                e.deleteEvent(event.getID());
            }
            for(Person person: people) {
                p.deletePerson(person.getID());
            }

            //error check!
            if(r.getGenerations() < 0) {
                throw new DataAccessException("error: Invalid generations parameter");
            }
            if(r.getUsername() == null) {
                throw new DataAccessException("error: Username value missing");
            }
            if(u.getUser(r.getUsername()) == null) {
                throw new DataAccessException("error: Invalid username");
            }

            int personNum = 0;
            int eventNum = 0;

            //create the first person in the tree to start
            User user = u.getUser(r.getUsername());
            Person thisUser = new Person(user.getPerson(), user.getAssociatedUsername(), user.getFirstName(), user.getLastName(),
                    user.getGender(), null, null, null);
            p.addPerson(thisUser);
            personNum += 1;

            EventMaker maker = new EventMaker();
            Event birth = maker.randomBirth(user.getAssociatedUsername(), user.getPerson());
            e.addEvent(birth);
            eventNum += 1;

            //fill in a tree with a number of generations
            ArrayList<Person> generation = new ArrayList<Person>();
            generation.add(thisUser);
            ArrayList<Person> nextGen = new ArrayList<Person>();
            for(int i = 0; i < r.getGenerations(); i++) {
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
                    mother = make.createFemaleWithHusband(motherUuid, person.getAssociatedUsername(), fatherUuid);
                    p.addPerson(mother);
                    personNum += 1;

                    father = make.createMaleWithWife(fatherUuid, person.getAssociatedUsername(), motherUuid, person.getLastName());
                    p.addPerson(father);
                    personNum += 1;

                    Event momBirth = maker.makeBirth(childBirth, motherUuid);
                    e.addEvent(momBirth);
                    Event dadBirth = maker.makeBirth(childBirth, fatherUuid);
                    e.addEvent(dadBirth);

                    Event marriage = maker.makeMarriage(momBirth, childBirth);
                    e.addEvent(marriage);
                    String dadMarriageUuid = UUID.randomUUID().toString();
                    Event dadMarriage = new Event(dadMarriageUuid, marriage.getAssociatedUsername(), fatherUuid, marriage.getLatitude(),
                            marriage.getLongitude(), marriage.getCountry(), marriage.getCity(), marriage.getEventType(), marriage.getYear());
                    e.addEvent(dadMarriage);

                    Event death = maker.makeDeath(marriage, childBirth);
                    e.addEvent(death);
                    Event dadDeath = maker.makeDeath(dadMarriage, childBirth);
                    e.addEvent(dadDeath);

                    eventNum += 6;

                    nextGen.add(mother);
                    nextGen.add(father);
                }
                generation.clear();
                generation = (ArrayList)nextGen.clone();
                nextGen.clear();
            }
            result = new FillResult("Successfully added " + Integer.toString(personNum) +
                    " persons and " + Integer.toString(eventNum) + " events to the database.", true);
            db.closeConnection(true);
        } catch (DataAccessException | IOException e) {
            db.closeConnection(false);
            result = new FillResult(e.getMessage(), false);
        }
        return result;
    }
}
