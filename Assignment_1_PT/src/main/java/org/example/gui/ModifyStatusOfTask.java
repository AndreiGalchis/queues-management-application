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

public class ModifyStatusOfTask extends JFrame {
    private final JLabel indicationLabel;
    private final JLabel employeesNamesLabel;
    private final JComboBox<String> employeesNamesComboBox;
    private final JLabel taskIDsLabel;
    private final JComboBox<Integer> taskIDsComboBox;
    private final JLabel taskStatusLabel;
    private final JComboBox<String> taskStatusComboBox;
    private final JButton doneButton;

    public ModifyStatusOfTask() {
        indicationLabel = new JLabel("Select an employee and a task and modify its status");
        employeesNamesLabel = new JLabel("Employees names:");
        employeesNamesComboBox = new JComboBox<>();
        taskIDsLabel = new JLabel("Task IDs:");
        taskIDsComboBox = new JComboBox<>();
        taskStatusLabel = new JLabel("Task status:");
        taskStatusComboBox = new JComboBox<>();
        doneButton = new JButton("Done");

        setTitle("Modify the status of an employee's task/s");
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        doneButton.addActionListener(new DoneListener());

        add(indicationLabel);
        add(employeesNamesLabel);
        add(employeesNamesComboBox);
        add(taskIDsLabel);
        add(taskIDsComboBox);
        add(taskStatusLabel);
        add(taskStatusComboBox);
        add(doneButton);

        setVisible(true);
    }

    public class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           /// Presentation presentation = new Presentation();
            setVisible(false);
          ///  presentation.setVisible(true);
        }
    }

}
