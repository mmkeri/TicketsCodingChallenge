import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mmkeri on 01/10/2017.
 */
public class SearchQuadTree {

    public static List<Event> searchQuadTree(int xCoord, int yCoord, QuadTree testQuadTree){
        List<Event> result = new ArrayList<Event>();
        int xMin = xCoord;
        int xMax = xCoord;
        int yMin = yCoord;
        int yMax = yCoord;

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
        return result;
    }
}
