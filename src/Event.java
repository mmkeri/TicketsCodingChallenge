import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Event {

    /**
     * unique identifier for this event
     */
    private int eventCode;
    /**
     * collection of all the tickets available for this event ordered by proce in descending order
     */
    private TreeMap<Double, List<Ticket>> eventTickets = new TreeMap<Double, List<Ticket>>();
    /**
     * the x-coordinate for the location of where the event is occurring
     */
    private int xCoord;
    /**
     * the y-coordinate for the location of where the event is occurring
     */
    private int yCoord;

    /**
     * a constructor for the Event object that does not include the list of tickets available for the event
     * @param eventCode {int} unique identifier for the event
     * @param xCoord {int} x-coordinate for the location of the event
     * @param yCoord {int} y-coordinate for the location of the event
     */
    public Event(int eventCode, int xCoord, int yCoord){
        this.eventCode = eventCode;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * a constructor for the Event object that does include the list of tickets available for the event
     * @param eventCode {int} unique identifier for the event
     * @param xCoord {int} x-coordinate for the location of the event
     * @param yCoord {int} y-coordinate for the location of the event
     * @param eventTickets {list} collection of all the tickets available at this event
     */
    public Event (int eventCode, int xCoord, int yCoord, TreeMap<Double, List<Ticket>> eventTickets){
        this.eventTickets = eventTickets;
        this.eventCode = eventCode;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * returns the unique identifying code for this event
     * @return {int} event code
     */
    public int getEventCode(){
        return eventCode;
    }

    /**
     * returns the x-coordinate for the location of this event
     * @return {int} x-coordinate
     */
    public int getxCoord(){
        return xCoord;
    }

    /**
     * returns the y-coordinate for the location of this event
     * @return {int} y-coordinate
     */
    public int getyCoord(){
        return yCoord;
    }

    /**
     * adds a ticket to the Map
     * @param price {double} the price of the ticket. Used as the key for the map
     * @param ticket {Ticket} the ticket object
     * @return {TreeMap} Map of Tickets sorted in descending order by price
     */
    public TreeMap<Double, List<Ticket>> addTicket(double price, Ticket ticket){
        if(eventTickets.get(price) == null){
            List<Ticket> ticketList = new ArrayList<Ticket>();
            ticket.setEventCode(this.eventCode);
            ticketList.add(ticket);
            eventTickets.put(price, ticketList);
        } else {
            List<Ticket> ticketList = eventTickets.get(price);
            ticket.setEventCode(this.eventCode);
            ticketList.add(ticket);
            eventTickets.put(price, ticketList);
        }
        return eventTickets;
    }

    /**
     * returns the Map of tickets associated with this event
     * @return {Map} containing all the tickets for this event in descending order by price
     */
    public TreeMap<Double, List<Ticket>> getEventTickets(){
        return eventTickets;
    }

    /**
     * returns the first Ticket from the Map which will be the lowest priced one
     * @return {Ticket}
     */
    public Ticket getLowestPricedTicket(){
        return eventTickets.firstEntry().getValue().get(0);
    }

    /**
     * implementation of the equals method based on the x and y coordinates of the event
     * @param o {Object} the object being tested for equality
     * @return {boolean}
     */
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (!Event.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        final Event other = (Event) o;
        if (this.eventCode != other.eventCode) {
            return false;
        }
        if(this.xCoord != other.xCoord){
            return false;
        }
        if(this.yCoord != other.yCoord){
            return false;
        }
        return true;
    }

    /**
     * override of the toString method
     * @return {String}
     */
    @Override
    public String toString() {
        return "Event{" +
                "eventCode=" + eventCode +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ". The least expensive ticket for this event is " + this.getLowestPricedTicket() +
                '}';
    }
}
