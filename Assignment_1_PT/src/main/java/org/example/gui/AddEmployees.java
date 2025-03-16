package org.example.gui;

import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.gui.Presentation;
import org.example.business_logic.TaskManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddEmployees extends JFrame {
    private final List<Employee> addedEmployees;
    private final List<Task> addedTasks;
    private final TaskManagement taskManagement;
    private final ComplexTask complexTask;

    private final JLabel indicationLabel;
    private final JLabel addEmployeeNameLabel;
    private final JTextField addEmployeeNameTextField;
    private final JLabel addEmployeeIDLabel;
    private final JTextField addEmployeeIDTextField;
    private final JButton addEmployeeButton;
    private final JButton doneButton;

    public AddEmployees(List<Employee> addedEmployees, List<Task> addedTasks, TaskManagement taskManagement, ComplexTask complexTask) {
        this.addedEmployees = addedEmployees;
        this.addedTasks = addedTasks;
        this.taskManagement = taskManagement;
        this.complexTask = complexTask;

        indicationLabel = new JLabel("Add the employee's information:");
        addEmployeeNameLabel = new JLabel("Employee's name:");
        addEmployeeNameTextField = new JTextField();
        addEmployeeIDLabel = new JLabel("Employee's ID:");
        addEmployeeIDTextField = new JTextField();
        addEmployeeButton = new JButton("Add Employee");
        doneButton = new JButton("Done");

        setTitle("Add Employee");
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addEmployeeButton.addActionListener(new AddEmployeeListener());
        doneButton.addActionListener(new DoneListener());

        add(indicationLabel);
        add(addEmployeeNameLabel);
        add(addEmployeeNameTextField);
        add(addEmployeeIDLabel);
        add(addEmployeeIDTextField);
        add(addEmployeeButton);
        add(doneButton);

        setVisible(true);
    }

    public class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String addedEmployeeName = addEmployeeNameTextField.getText();
            String addedEmployeeID = addEmployeeIDTextField.getText();

            if(addedEmployeeName.isEmpty() || addedEmployeeID.isEmpty()) {
                JOptionPane.showMessageDialog(AddEmployees.this, "Please fill both fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int employeeID = Integer.parseInt(addedEmployeeID);
                Employee employee = new Employee(employeeID, addedEmployeeName);

                addedEmployees.add(employee); // Update the correct list
                taskManagement.addEmployeeToMap(employee);

                JOptionPane.showMessageDialog(AddEmployees.this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddEmployees.this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Presentation presentation = new Presentation(addedEmployees, addedTasks, taskManagement, complexTask);
            setVisible(false);
            presentation.setVisible(true);
        }
    }
}
