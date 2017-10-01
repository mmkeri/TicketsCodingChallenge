import javax.swing.*;
import java.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.JFrame.*;

public class GuiInterface{

    JButton enterButton;
    JButton clearButton;
    JTextField xCoord;
    JTextField yCoord;
    JLabel result1;
    JLabel result2;
    JLabel result3;
    JLabel result4;
    JLabel result5;
    QuadTree quadTree;
    List<JLabel> resultLabelList;

    public void createTheFrame(QuadTree quadTree) {

        this.quadTree = quadTree;
        JFrame frame = new JFrame("Search for Events and Tickets");
        enterButton = new JButton("Start Search");
        clearButton = new JButton("Clear Search");
        xCoord = new JTextField(2);
        yCoord = new JTextField(2);
        JLabel xCoordLabel = new JLabel("X-coordinate");
        JLabel yCoordLabel = new JLabel("Y-coordinate");
        result1 = new JLabel("");
        result2 = new JLabel("");
        result3 = new JLabel("");
        result4 = new JLabel("");
        result5 = new JLabel("");
        resultLabelList = new ArrayList<JLabel>();
        resultLabelList.add(result1);
        resultLabelList.add(result2);
        resultLabelList.add(result3);
        resultLabelList.add(result4);
        resultLabelList.add(result5);


        enterButton.addActionListener(new EnterListener());
        clearButton.addActionListener(new ClearListener());

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel btmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel centerPanel = new JPanel();
        /*
        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(result1)
                                .addComponent(result2)
                                .addComponent(result3))
                        .addComponent(result4)
                        .addComponent(result5)
        );
        */

        btmPanel.add(enterButton);
        btmPanel.add(clearButton);

        topPanel.add(xCoordLabel);
        topPanel.add(xCoord);
        topPanel.add(yCoordLabel);
        topPanel.add(yCoord);


        centerPanel.add(result1, 0);
        centerPanel.add(result2, 1);
        centerPanel.add(result3, 2);
        centerPanel.add(result4, 3);
        centerPanel.add(result5, 4);


        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(btmPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(600,300);


        frame.setVisible(true);
    }

    class EnterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userXCoord = Integer.parseInt(xCoord.getText());
            int userYCoord = Integer.parseInt(yCoord.getText());
            List<Event> returnedEvents = SearchQuadTree.searchQuadTree(userXCoord, userYCoord, quadTree);
            TreeMap<Integer, Event> lowestEvents = findCheapestEvents(returnedEvents);
            Set<Map.Entry<Integer, Event>> lowEvents = lowestEvents.descendingMap().entrySet();

            Iterator<Map.Entry<Integer, Event>> itr = lowestEvents.descendingMap().entrySet().iterator();
            Map.Entry<Integer, Event> firstEntry = itr.next();
            result1.setText("Event " + firstEntry.getValue().getEventCode() + " - $" + firstEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + firstEntry.getKey());

            Map.Entry<Integer, Event> secondEntry = itr.next();
            result2.setText("Event " + secondEntry.getValue().getEventCode() + " - $" + secondEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + secondEntry.getKey());

            Map.Entry<Integer, Event> thirdEntry = itr.next();
            result3.setText("Event " + thirdEntry.getValue().getEventCode() + " - $" + thirdEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + thirdEntry.getKey());

            Map.Entry<Integer, Event> fourthEntry = itr.next();
            result4.setText("Event " + fourthEntry.getValue().getEventCode() + " - $" + fourthEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + fourthEntry.getKey());

            Map.Entry<Integer, Event> fifthEntry = itr.next();
            result5.setText("Event " + fifthEntry.getValue().getEventCode() + " - $" + fifthEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + fifthEntry.getKey());

            /*
            Map.Entry<Integer, Event> firstEntry = lowestEvents.firstEntry();
            result1.setText("Event " + firstEntry.getValue().getEventCode() + " - $" + firstEntry.getValue().getLowestPricedTicket().getPrice()
            + ", Distance " + firstEntry.getKey());

            Map.Entry<Integer, Event> secondEntry = lowestEvents.ceilingEntry(firstEntry.getKey());
            result2.setText("Event " + secondEntry.getValue().getEventCode() + " - $" + secondEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + secondEntry.getKey());

            Map.Entry<Integer, Event> thirdEntry = lowestEvents.ceilingEntry(secondEntry.getKey());
            result3.setText("Event " + thirdEntry.getValue().getEventCode() + " - $" + thirdEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + thirdEntry.getKey());

            Map.Entry<Integer, Event> fourthEntry = lowestEvents.ceilingEntry(thirdEntry.getKey());
            result4.setText("Event " + fourthEntry.getValue().getEventCode() + " - $" + fourthEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + fourthEntry.getKey());

            Map.Entry<Integer, Event> fifthEntry = lowestEvents.ceilingEntry(fourthEntry.getKey());
            result5.setText("Event " + fifthEntry.getValue().getEventCode() + " - $" + fifthEntry.getValue().getLowestPricedTicket().getPrice()
                    + ", Distance " + fifthEntry.getKey());
                    */
        }
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xCoord.setText("");
            yCoord.setText("");
            result1.setText("");
            result2.setText("");
            result3.setText("");
            result4.setText("");
            result5.setText("");
        }
    }

    private TreeMap<Integer, Event> findCheapestEvents (List<Event> returnedEvents){
        Position userPosition = new Position(Integer.parseInt(xCoord.getText()), Integer.parseInt(yCoord.getText()));
        TreeMap<Integer, Event> cheapestEvents = new TreeMap<Integer, Event>();
        for(Event e : returnedEvents){
            cheapestEvents.put(ManhattanDistances.calculateManhattanDistance(e, userPosition), e);
        }
        return cheapestEvents;
    }
}
