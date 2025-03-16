package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewEmployeesAndTheirTasks extends JFrame {
    private final JLabel indicationLabel;
    private final JLabel employeesNamesLabel;
    private final JComboBox<String> employeesNamesComboBox;
    private final JLabel taskIDsLabel;
    private final JComboBox<Integer> taskIDsComboBox;

    private final JButton OKButton;

    public ViewEmployeesAndTheirTasks() {
        indicationLabel = new JLabel("Select an employee to see their assigned tasks (including the estimation of duration)");
        employeesNamesLabel = new JLabel("Employees names:");
        employeesNamesComboBox = new JComboBox<>();
        taskIDsLabel = new JLabel("Task IDs:");
        taskIDsComboBox = new JComboBox<>();
        OKButton = new JButton("OK");

        setTitle("View employees and their assigned tasks");
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        OKButton.addActionListener(new OKListener());

        add(indicationLabel);
        add(employeesNamesLabel);
        add(employeesNamesComboBox);
        add(taskIDsLabel);
        add(taskIDsComboBox);
        add(OKButton);

        setVisible(true);
    }

    public class OKListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           /// Presentation presentation = new Presentation();
            setVisible(false);
          ///  presentation.setVisible(true);
        }
    }
}
