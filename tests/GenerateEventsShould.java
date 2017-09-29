import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class GenerateEventsShould {

    private GenerateEvents testGenerator = new GenerateEvents();

    @Test
    public void returnARandomNumberBetweenNegativeTenAndPositiveTen(){
        int result = GenerateEvents.generateRandomCoordinate();
        assertTrue(result >= -10);
        assertTrue(result <= 10);
    }

    @Test
    public void generateAListOf20Points(){
        assertTrue(testGenerator.generateRandomPoints().size() == 20);
    }

    @Test
    public void generateAListOf20Events(){
        assertTrue(testGenerator.generateEvents().size() == 20);
    }

    @Test
    public void generateDifferentHashCodesForDifferentPoints(){
        GenerateEvents.Point pointOne =  new GenerateEvents.Point();
        GenerateEvents.Point pointTwo = new GenerateEvents.Point();
        pointOne.setX(5);
        pointOne.setY(7);
        pointTwo.setX(2);
        pointTwo.setY(9);
        assertFalse(pointOne.hashCode() == pointTwo.hashCode());
    }

    @Test
    public void generateTheSameHashCodeForPointsWithTheSameCoordinates(){
        GenerateEvents.Point pointOne =  new GenerateEvents.Point();
        GenerateEvents.Point pointTwo = new GenerateEvents.Point();
        pointOne.setX(3);
        pointOne.setY(7);
        pointTwo.setX(3);
        pointTwo.setY(7);
        assertTrue(pointOne.hashCode() == pointTwo.hashCode());
    }

    @Test
    public void indicateThatTwoPointsAreEqualIfTheyHaveTheSameCoordinates(){
        GenerateEvents.Point pointOne =  new GenerateEvents.Point();
        GenerateEvents.Point pointTwo = new GenerateEvents.Point();
        pointOne.setX(3);
        pointOne.setY(7);
        pointTwo.setX(3);
        pointTwo.setY(7);
        assertTrue(pointOne.equals(pointTwo));
    }

    @Test
    public void indicateThatTwoPointsAreNotEqualIfTheyHaveDifferentCoordinates(){
        GenerateEvents.Point pointOne =  new GenerateEvents.Point();
        GenerateEvents.Point pointTwo = new GenerateEvents.Point();
        pointOne.setX(5);
        pointOne.setY(7);
        pointTwo.setX(2);
        pointTwo.setY(9);
        assertFalse(pointOne.equals(pointTwo));
    }
}
