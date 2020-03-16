package Service;

import java.util.List;

public class ListOfLocations {
    List<Locations> data;
    ListOfLocations(List<Locations> list) { data = list; }
    List<Locations> getData() { return data; }
}
