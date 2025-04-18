package org.example.GUI;

import org.example.BusinessLogic.Scheduler;
import org.example.BusinessLogic.SelectionPolicy;
import org.example.BusinessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrontPage extends JFrame {
    private final JLabel enterDataLabel;
    private final JLabel enterNrOfClientsLabel;
    private final JTextField enterNrOfClientsTextField;
    private final JLabel enterNrOfQueuesLabel;
    private final JTextField enterNrOfQueuesTextField;
    private final JLabel enterSimulationTimeLabel;
    private final JTextField enterSimulationTimeTextField;
    private final JLabel minArrivalTimeLabel;
    private final JTextField minArrivalTimeTextField;
    private final JLabel maxArrivalTimeLabel;
    private final JTextField maxArrivalTimeTextField;
    private final JLabel minServiceTimeLabel;
    private final JTextField minServiceTimeTextField;
    private final JLabel maxServiceTimeLabel;
    private final JTextField maxServiceTimeTextField;
    private final JLabel strategyLabel;
    private final JRadioButton queueRadioButton;
    private final JRadioButton timeRadioButton;
    private final JButton startSimulationButton;

    public FrontPage() {
        enterDataLabel = new JLabel("Enter data:");
        enterNrOfClientsLabel = new JLabel("Number of clients:");
        enterNrOfClientsTextField = new JTextField();
        enterNrOfQueuesLabel = new JLabel("Number of queues:");
        enterNrOfQueuesTextField = new JTextField();
        enterSimulationTimeLabel = new JLabel("Simulation time:");
        enterSimulationTimeTextField = new JTextField();
        minArrivalTimeLabel = new JLabel("Minimum arrival time:");
        minArrivalTimeTextField = new JTextField();
        maxArrivalTimeLabel = new JLabel("Maximum arrival time:");
        maxArrivalTimeTextField = new JTextField();
        minServiceTimeLabel = new JLabel("Minimum service time:");
        minServiceTimeTextField = new JTextField();
        maxServiceTimeLabel = new JLabel("Maximum service time:");
        maxServiceTimeTextField = new JTextField();
        strategyLabel = new JLabel("Choose strategy:");
        queueRadioButton = new JRadioButton("Shortest queue");
        timeRadioButton = new JRadioButton("Shortest time");
        startSimulationButton = new JButton("Start");

        JPanel topPanel = new JPanel();
        topPanel.add(enterDataLabel);

        JPanel clientsPanel = new JPanel(new GridLayout(1,2));
        clientsPanel.add(enterNrOfClientsLabel);
        clientsPanel.add(enterNrOfClientsTextField);

        JPanel queuesPanel = new JPanel(new GridLayout(1,2));
        queuesPanel.add(enterNrOfQueuesLabel);
        queuesPanel.add(enterNrOfQueuesTextField);

        JPanel simulationTimePanel = new JPanel(new GridLayout(1,2));
        simulationTimePanel.add(enterSimulationTimeLabel);
        simulationTimePanel.add(enterSimulationTimeTextField);

        JPanel arrivalTimePanel = new JPanel(new GridLayout(2,2));
        arrivalTimePanel.add(minArrivalTimeLabel);
        arrivalTimePanel.add(minArrivalTimeTextField);
        arrivalTimePanel.add(maxArrivalTimeLabel);
        arrivalTimePanel.add(maxArrivalTimeTextField);

        JPanel serviceTimePanel = new JPanel(new GridLayout(2,2));
        serviceTimePanel.add(minServiceTimeLabel);
        serviceTimePanel.add(minServiceTimeTextField);
        serviceTimePanel.add(maxServiceTimeLabel);
        serviceTimePanel.add(maxServiceTimeTextField);

        JPanel strategyPanel = new JPanel(new GridLayout(1,3));
        strategyPanel.add(strategyLabel);
        strategyPanel.add(queueRadioButton);
        strategyPanel.add(timeRadioButton);

        ButtonGroup strategyGroup = new ButtonGroup();
        strategyGroup.add(queueRadioButton);
        strategyGroup.add(timeRadioButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startSimulationButton);

        setLayout(new GridLayout(8,1));
        add(topPanel);
        add(clientsPanel);
        add(queuesPanel);
        add(simulationTimePanel);
        add(arrivalTimePanel);
        add(serviceTimePanel);
        add(strategyPanel);
        add(buttonPanel);

        startSimulationButton.addActionListener(new StartSimulationListener(){});

        setTitle("Front page");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public int getNrOfClients() {
        return Integer.parseInt(enterNrOfClientsTextField.getText());
    }

    public int getNrOfQueues() {
        return Integer.parseInt(enterNrOfQueuesTextField.getText());
    }

    public int getSimulationTime() {
        return Integer.parseInt(enterSimulationTimeTextField.getText());
    }

    public int getMinArrivalTime() {
        return Integer.parseInt(minArrivalTimeTextField.getText());
    }

    public int getMaxArrivalTime() {
        return Integer.parseInt(maxArrivalTimeTextField.getText());
    }

    public int getMinServiceTime() {
        return Integer.parseInt(minServiceTimeTextField.getText());
    }

    public int getMaxServiceTime() {
        return Integer.parseInt(maxServiceTimeTextField.getText());
    }

    public class StartSimulationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int nrOfClients = getNrOfClients();
            int nrOfQueues = getNrOfQueues();
            int simulationTime = getSimulationTime();
            int minArrivalTime = getMinArrivalTime();
            int maxArrivalTime = getMaxArrivalTime();
            int minServiceTime = getMinServiceTime();
            int maxServiceTime = getMaxServiceTime();

            SelectionPolicy policy = queueRadioButton.isSelected() ? SelectionPolicy.SHORTEST_QUEUE : SelectionPolicy.SHORTEST_TIME;

            Scheduler scheduler = new Scheduler(nrOfQueues, nrOfClients);
            scheduler.changeStrategy(policy);

            SimulationFrame simulationFrame = new SimulationFrame();
            simulationFrame.setVisible(true);

            SimulationManager simulationManager = new SimulationManager(simulationTime, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime,
                                                                         nrOfQueues, nrOfClients, policy, scheduler, simulationFrame, new ArrayList<>());
            Thread mainThread = new Thread(simulationManager);
            mainThread.start();

            simulationManager.generateRandomTasks(nrOfClients);
        }
    }
}
