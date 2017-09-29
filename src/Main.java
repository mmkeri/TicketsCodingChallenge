import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        QuadTree testQuadTree = new QuadTree(-10, -10, 10, 10);
        testQuadTree = InitializeQuadTree.InitializeQuadTree(testQuadTree);
        System.out.println(testQuadTree.contains(3, 3));

        List<Event> result = new ArrayList<Event>();
        int xMin = 9;
        int yMin = 9;
        int xMax = 9;
        int yMax = 9;
        Position testPosition = new Position(9, 9);
        while(result.size() < 5){
            result = testQuadTree.searchWithin(xMin, yMin, xMax, yMax);
            if(xMin >= -10) {
                xMin--;
            }
            if(yMin >= -10) {
                yMin--;
            }
            if(xMax <= 10) {
                xMax++;
            }
            if(yMax <= 10) {
                yMax++;
            }
        }
        for(Event e: result){
            System.out.println(e.toString() + "Manhattan distance " + DistancesMap.calculateManhattanDistance(e, testPosition));
        }
    }
}
