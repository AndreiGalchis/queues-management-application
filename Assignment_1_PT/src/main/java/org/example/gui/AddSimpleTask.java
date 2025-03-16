package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.SimpleTask;
import org.example.data_model.Task;
import org.example.gui.AddTask.ContinueListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class AddSimpleTask extends JFrame {
    int selectedID;
    private final List<Task> addedTasks;
    private final List<Employee> addedEmployees;
    private TaskManagement taskManagement;
    private ComplexTask complexTask;

    private final JLabel indicationLabel;
    private final JLabel startHourLabel;
    private final JTextField startHourTextField;
    private final JLabel endHourLabel;
    private final JTextField endHourTextField;
    private final JButton addButton;

    public AddSimpleTask(int selectedID, List<Task> addedTasks, List<Employee> addedEmployees, TaskManagement taskManagement, ComplexTask complexTask) {
        this.selectedID = selectedID;
        this.addedTasks = addedTasks;
        this.addedEmployees = addedEmployees;
        this.taskManagement = taskManagement;
        this.complexTask = complexTask;

        indicationLabel = new JLabel("Add the start hour and the end hour of the task");
        startHourLabel = new JLabel("Start hour: ");
        startHourTextField = new JTextField();
        endHourLabel = new JLabel("End hour: ");
        endHourTextField = new JTextField();
        addButton = new JButton("Add");

        setTitle("Simple task");
        setLayout(new GridLayout(3,2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addButton.addActionListener(new AddListener());

        add(indicationLabel);
        add(startHourLabel);
        add(startHourTextField);
        add(endHourLabel);
        add(endHourTextField);
        add(addButton);

        setVisible(true);
    }

    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(startHourTextField.getText().equals("") || endHourTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            }
            SimpleTask addedSimpleTask = new SimpleTask(selectedID,
                               "Uncompleted",
                                         Integer.parseInt(startHourTextField.getText()),
                                         Integer.parseInt(endHourTextField.getText()));
            addedTasks.add(addedSimpleTask);

            JOptionPane.showMessageDialog(AddSimpleTask.this, "Simple Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            new Presentation(addedEmployees, addedTasks, taskManagement, complexTask).setVisible(true);
            ///Presentation presentation = new Presentation();
            ///presentation.setVisible(true);
        }
    }
}
