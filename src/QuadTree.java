import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class QuadTree {


    private Node root_;

    /**
     * Constructs a new quad tree.
     *
     * @param {double} minX Minimum x-value that can be held in tree.
     * @param {double} minY Minimum y-value that can be held in tree.
     * @param {double} maxX Maximum x-value that can be held in tree.
     * @param {double} maxY Maximum y-value that can be held in tree.
     */
    public QuadTree(int minX, int minY, int maxX, int maxY) {
        this.root_ = new Node(minX, minY, maxX - minX, maxY - minY, null);
    }

    /**
     * Returns a reference to the tree's root node.  Callers shouldn't modify nodes,
     * directly.  This is a convenience for visualization and debugging purposes.
     *
     * @return {Node} The root node.
     */
    public Node getRootNode() {
        return this.root_;
    }

    /**
     * Sets the value of an (x, y) point within the quad-tree.
     *
     * @param {double} x The x-coordinate.
     * @param {double} y The y-coordinate.
     * @param {Object} value The value associated with the point.
     */
    public void set(Event event) {
        int x = event.getxCoord();
        int y = event.getyCoord();

        Node root = this.root_;
        if (x < root.getX() || y < root.getY() || x > root.getX() + root.getW() || y > root.getY() + root.getH()) {
            throw new QuadTreeException("Out of bounds : (" + x + ", " + y + ")");
        }
        this.insert(root, event);
    }

    /**
     * Inserts a point into the tree, updating the tree's structure if necessary.
     * @param {.QuadTree.Node} parent The parent to insert the point
     *     into.
     * @param {QuadTree.Point} point The point to insert.
     * @return {boolean} True if a new node was added to the tree; False if a node
     *     already existed with the correpsonding coordinates and had its value
     *     reset.
     * @private
     */
    private boolean insert(Node parent, Event event) {
        Boolean result = false;
        switch (parent.getNodeType()) {
            case EMPTY:
                this.setPointForNode(parent, event);
                result = true;
                break;
            case LEAF:
                if (parent.getEvent().getxCoord() == event.getxCoord() && parent.getEvent().getyCoord() == event.getyCoord()) {
                    this.setPointForNode(parent, event);
                    result = false;
                } else {
                    this.split(parent);
                    result = this.insert(parent, event);
                }
                break;
            case POINTER:
                result = this.insert(
                        this.getQuadrantForPoint(parent, event.getxCoord(), event.getyCoord()), event);
                break;

            default:
                throw new QuadTreeException("Invalid nodeType in parent");
        }
        return result;
    }

    /**
     * Converts a leaf node to a pointer node and reinserts the node's point into
     * the correct child.
     * @param {QuadTree.Node} node The node to split.
     * @private
     */
    private void split(Node node) {
        Event oldEvent = node.getEvent();
        node.setEvent(null);

        node.setNodeType(NodeType.POINTER);

        int x = node.getX();
        int y = node.getY();
        double hw = node.getW() / 2;
        double hh = node.getH() / 2;

        node.setNw(new Node(x, y, hw, hh, node));
        node.setNe(new Node((int)(x + hw), y, hw, hh, node));
        node.setSw(new Node(x, (int)(y + hh), hw, hh, node));
        node.setSe(new Node((int)(x + hw), (int)(y + hh), hw, hh, node));

        this.insert(node, oldEvent);
    }

    /**
     * Returns the child quadrant within a node that contains the given (x, y)
     * coordinate.
     * @param {QuadTree.Node} parent The node.
     * @param {number} x The x-coordinate to look for.
     * @param {number} y The y-coordinate to look for.
     * @return {QuadTree.Node} The child quadrant that contains the
     *     point.
     * @private
     */
    private Node getQuadrantForPoint(Node parent, double x, double y) {
        double mx = parent.getX() + parent.getW() / 2;
        double my = parent.getY() + parent.getH() / 2;
        if (x < mx) {
            return y < my ? parent.getNw() : parent.getSw();
        } else {
            return y < my ? parent.getNe() : parent.getSe();
        }
    }

    /**
     * Sets the point for a node, as long as the node is a leaf or empty.
     * @param {QuadTree.Node} node The node to set the point for.
     * @param {QuadTree.Point} point The point to set.
     * @private
     */
    private void setPointForNode(Node node, Event event) {
        if (node.getNodeType() == NodeType.POINTER) {
            throw new QuadTreeException("Can not set point for node of type POINTER");
        }
        node.setNodeType(NodeType.LEAF);
        node.setEvent(event);
    }

    private void search(Position position, BestSet bestSet, Node node){
        int x = position.getxCoord();
        int y = position.getyCoord();

        if(node == null){
            return;
        }

        int x1 = node.getX(), y1 = node.getY();
        double x2 = node.getW() + x1, y2 = node.getH() + y1;

        if(bestSet.isAtCapacity()){
            int furthestDistance = bestSet.getCurrentFurthestDistance();
            if (x < x1 - furthestDistance || x > x2 + furthestDistance ||
                y < y1 - furthestDistance || y > y2 + furthestDistance) {
                return;
            }
        }
        Event nodeEvent = node.getEvent();
        if(nodeEvent != null) {
            int distance = ManhattanDistances.calculateManhattanDistance(nodeEvent, position);
            bestSet.updateIfBetter(distance, nodeEvent);
        }

        double mx = node.getX() + node.getW() / 2;
        double my = node.getY() + node.getH() / 2;
        double deltaX = x - mx;
        double deltaY = y - my;
        double absDeltaX = Math.abs(deltaX);
        double absDeltaY = Math.abs(deltaY);

        if (deltaX < 0) {
            if(deltaY < 0){
                if (absDeltaX > absDeltaY){
                    searchFourQuadrants(position, bestSet, node.getNw(), node.getSw(), node.getNe(), node.getSe());
                } else {
                    searchFourQuadrants(position, bestSet, node.getNw(), node.getNe(), node.getSw(), node.getSe());
                }
            } else {
                if(absDeltaX < absDeltaY){
                    searchFourQuadrants(position, bestSet, node.getSw(), node.getSe(), node.getNw(), node.getNe());
                } else {
                    searchFourQuadrants(position, bestSet, node.getSw(), node.getNw(), node.getSe(), node.getNe());
                }
            }
        } else {
            if(deltaY < 0) {
                if(absDeltaX > absDeltaY){
                    searchFourQuadrants(position, bestSet, node.getNe(), node.getSe(), node.getNw(), node.getSw());
                } else {
                    searchFourQuadrants(position, bestSet, node.getNe(), node.getNw(), node.getSe(), node.getSw());
                }
            } else {
                if(absDeltaX < absDeltaY){
                    searchFourQuadrants(position, bestSet, node.getSe(), node.getSw(), node.getNe(), node.getNw());
                } else {
                    searchFourQuadrants(position, bestSet, node.getSe(), node.getNe(), node.getSw(), node.getNw());
                }
            }
        }
    }

    public void searchFourQuadrants(Position position, BestSet bestSet, Node q1, Node q2, Node q3, Node q4){
        search(position, bestSet, q1);
        search(position, bestSet, q2);
        search(position, bestSet, q3);
        search(position, bestSet, q4);
    }

    public List<Map.Entry<Integer,Event>> search(Position position, int numOfResults){
        BestSet bestSet = new BestSet(numOfResults);
        this.search(position, bestSet, this.getRootNode());
        return bestSet.flattened();
    }
}
