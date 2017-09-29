import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TicketShould {

    private Ticket testTicket = new Ticket(25.30);

    @Test
    public void returnTheCorrectPrice(){
        assertEquals(25.30, testTicket.getPrice());
    }

    @Test
    public void returnTheCorrectEventCode(){
        testTicket.setEventCode(1);
        assertEquals(1, testTicket.getEventCode());
    }

    @Test
    public void considerTwoTicketsOfTheSamePriceAndForTheSameEventToBeEqual(){
        Ticket dupTicket = new Ticket(25.30);
        assertTrue(dupTicket.equals(testTicket));
    }

    @Test
    public void considerTwoTicketsOfDifferentPricesButForTheSameEventToNotBeEqual(){
        Ticket dupTicket = new Ticket(30.00);
        assertFalse(dupTicket.equals(testTicket));
    }

    @Test
    public void considerTwoTicketsOfTheSamePriceButDifferentEventsToNotBeEqual(){
        Ticket dupTicket = new Ticket(25.30);
        dupTicket.setEventCode(2);
        assertFalse(dupTicket.equals(testTicket));
    }

    @Test
    public void returnANegativeNumberWhenTheTicketPriceOfTheSecondTicketIsHigher(){
        Ticket highTicket = new Ticket(30.00);
        assertTrue(testTicket.compareTo(highTicket) < 0);
    }

    @Test
    public void returnAPositiveNumberWhenTheTicketPricOfTheSecondTicketIsLower(){
        Ticket lowTicket = new Ticket(20.00);
        assertTrue(testTicket.compareTo(lowTicket) > 0);
    }

    @Test
    public void returnZeroIfTheTicketPriceOfTheSecondTicketIsEqual(){
        Ticket equalTicket = new Ticket(25.30);
        assertTrue(testTicket.compareTo(equalTicket) == 0);
    }
}
