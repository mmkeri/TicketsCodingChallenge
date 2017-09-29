import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateEvents {

    /**
     * generates a random coordinate between the range of -10 and +10
     * @return {int}
     */
    public static int generateRandomCoordinate() {
        int random = ThreadLocalRandom.current().nextInt(-10, 10);
        return random;
    }

    /**
     * uses the randomly generated coordinates to generate a set of random points that are unique
     * @return {Set} contains a total of 20 unique Points
     */
    public static Set<Point> generateRandomPoints(){
        Set<Point> set = new HashSet<Point>();
        Point test;

        do{
            test = new Point();
            test.x = generateRandomCoordinate();
            test.y = generateRandomCoordinate();
            set.add(test);
        }
        while (set.size() < 20);

        return set;
    }

    /**
     * utilises the randomly generated Points to then create an equal number of events
     * @return {List} contains a total of 20 unique Events
     */
    public static List<Event> generateEvents(){
        Set<Point> eventPoints = generateRandomPoints();
        List<Event> generatedEvents = new ArrayList<Event>();
        int i = 1;
        for(Point p : eventPoints){
            generatedEvents.add(new Event(i, p.getX(), p.getY()));
            i++;
        }
        return generatedEvents;
    }

    /**
     * inner class acting as a container for the randomly generated coordinates
     */
    public static class Point{
        /**
         * the x-coordinate of the Point
         */
        int x;
        /**
         * the y-coordinate of the Point
         */
        int y;

        /**
         * sets the x-coordinate value for this Point
         * @param x {int}
         */
        public void setX(int x){
            this.x = x;
        }

        /**
         * sets the y-coordinate value for this Point
         * @param y {int}
         */
        public void setY(int y){
            this.y = y;
        }

        /**
         * returns the x-coordinate for this Point
         * @return {int}
         */
        public int getX(){
            return x;
        }

        /**
         * returns the y-coordinate for this Point
         * @return
         */
        public int getY(){
            return y;
        }

        /**
         * implementation of the equals method based on the x and y values for the Point
         * @param o {Object} the object being compared to this Point
         * @return {boolean}
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof Point)) return false;

            Point point = (Point) o;

            return x == point.x && y == point.y;
        }

        /**
         * implementation of the hashcode method that uses the x and y values for its generation
         * @return
         */
        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}
