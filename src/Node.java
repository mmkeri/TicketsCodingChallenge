public class Node {

    private int x;
    private int y;
    private int w;
    private int h;
    private Node opt_parent;
    private Point point;
    private NodeType nodetype = NodeType.EMPTY;
    private Node nw;
    private Node ne;
    private Node sw;
    private Node se;

    /**
     * Constructs a new quad tree node.
     *
     * @param {double} x X-coordinate of node.
     * @param {double} y Y-coordinate of node.
     * @param {double} w Width of node.
     * @param {double} h Height of node.
     * @param {Node}   opt_parent Optional parent node.
     * @constructor
     */
    public Node(int x, int y, int w, int h, Node opt_parent) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.opt_parent = opt_parent;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Node getParent() {
        return opt_parent;
    }

    public void setParent(Node opt_parent) {
        this.opt_parent = opt_parent;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return this.point;
    }

    public void setNodeType(NodeType nodetype) {
        this.nodetype = nodetype;
    }

    public NodeType getNodeType() {
        return this.nodetype;
    }


    public void setNw(Node nw) {
        this.nw = nw;
    }

    public void setNe(Node ne) {
        this.ne = ne;
    }

    public void setSw(Node sw) {
        this.sw = sw;
    }

    public void setSe(Node se) {
        this.se = se;
    }

    public Node getNe() {
        return ne;
    }

    public Node getNw() {
        return nw;
    }

    public Node getSw() {
        return sw;
    }

    public Node getSe() {
        return se;
    }
}
