import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        QuadTree testQuadTree = new QuadTree(-10, -10, 10, 10);
        testQuadTree = InitializeQuadTree.InitializeQuadTree(testQuadTree);
        GuiInterface inputInterface = new GuiInterface();
        inputInterface.createTheFrame(testQuadTree);
    }
}
