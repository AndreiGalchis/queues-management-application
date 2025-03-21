package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployees extends JFrame {
    private final TaskManagement taskManagement;

    private final JLabel indicationLabel;
    private final JTextField addEmployeeNameTextField;
    private final JTextField addEmployeeIDTextField;
    private final JButton addEmployeeButton;
    private final JButton doneButton;

    public AddEmployees(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

        indicationLabel = new JLabel("Add the employee's information:", SwingConstants.CENTER);
        JLabel addEmployeeNameLabel = new JLabel("Employee's name:");
        addEmployeeNameTextField = new JTextField(15);
        JLabel addEmployeeIDLabel = new JLabel("Employee's ID:");
        addEmployeeIDTextField = new JTextField(15);
        addEmployeeButton = new JButton("Add Employee");
        doneButton = new JButton("Done");

        JPanel topPanel = new JPanel();
        topPanel.add(indicationLabel);

        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        namePanel.add(addEmployeeNameLabel);
        namePanel.add(addEmployeeNameTextField);

        JPanel idPanel = new JPanel(new GridLayout(1, 2));
        idPanel.add(addEmployeeIDLabel);
        idPanel.add(addEmployeeIDTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addEmployeeButton);

        JPanel donePanel = new JPanel();
        donePanel.add(doneButton);

        addEmployeeButton.addActionListener(new AddEmployeeListener());
        doneButton.addActionListener(new DoneListener());


        setLayout(new GridLayout(5, 1));
        add(topPanel);
        add(namePanel);
        add(idPanel);
        add(buttonPanel);
        add(donePanel);

        setTitle("Add Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String addedEmployeeName = addEmployeeNameTextField.getText();
            String addedEmployeeID = addEmployeeIDTextField.getText();

            if (addedEmployeeName.isEmpty() || addedEmployeeID.isEmpty()) {
                JOptionPane.showMessageDialog(AddEmployees.this, "Please fill both fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int employeeID = Integer.parseInt(addedEmployeeID);
                Employee employee = new Employee(employeeID, addedEmployeeName);

                for (Employee existingEmployee : taskManagement.getAddedEmployees()) {    /// Verif sa fie id ul la Employee unic
                    if (existingEmployee.getIdEmployee() == employeeID) {
                        JOptionPane.showMessageDialog(AddEmployees.this,
                                "Employee ID already exists! Please choose a different ID.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                taskManagement.addEmployeeToMap(employee);     /// adaug angajatul in Map
                JOptionPane.showMessageDialog(AddEmployees.this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddEmployees.this, "Invalid ID format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Presentation(taskManagement);
            setVisible(false);
        }
    }
}
