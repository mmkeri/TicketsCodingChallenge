public class Ticket implements Comparable<Ticket>{

    /**
     * Price of a ticket represented by a double
     */
    private double price;
    /**
     * The unique identifier code for an event represented by an integer
     */
    private int eventCode;

    /**
     * Constructor for the Ticket object
     * @param price {double} ticket price
     */
    public Ticket(double price){
        this.price = price;
    }

    /**
     * Returns the price associated with this Ticket
     * @return {double}
     */
    public double getPrice(){
        return price;
    }

    /**
     * Sets the event code associated with this Ticket
     * @param code {int}
     */
    public void setEventCode(int code){
        this.eventCode = code;
    }

    /**
     * Returns the event code associated with this Ticket
     * @return {int}
     */
    public int getEventCode(){
        return eventCode;
    }

    /**
     * Implementation of the compareTo method based on the ticket price
     * @param o {Ticket} the other ticketed being compared to this object
     * @return {int}
     */
    @Override
    public int compareTo(Ticket o) {
            return (int) (this.price - o.price);
    }

    /**
     * Implementation of the equals method based on ticket price and event code
     * @param o {Object} the object being compared to this Ticket
     * @return {boolean} whether or not the two objects are in fact equivalent
     */
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (!Ticket.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        final Ticket other = (Ticket) o;
        if (this.eventCode != other.eventCode) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        return true;
    }
}
