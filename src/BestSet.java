import java.util.*;

class BestSet {
    private SortedMap<Integer, SortedMap<Double,List<Event>>> distanceMap = new TreeMap<>();
    private int valueCount;
    private final int capacity;

    public BestSet(int capacity){
        this.capacity = capacity;
    }

    public int getCurrentFurthestDistance() {
        return distanceMap.lastKey();
    }

    public boolean isAtCapacity(){
        return valueCount == capacity;
    }

    public void updateIfBetter(int distance, Event event){

        // we know we have not filled our best set yet, by definition this is one of the best so far
        if (this.valueCount < this.capacity) {
            this.add(distance, event);
            return;
        }

        // otherwise determine worst
        int worstDistance = distanceMap.lastKey();
        double worstPrice = distanceMap.get(worstDistance).lastKey();

        if(distance < worstDistance ||
            event.getLowestPricedTicket().getPrice() < worstPrice) {

            add(distance, event);
            removeWorstOfBest();
        }
    }

    private void add(int distance, Event event) {
        double ticketPrice = event.getLowestPricedTicket().getPrice();

        SortedMap<Double, List<Event>> priceMapForProvidedDistance = distanceMap.get(distance);

        if (priceMapForProvidedDistance == null) {
            priceMapForProvidedDistance = new TreeMap<>();
            distanceMap.put(distance, priceMapForProvidedDistance);
        }

        List<Event> eventListForPricePoint = priceMapForProvidedDistance.get(ticketPrice);

        if (eventListForPricePoint == null) {
            eventListForPricePoint = new ArrayList<>();
            priceMapForProvidedDistance.put(ticketPrice, eventListForPricePoint);
        }

        // now we are sure we have an event list, and it's in all the requisite maps
        eventListForPricePoint.add(event);
        this.valueCount++;
    }

    private void removeWorstOfBest() {
        int worstDistance = distanceMap.lastKey();

        // a map of all the events by price, for all being at the worst distance
        SortedMap<Double, List<Event>> worstDistancePriceMap = distanceMap.get(worstDistance);

        double worstPrice = worstDistancePriceMap.lastKey();

        List<Event> worstPriceEvents = worstDistancePriceMap.get(worstPrice);

        // remove the last event amongs the worst price
        worstPriceEvents.remove(worstPriceEvents.size() - 1);

        if (worstPriceEvents.size() == 0) {
            // that was the only event at that price, we need to remove it from the priceMap too
            worstDistancePriceMap.remove(worstPrice);
        }

        if (worstDistancePriceMap.size() == 0) {
            // that was the only event at that distance, we need to remove it from the distanceMap too
            distanceMap.remove(worstDistance);
        }

        // we've removed an event (and any supporting organisational structures)
        this.valueCount--;
    }

    public int getValueCount() {
        return this.valueCount;
    }

    public List<Map.Entry<Integer,Event>> flattened() {
        List<Map.Entry<Integer, Event>> results = new ArrayList<>(this.getValueCount());
        for(Map.Entry<Integer, SortedMap<Double, List<Event>>> distanceMapEntry : distanceMap.entrySet()){
            for(List<Event> eventList : distanceMapEntry.getValue().values()){
                for(Event event : eventList) {
                    results.add(new AbstractMap.SimpleImmutableEntry<Integer, Event>(distanceMapEntry.getKey(), event));
                }
            }
        }
        return results;
    }
}
