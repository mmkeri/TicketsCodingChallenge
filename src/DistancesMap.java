import java.util.List;
import java.util.TreeMap;

public class DistancesMap {

    private TreeMap<Integer, List<Event>> distancesMap = new TreeMap<Integer, List<Event>>();

    public void addEventToMap(Event event, Position customerPosition){

    }

    public static int calculateManhattanDistance(Event event, Position customerPosition){
        return Math.abs(event.getxCoord() - customerPosition.getxCoord()) +
                Math.abs(event.getyCoord() - customerPosition.getyCoord());
    }
}
