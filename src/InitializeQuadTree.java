import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by mmkeri on 29/09/2017.
 */
public class InitializeQuadTree {

    public static QuadTree InitializeQuadTree(QuadTree quadTree){
        List<Ticket> tickets = GenerateSeedTickets.generateSeedTickets();
        List<Event> events = GenerateEvents.generateEvents();
        int i = 0;
        for(Event e : events){
            for(int j = 0; j < 5; j++){
                e.addTicket(tickets.get(i).getPrice(), tickets.get(i));
                i++;
            }
            quadTree.set(e, e.getxCoord(), e.getyCoord());
        }
        return quadTree;
    }
}
