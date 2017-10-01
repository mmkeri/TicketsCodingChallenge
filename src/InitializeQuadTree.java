import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InitializeQuadTree {

    /**
     * utilises the methods for generating random tickets and unique, random events that are then added to the QuadTree
     * @param quadTree {QuadTree}
     * @return {QuadTree}
     */
    public static QuadTree InitializeQuadTree(QuadTree quadTree){
        List<Ticket> tickets = GenerateSeedTickets.generateSeedTickets();
        List<Event> events = GenerateEvents.generateEvents();
        int i = 0;
        for(Event e : events){
            for(int j = 0; j < 5; j++){
                e.addTicket(tickets.get(i).getPrice(), tickets.get(i));
                i++;
            }
            quadTree.set(e);
        }
        return quadTree;
    }
}
