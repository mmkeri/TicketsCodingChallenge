public class Node {

    /**
     * the x-coordinate for this node
     */
    private int x;
    /**
     * the y-coordinate for this node
     */
    private int y;
    /**
     * the width of this node
     */
    private double w;
    /**
     * the height of this node
     */
    private double h;
    /**
     * the parent node for this node. May be null if the node is the root
     */
    private Node opt_parent;
    /**
     * the Event object associated with this node
     */
    private Event event;
    /**
     * the type for this node (root, pointer or leaf)
     */
    private NodeType nodetype = NodeType.EMPTY;
    /**
     * the node representing the northwest quadrant of the parent node
     */
    private Node nw;
    /**
     * the node representing the northeast quadrant of the parent node
     */
    private Node ne;
    /**
     * the node representing the southwest quadrant of the parent node
     */
    private Node sw;
    /**
     * the node representing the southeast quadrant of the parent node
     */
    private Node se;

    /**
     * Constructs a new quad tree node.
     *
     * @param {int} x X-coordinate of node.
     * @param {int} y Y-coordinate of node.
     * @param {double} w Width of node.
     * @param {double} h Height of node.
     * @param {Node}   opt_parent Optional parent node.
     * @constructor
     */
    public Node(int x, int y, double w, double h, Node opt_parent) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.opt_parent = opt_parent;
    }

    /**
     * returns the x-coordinate for this node
     * @return {int}
     */
    public int getX() {
        return x;
    }

    /**
     * sets the x-coordinate for this node
     * @param x {int}
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * returns the y-coordinate for this node
     * @return {int}
     */
    public int getY() {
        return y;
    }

    /**
     * sets the y-coordinate for this node
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * returns the width of this node
     * @return {double}
     */
    public double getW() {
        return w;
    }

    /**
     * sets the width of this node
     * @param w {double}
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * returns the height of this node
     * @return {double}
     */
    public double getH() {
        return h;
    }

    /**
     * sets the height of this node
     * @param h {double}
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * returns the parent node for this node
     * @return {Node}
     */
    public Node getParent() {
        return opt_parent;
    }

    /**
     * sets the parent node for this node
     * @param opt_parent {Node}
     */
    public void setParent(Node opt_parent) {
        this.opt_parent = opt_parent;
    }

    /**
     * sets the Event that is associated with this node
     * @param event {Event}
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * returns the Event that is associated with this node
     * @return {Event}
     */
    public Event getEvent() {
        return this.event;
    }

    /**
     * sets the type for this node based on the nodetype enum
     * @param nodetype {enum}
     */
    public void setNodeType(NodeType nodetype) {
        this.nodetype = nodetype;
    }

    /**
     * returns the type for this node based on the nodetype enum
     * @return {nodetype enum}
     */
    public NodeType getNodeType() {
        return this.nodetype;
    }

    /**
     * sets the northwest inner node for this node
     * @param nw {Node}
     */
    public void setNw(Node nw) {
        this.nw = nw;
    }

    /**
     * sets the northeast inner node for this node
     * @param ne {Node}
     */
    public void setNe(Node ne) {
        this.ne = ne;
    }

    /**
     * sets the southwest inner node for this node
     * @param sw {Node}
     */
    public void setSw(Node sw) {
        this.sw = sw;
    }

    /**
     * sets the southeast inner node for this node
     * @param se {Node}
     */
    public void setSe(Node se) {
        this.se = se;
    }

    /**
     * returns the northeast node of this node
     * @return {Node}
     */
    public Node getNe() {
        return ne;
    }

    /**
     * returns the northwest node of this node
     * @return {Node}
     */
    public Node getNw() {
        return nw;
    }

    /**
     * returns the southwest node of this node
     * @return
     */
    public Node getSw() {
        return sw;
    }

    /**
     * returns the southeast node of this node
     * @return
     */
    public Node getSe() {
        return se;
    }
}
