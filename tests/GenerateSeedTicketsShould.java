import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class GenerateSeedTicketsShould {

    private List<Ticket> seedTickets = GenerateSeedTickets.generateSeedTickets();
    @Test
    public void createOneHundredSeedTickets(){
        assertTrue(seedTickets.size() == 100);
    }
}
