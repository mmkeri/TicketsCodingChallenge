import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SearchQuadTree {

    public static List<Map.Entry<Integer,Event>> searchQuadTree2(Position position, QuadTree testQuadTree){
        BestSet bestSet = new BestSet();
        testQuadTree.search(position, bestSet, testQuadTree.getRootNode());
        return bestSet.flattened();
    }
}
