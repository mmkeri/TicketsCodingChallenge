import java.util.List;
import java.util.TreeMap;

public class ManhattanDistances {

    /**
     * calculates the Manhattan distance between the users location and that of an event
     * @param event {Event} the event's location
     * @param customerPosition {Position} the user's location
     * @return {int}
     */
    public static int calculateManhattanDistance(Event event, Position customerPosition){
        return Math.abs(event.getxCoord() - customerPosition.getxCoord()) +
                Math.abs(event.getyCoord() - customerPosition.getyCoord());
    }
}
