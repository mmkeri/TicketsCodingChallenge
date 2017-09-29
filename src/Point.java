public class Point implements Comparable {

    private int x;
    private int y;
    private Event event;

    /**
     * Creates a new point object.
     *
     * @param {double} x The x-coordinate of the point.
     * @param {double} y The y-coordinate of the point.
     * @param {Object} opt_value Optional value associated with the point.
     */
    public Point(int x, int y, Event event) {
        this.x = x;
        this.y = y;
        this.event = event;
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

    public Event getValue() {
        return event;
    }

    public void setValue(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public int compareTo(Object o) {
        Point tmp = (Point) o;
        if (this.x < tmp.x) {
            return -1;
        } else if (this.x > tmp.x) {
            return 1;
        } else {
            if (this.y < tmp.y) {
                return -1;
            } else if (this.y > tmp.y) {
                return 1;
            }
            return 0;
        }
    }
}
