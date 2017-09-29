import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GenerateSeedTickets {

    /**
     * generates 100 tickets with random values between 0 and 100
     * @return {List} list of tickets
     */
    public static List<Ticket> generateSeedTickets(){
        List<Ticket> seedTickets = new ArrayList<Ticket>();
        for(int i = 0; i < 100; i++){
            Double num = (Math.random() * 100) * 100;
            long interim = Math.round(num);
            num = (double)interim / 100;
            Ticket generatedTicket = new Ticket(num);
            seedTickets.add(generatedTicket);
        }
        return seedTickets;
    }
}
