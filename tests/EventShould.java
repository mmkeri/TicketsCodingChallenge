import org.junit.Test;

import java.util.List;
import java.util.TreeMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class EventShould {

    private Ticket ticketOne = new Ticket(35.90);
    private Ticket ticketTwo = new Ticket(24.50);
    private Ticket ticketThree = new Ticket(36.20);
    private Ticket ticketFour = new Ticket(27.80);
    private Event testEvent = new Event(1, 5, 7);

    @Test
    public void addOneTicketToTheEventsList(){
        assertEquals(0, testEvent.getEventTickets().size());
        testEvent.addTicket(ticketOne.getPrice(), ticketOne);
        assertEquals(1, testEvent.getEventTickets().size());
    }

    @Test
    public void addTwoTicketsToTheEventsList(){
        assertEquals(0, testEvent.getEventTickets().size());
        testEvent.addTicket(ticketOne.getPrice(), ticketOne);
        testEvent.addTicket(ticketTwo.getPrice(), ticketTwo);
        assertEquals(2, testEvent.getEventTickets().size());
    }

    @Test
    public void returnTheSameTicketThatWasAddedToTheList(){
        testEvent.addTicket(ticketOne.getPrice(), ticketOne);
        List<Ticket> result = testEvent.getEventTickets().get(ticketOne.getPrice());
        assertEquals(ticketOne, result.get(0));
    }

    @Test
    public void returnTheLowestPricedTicketFromTheEventList(){
        testEvent.addTicket(ticketOne.getPrice(), ticketOne);
        testEvent.addTicket(ticketTwo.getPrice(), ticketTwo);
        testEvent.addTicket(ticketThree.getPrice(), ticketThree);
        testEvent.addTicket(ticketFour.getPrice(), ticketFour);
        assertEquals(ticketTwo, testEvent.getLowestPricedTicket());
    }

    @Test
    public void returnTheMapOfEventTickets(){
        testEvent.addTicket(ticketOne.getPrice(), ticketOne);
        testEvent.addTicket(ticketTwo.getPrice(), ticketTwo);
        testEvent.addTicket(ticketThree.getPrice(), ticketThree);
        testEvent.addTicket(ticketFour.getPrice(), ticketFour);
        TreeMap<Double, List<Ticket>> result = testEvent.getEventTickets();
        assertTrue(result.containsKey(ticketOne.getPrice()));
        assertTrue(result.containsKey(ticketTwo.getPrice()));
        assertTrue(result.containsKey(ticketThree.getPrice()));
        assertTrue(result.containsKey(ticketFour.getPrice()));
    }
    @Test
    public void returnTheCorrectXCoordinateForTheEvent(){
        assertEquals(5, testEvent.getxCoord());
    }

    @Test
    public void returnTheCorrectYCoordinateForTheEvent(){
        assertEquals(7, testEvent.getyCoord());
    }

    @Test
    public void returnTheCorrectEventCode(){assertEquals(1, testEvent.getEventCode());}

    @Test
    public void returnTrueIfTwoEventsHaveTheSameEventCodeAndSameCoordinates(){
        Event dupEvent = new Event(1, 5, 7);
        assertTrue(testEvent.equals(dupEvent));
    }

    @Test
    public void returnFalseIfTheTwoEventsHaveDifferentEventCodesButOtherwiseTheSame(){
        Event wrongCodeEvent = new Event(2, 5, 7);
        assertFalse(testEvent.equals(wrongCodeEvent));
    }

    @Test
    public void returnFalseIfTheTwoEventsHaveDifferentXCoordinatesButOtherwiseTheSame(){
        Event wrongXCoordEvent = new Event(1, 7, 7);
        assertFalse(testEvent.equals(wrongXCoordEvent));
    }

    @Test
    public void returnFalseIfTheTwoEventsHaveDifferentYCoordinatesButOtherwiseTheSame(){
        Event wrongYCoordEvent = new Event(1, 5, 9);
    }

    @Test
    public void returnFalseIfTheTwoEventsHaveDifferentEventCodesAndXCoordsButSameYCoord(){
        Event sameYCoordEvent = new Event(2, 7, 7);
    }

    @Test
    public void returnFalseIfTheEventsHaveDifferentEventCodesAndYCoordsButSameXCoord(){
        Event sameXCoordEvent = new Event(2, 5, 9);
    }
}
