import java.util.List;
import java.util.TreeMap;

/**
 * Created by mmkeri on 27/09/2017.
 */
public class DistancesMap {

    private TreeMap<Integer, List<Event>> distancesMap = new TreeMap<Integer, List<Event>>();

    public void addEventToMap(Event event, Position customerPosition){

    }

    private int calculateManhattanDistance(Event event, Position customerPosition){
        return Math.abs(event.getxCoord() - customerPosition.getxCoord()) +
                Math.abs(event.getyCoord() - customerPosition.getyCoord());
    }
}
