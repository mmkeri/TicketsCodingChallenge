import java.util.ArrayList;
import java.util.List;

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
    public void set(Event event, int x, int y) {

        Node root = this.root_;
        if (x < root.getX() || y < root.getY() || x > root.getX() + root.getW() || y > root.getY() + root.getH()) {
            throw new QuadTreeException("Out of bounds : (" + x + ", " + y + ")");
        }
        if (this.insert(root, new Event(event.getEventCode(), x, y, event.getEventTickets()))) {
            this.count_++;
        }
    }

    /**
     * Gets the value of the point at (x, y) or null if the point is empty.
     *
     * @param {double} x The x-coordinate.
     * @param {double} y The y-coordinate.
     * @param {Object} opt_default The default value to return if the node doesn't
     *                 exist.
     * @return {*} The value of the node, the default value if the node
     *         doesn't exist, or undefined if the node doesn't exist and no default
     *         has been provided.
     */
    public Object get(int x, int y, Object opt_default) {
        Node node = this.find(this.root_, x, y);
        return node != null ? node.getEvent().getLowestPricedTicket() : opt_default;
    }

    /**
     * Removes a point from (x, y) if it exists.
     *
     * @param {double} x The x-coordinate.
     * @param {double} y The y-coordinate.
     * @return {Object} The value of the node that was removed, or null if the
     *         node doesn't exist.
     */
    public Object remove(int x, int y) {
        Node node = this.find(this.root_, x, y);
        if (node != null) {
            Object value = node.getEvent().getLowestPricedTicket();
            node.setEvent(null);
            node.setNodeType(NodeType.EMPTY);
            this.balance(node);
            this.count_--;
            return value;
        } else {
            return null;
        }
    }

    /**
     * Returns true if the point at (x, y) exists in the tree.
     *
     * @param {double} x The x-coordinate.
     * @param {double} y The y-coordinate.
     * @return {boolean} Whether the tree contains a point at (x, y).
     */
    public boolean contains(int x, int y) {
        return this.get(x, y, null) != null;
    }

    /**
     * @return {boolean} Whether the tree is empty.
     */
    public boolean isEmpty() {
        return this.root_.getNodeType() == NodeType.EMPTY;
    }

    /**
     * @return {number} The number of items in the tree.
     */
    public int getCount() {
        return this.count_;
    }

    /**
     * Removes all items from the tree.
     */
    public void clear() {
        this.root_.setNw(null);
        this.root_.setNe(null);
        this.root_.setSw(null);
        this.root_.setSe(null);
        this.root_.setNodeType(NodeType.EMPTY);
        this.root_.setEvent(null);
        this.count_ = 0;
    }

    /**
     * Returns an array containing the coordinates of each point stored in the tree.
     * @return {Array.<Point>} Array of coordinates.
     */
    public Event[] getKeys() {
        final List<Event> arr = new ArrayList<Event>();
        this.traverse(this.root_, new Function() {
            @Override
            public void call(QuadTree quadTree, Node node) {
                arr.add(node.getEvent());
            }
        });
        return arr.toArray(new Event[arr.size()]);
    }

    /**
     * Returns an array containing all values stored within the tree.
     * @return {Array.<Object>} The values stored within the tree.
     */
    public Object[] getValues() {
        final List<Object> arr = new ArrayList<Object>();
        this.traverse(this.root_, new Function() {
            @Override
            public void call(QuadTree quadTree, Node node) {
                arr.add(node.getEvent().getLowestPricedTicket());
            }
        });

        return arr.toArray(new Object[arr.size()]);
    }

    /**
     * Determines whether a
     * @param xmin
     * @param ymin
     * @param xmax
     * @param ymax
     * @return
     */
    public Point[] searchIntersect(final int xmin, final int ymin, final int xmax, final int ymax) {
        final List<Event> arr = new ArrayList<Event>();
        this.navigate(this.root_, new Function() {
            @Override
            public void call(QuadTree quadTree, Node node) {
                Event ev = node.getEvent();
                if (ev.getxCoord() < xmin || ev.getxCoord() > xmax || ev.getyCoord() < ymin || ev.getyCoord() > ymax) {
                    // Definitely not within the polygon!
                } else {
                    arr.add(node.getEvent());
                }

            }
        }, xmin, ymin, xmax, ymax);
        return arr.toArray(new Point[arr.size()]);
    }

    /**
     * Finds nodes that are located within the polygon outlined by the provided minimum and maximum coordinates
     * @param xmin {int} the x-coordinate of the left lower corner of the polygon
     * @param ymin {int} the y-coordinate of the left lower corner of the polygon
     * @param xmax {int} the x-coordinate of the right upper corner of the polygon
     * @param ymax {int} the y-coordinate of the right upper corner of the polygon
     * @return {List} list of all the events that are found within the polygon
     */
    public List<Event> searchWithin(final int xmin, final int ymin, final int xmax, final int ymax) {
        final List<Event> arr = new ArrayList<Event>();
        this.navigate(this.root_, new Function() {
            @Override
            public void call(QuadTree quadTree, Node node) {
                Event ev = node.getEvent();
                if (ev.getxCoord() > xmin && ev.getxCoord() < xmax && ev.getyCoord() > ymin && ev.getyCoord() < ymax) {
                    arr.add(node.getEvent());
                }
            }
        }, xmin, ymin, xmax, ymax);
        return arr;
    }

    public void navigate(Node node, Function func, double xmin, double ymin, double xmax, double ymax) {
        switch (node.getNodeType()) {
            case LEAF:
                func.call(this, node);
                break;

            case POINTER:
                if (intersects(xmin, ymax, xmax, ymin, node.getNe()))
                    this.navigate(node.getNe(), func, xmin, ymin, xmax, ymax);
                if (intersects(xmin, ymax, xmax, ymin, node.getSe()))
                    this.navigate(node.getSe(), func, xmin, ymin, xmax, ymax);
                if (intersects(xmin, ymax, xmax, ymin, node.getSw()))
                    this.navigate(node.getSw(), func, xmin, ymin, xmax, ymax);
                if (intersects(xmin, ymax, xmax, ymin, node.getNw()))
                    this.navigate(node.getNw(), func, xmin, ymin, xmax, ymax);
                break;
        }
    }

    /**
     * Determines whether or not some part of the node overlaps with the polygon that is being tested
     * @param left {double} the left side of the polygon
     * @param bottom {double} the lower side of the polygon
     * @param right {double} the right side of the polygon
     * @param top {double} the upper side of the polygon
     * @param node {Node} the node being tested
     * @return {boolean} whether or not the node overlaps with the polygon to some extent
     */
    private boolean intersects(double left, double bottom, double right, double top, Node node) {
        return !(node.getX() > right ||
                (node.getX() + node.getW()) < left ||
                node.getY() > bottom ||
                (node.getY() + node.getH()) < top);
    }
    /**
     * Clones the quad-tree and returns the new instance.
     * @return {QuadTree} A clone of the tree.
     */
    public QuadTree clone() {
        int x1 = this.root_.getX();
        int y1 = this.root_.getY();
        int x2 = (int)(x1 + this.root_.getW());
        int y2 = (int)(y1 + this.root_.getH());
        final QuadTree clone = new QuadTree(x1, y1, x2, y2);
        // This is inefficient as the clone needs to recalculate the structure of the
        // tree, even though we know it already.  But this is easier and can be
        // optimized when/if needed.
        this.traverse(this.root_, new Function() {
            @Override
            public void call(QuadTree quadTree, Node node) {
                clone.set(node.getEvent(), node.getEvent().getxCoord(), node.getEvent().getyCoord());
            }
        });


        return clone;
    }

    /**
     * Traverses the tree depth-first, with quadrants being traversed in clockwise
     * order (NE, SE, SW, NW).  The provided function will be called for each
     * leaf node that is encountered.
     * @param {QuadTree.Node} node The current node.
     * @param {function(QuadTree.Node)} fn The function to call
     *     for each leaf node. This function takes the node as an argument, and its
     *     return value is irrelevant.
     * @private
     */
    public void traverse(Node node, Function func) {
        switch (node.getNodeType()) {
            case LEAF:
                func.call(this, node);
                break;

            case POINTER:
                this.traverse(node.getNe(), func);
                this.traverse(node.getSe(), func);
                this.traverse(node.getSw(), func);
                this.traverse(node.getNw(), func);
                break;
        }
    }

    /**
     * Finds a leaf node with the same (x, y) coordinates as the target point, or
     * null if no point exists.
     * @param {QuadTree.Node} node The node to search in.
     * @param {number} x The x-coordinate of the point to search for.
     * @param {number} y The y-coordinate of the point to search for.
     * @return {QuadTree.Node} The leaf node that matches the target,
     *     or null if it doesn't exist.
     * @private
     */
    public Node find(Node node, int x, int y) {
        Node response = null;
        switch (node.getNodeType()) {
            case EMPTY:
                break;

            case LEAF:
                response = node.getEvent().getxCoord() == x && node.getEvent().getyCoord() == y ? node : null;
                break;

            case POINTER:
                response = this.find(this.getQuadrantForPoint(node, x, y), x, y);
                break;

            default:
                throw new QuadTreeException("Invalid nodeType");
        }
        return response;
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
}
