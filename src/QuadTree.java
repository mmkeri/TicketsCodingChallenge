import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class QuadTree {


    private Node root_;
    private int count_ = 0;

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
        if (this.insert(root, new Event(event.getEventCode(), x, y, event.getEventTickets()))) {
            this.count_++;
        }
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
     * Attempts to balance a node. A node will need balancing if all its children
     * are empty or it contains just one leaf.
     * @param {QuadTree.Node} node The node to balance.
     * @private
     */
    private void balance(Node node) {
        switch (node.getNodeType()) {
            case EMPTY:
            case LEAF:
                if (node.getParent() != null) {
                    this.balance(node.getParent());
                }
                break;

            case POINTER: {
                Node nw = node.getNw();
                Node ne = node.getNe();
                Node sw = node.getSw();
                Node se = node.getSe();
                Node firstLeaf = null;

                // Look for the first non-empty child, if there is more than one then we
                // break as this node can't be balanced.
                if (nw.getNodeType() != NodeType.EMPTY) {
                    firstLeaf = nw;
                }
                if (ne.getNodeType() != NodeType.EMPTY) {
                    if (firstLeaf != null) {
                        break;
                    }
                    firstLeaf = ne;
                }
                if (sw.getNodeType() != NodeType.EMPTY) {
                    if (firstLeaf != null) {
                        break;
                    }
                    firstLeaf = sw;
                }
                if (se.getNodeType() != NodeType.EMPTY) {
                    if (firstLeaf != null) {
                        break;
                    }
                    firstLeaf = se;
                }

                if (firstLeaf == null) {
                    // All child nodes are empty: so make this node empty.
                    node.setNodeType(NodeType.EMPTY);
                    node.setNw(null);
                    node.setNe(null);
                    node.setSw(null);
                    node.setSe(null);

                } else if (firstLeaf.getNodeType() == NodeType.POINTER) {
                    // Only child was a pointer, therefore we can't rebalance.
                    break;

                } else {
                    // Only child was a leaf: so update node's point and make it a leaf.
                    node.setNodeType(NodeType.LEAF);
                    node.setNw(null);
                    node.setNe(null);
                    node.setSw(null);
                    node.setSe(null);
                    node.setEvent(firstLeaf.getEvent());
                }

                // Try and balance the parent as well.
                if (node.getParent() != null) {
                    this.balance(node.getParent());
                }
            }
            break;
        }
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

    public void search(Position position, BestSet bestSet, Node node){
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
        if (x < mx) {
            if(y < my){
                searchFourQuadrants(position, bestSet, node.getNw(), node.getNe(), node.getSw(), node.getSe());
            } else {
                searchFourQuadrants(position, bestSet, node.getSw(), node.getNw(), node.getSe(), node.getNe());
            }
        } else {
            if(y < my) {
                searchFourQuadrants(position, bestSet, node.getNe(), node.getNw(), node.getSe(), node.getSw());
            } else {
                searchFourQuadrants(position, bestSet, node.getSe(), node.getSw(), node.getNe(), node.getNw());
            }
        }
    }

    public void searchFourQuadrants(Position position, BestSet bestSet, Node q1, Node q2, Node q3, Node q4){
        search(position, bestSet, q1);
        search(position, bestSet, q2);
        search(position, bestSet, q3);
        search(position, bestSet, q4);
    }
}
