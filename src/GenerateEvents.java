import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateEvents {

    public static int generateRandomCoordinate() {
        int random = ThreadLocalRandom.current().nextInt(-10, 10 + 1);
        return random;
    }

    public Set<Point> generateRandomPoints(){
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

    public List<Event> generateEvents(){
        Set<Point> eventPoints = generateRandomPoints();
        List<Event> generatedEvents = new ArrayList<Event>();
        int i = 1;
        for(Point p : eventPoints){
            generatedEvents.add(new Event(i, p.getX(), p.getY()));
            i++;
        }
        return generatedEvents;
    }

    public static class Point{
        int x;
        int y;

        public void setX(int x){
            this.x = x;
        }

        public void setY(int y){
            this.y = y;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof Point)) return false;

            Point point = (Point) o;

            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}
