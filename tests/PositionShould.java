import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PositionShould {

    Position testPosition = new Position(5, 8);

    @Test
    public void returnTheCorrectXCoordinate(){
        assertEquals(5, testPosition.getxCoord());
    }

    @Test
    public void returnTheCorrectYCoordinate(){
        assertEquals(8, testPosition.getyCoord());
    }
}
