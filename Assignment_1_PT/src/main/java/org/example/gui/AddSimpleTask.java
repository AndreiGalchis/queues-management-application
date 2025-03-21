package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.SimpleTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSimpleTask extends JFrame {
    private final int selectedID;
    private final TaskManagement taskManagement;
    private final JTextField startHourTextField;
    private final JTextField endHourTextField;
    private final JButton addButton;

    public AddSimpleTask(int selectedID, TaskManagement taskManagement) {
        this.selectedID = selectedID;
        this.taskManagement = taskManagement;

        JLabel indicationLabel = new JLabel("Add the start and end hour of the task:", SwingConstants.CENTER);
        JLabel startHourLabel = new JLabel("Start Hour:");
        startHourTextField = new JTextField(10);
        JLabel endHourLabel = new JLabel("End Hour:");
        endHourTextField = new JTextField(10);
        addButton = new JButton("Add");

        JPanel startHourPanel = new JPanel(new GridLayout(1, 2));
        startHourPanel.add(startHourLabel);
        startHourPanel.add(startHourTextField);

        JPanel endHourPanel = new JPanel(new GridLayout(1, 2));
        endHourPanel.add(endHourLabel);
        endHourPanel.add(endHourTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        addButton.addActionListener(new AddListener());

        setLayout(new GridLayout(4, 1));
        add(indicationLabel);
        add(startHourPanel);
        add(endHourPanel);
        add(buttonPanel);

        setTitle("Simple Task");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (startHourTextField.getText().isEmpty() || endHourTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(AddSimpleTask.this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int startHour = Integer.parseInt(startHourTextField.getText());
                int endHour = Integer.parseInt(endHourTextField.getText());

                SimpleTask addedSimpleTask = new SimpleTask(selectedID, "Uncompleted", startHour, endHour);
                taskManagement.addTask(addedSimpleTask);    /// adaug taskul intr un set sa verif daca id ul exista deja

                JOptionPane.showMessageDialog(AddSimpleTask.this, "Simple Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Presentation(taskManagement).setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddSimpleTask.this, "Hours must be integers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
