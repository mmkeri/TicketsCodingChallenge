import java.util.List;
import java.util.TreeMap;

public class ManhattanDistances {

    public static int calculateManhattanDistance(Event event, Position customerPosition){
        return Math.abs(event.getxCoord() - customerPosition.getxCoord()) +
                Math.abs(event.getyCoord() - customerPosition.getyCoord());
    }
}
