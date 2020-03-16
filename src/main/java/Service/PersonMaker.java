package Service;

import Handlers.JsonSerializer;
import Model.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PersonMaker {
    List<String> fnames;
    List<String> mnames;
    List<String> snames;

    PersonMaker() throws IOException {
        String jsonString = new Scanner(new File("json/fnames.json")).useDelimiter("\\Z").next();
        ListsOfStrings list = new JsonSerializer().deserialize(jsonString,(ListsOfStrings.class));
        fnames = list.getData();
        jsonString = new Scanner(new File("json/mnames.json")).useDelimiter("\\Z").next();
        list = new JsonSerializer().deserialize(jsonString, ListsOfStrings.class);
        mnames = list.getData();
        jsonString = new Scanner(new File("json/snames.json")).useDelimiter("\\Z").next();
        list = new JsonSerializer().deserialize(jsonString, ListsOfStrings.class);
        snames = list.getData();
    }
    Person createMaleWithWife(String id, String userId, String wifeId, String lastName) {
        Person husband = null;
        Random random = new Random();
        String firstName = mnames.get(random.nextInt(mnames.size()));
        husband = new Person(id, userId, firstName, lastName, "m", null, null, wifeId);
        return husband;
    }
    Person createFemaleWithHusband(String id, String userId, String husbandId) {
        Person wife = null;
        Random random = new Random();
        String firstName = fnames.get(random.nextInt(fnames.size()));
        String lastName = snames.get(random.nextInt(snames.size()));
        wife = new Person(id, userId, firstName, lastName, "f", null, null,  husbandId);
        return wife;
    }
}
