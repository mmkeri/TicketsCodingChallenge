import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ManhattanDistancesShould {

    Position customerPosition = new Position(-4, 6);

    @Test
    public void returnCorrectDistanceInQuadrantOne(){
        Event quadOneEvent = new Event(1, 2, 9);
        assertEquals(9, ManhattanDistances.calculateManhattanDistance(quadOneEvent, customerPosition));
    }

    @Test
    public void returnCorrectDistanceInQuadrantTwo(){
        Event quadTwoEvent = new Event(1, 5, -4);
        assertEquals(19, ManhattanDistances.calculateManhattanDistance(quadTwoEvent, customerPosition));
    }

    @Test
    public void returnCorrectDistanceInQuadrantThree(){
        Event quadThreeEvent = new Event(1, -3, -8);
        assertEquals(15, ManhattanDistances.calculateManhattanDistance(quadThreeEvent, customerPosition));
    }

    @Test
    public void returnCorrectDistanceInQuadrantFour(){
        Event quadFourEvent = new Event(1, -7, 3);
        assertEquals(6, ManhattanDistances.calculateManhattanDistance(quadFourEvent, customerPosition));
    }

    @Test
    public void returnZeroWhenTheEventIsAtTheSamePositionAsTheCustomer(){
        Event localEvent = new Event(1, -4, 6);
        assertEquals(0, ManhattanDistances.calculateManhattanDistance(localEvent, customerPosition));
    }
}
