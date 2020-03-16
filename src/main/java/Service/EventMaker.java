package Service;

import Handlers.JsonSerializer;
import Model.Event;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class EventMaker {
    List<Locations> locations;
    public EventMaker() throws IOException {
        String jsonString = new Scanner(new File("json/locations.json")).useDelimiter("\\Z").next();
        ListOfLocations list = new JsonSerializer().deserialize(jsonString,(ListOfLocations.class));
        locations = list.getData();
    }

    Event randomBirth(String user, String person) {
        Event random = null;
        String uuid = UUID.randomUUID().toString();
        Random random1 = new Random();
        Locations place = locations.get(random1.nextInt(locations.size()));
        int year = 1970 + random1.nextInt(100) - 50;
        random = new Event(uuid, user, person, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(),
                "birth", year);
        return random;
    }

    Event makeBirth(Event childBirth, String person) {
        String uuid = UUID.randomUUID().toString();
        Random random = new Random();
        Locations place = locations.get(random.nextInt(locations.size()));
        int diffInAge = 20 + random.nextInt(20);
        Event birth = new Event(uuid, childBirth.getAssociatedUsername(), person, place.getLatitude(), place.getLongitude(),
                place.getCountry(), place.getCity(), "birth", childBirth.getYear() - diffInAge);
        return birth;
    }

    Event makeMarriage(Event birth, Event childBirth) {
        Event marriage = null;
        String uuid = UUID.randomUUID().toString();

        Random random = new Random();
        Locations place = locations.get(random.nextInt(locations.size()));

        int year = birth.getYear() + random.nextInt(childBirth.getYear() - birth.getYear() - 18) + 19;

        marriage = new Event(uuid, birth.getAssociatedUsername(), birth.getPerson(), place.getLatitude(), place.getLongitude(), place.getCountry(),
                place.getCity(), "marriage", year);
        return marriage;
    }

    Event makeDeath(Event marriage, Event childBirth) {
        Event death = null;
        String uuid = UUID.randomUUID().toString();

        Random random = new Random();
        Locations place = locations.get(random.nextInt(locations.size()));

        int year = childBirth.getYear() + random.nextInt(60);

        death = new Event(uuid, childBirth.getAssociatedUsername(), marriage.getPerson(), place.getLatitude(), place.getLongitude(),
                place.getCountry(), place.getCity(), "death", year);
        return death;
    }
}