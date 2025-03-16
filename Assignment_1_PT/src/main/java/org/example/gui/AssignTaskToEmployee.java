package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.gui.AddEmployees.AddEmployeeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssignTaskToEmployee extends JFrame {
    private final List<Employee> addedEmployees;
    private final List<Task> addedTasks;
    private final TaskManagement taskManagement;
    private final ComplexTask complexTask;

    private final JLabel indicationLabel;
    private final JLabel employeeNameLabel;
    private final JComboBox<String> employeesComboBox;
    private final JLabel IDLabel;
    private final JComboBox<Integer> taskIDComboBox;
    private final JButton assignButton;
    private final JButton doneButton;

    public AssignTaskToEmployee(List<Employee> addedEmployees, List<Task> addedTasks, TaskManagement taskManagement, ComplexTask complexTask) {
        this.addedEmployees = addedEmployees;
        this.addedTasks = addedTasks;
        this.taskManagement = taskManagement;
        this.complexTask = complexTask;

        indicationLabel = new JLabel("Select an employee and the task you want to assign for each one");
        employeeNameLabel = new JLabel("Employee names:");
        employeesComboBox = new JComboBox<>();
        IDLabel = new JLabel("Task ID:");
        taskIDComboBox = new JComboBox<>();
        assignButton = new JButton("Assign");
        doneButton = new JButton("Done");

        setTitle("Assign task to employee");
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        populateComboBoxes();

        assignButton.addActionListener(new AssignListener());
        doneButton.addActionListener(new DoneListener());

        add(indicationLabel);
        add(employeeNameLabel);
        add(employeesComboBox);
        add(IDLabel);
        add(taskIDComboBox);
        add(assignButton);
        add(doneButton);

        setVisible(true);
    }

    private void populateComboBoxes() {
        // 🔹 Add employee names to employeesComboBox
        for (Employee employee : addedEmployees) {
            employeesComboBox.addItem(employee.getName());
        }

        // 🔹 Add task IDs to taskIDComboBox
        for (Task task : addedTasks) {
            taskIDComboBox.addItem(task.getIdTask());
        }
    }

    public class AssignListener implements ActionListener {

        public Employee findSelectedEmployeeObj(List<Employee> addedEmployees, String selectedEmployeeName) {
            for(Employee employee : addedEmployees) {
                if(employee.getName().equals(selectedEmployeeName)) {
                    return employee;
                }
            }
            return null;
        }

        public Task findSelectedTaskObj(List<Task> addedTasks, int selectedTaskID) {
            for(Task task : addedTasks) {
                if(task.getIdTask() == selectedTaskID) {
                    return task;
                }
            }
            return null;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeesComboBox.getSelectedItem() == null || taskIDComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Please select both an employee and a task!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedEmployeeName = employeesComboBox.getSelectedItem().toString();
            int selectedTaskID = Integer.parseInt(taskIDComboBox.getSelectedItem().toString());

            Employee foundEmployee = findSelectedEmployeeObj(addedEmployees, selectedEmployeeName);
            Task foundTask = findSelectedTaskObj(addedTasks, selectedTaskID);

            if (foundEmployee != null && foundTask != null) {
                taskManagement.assignTaskToEmployee(foundEmployee.getIdEmployee(), foundTask);
                JOptionPane.showMessageDialog(null, "Task assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Employee or Task not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Presentation presentation = new Presentation(addedEmployees, addedTasks, taskManagement, complexTask);
            setVisible(false);
            presentation.setVisible(true);
//            new Presentation(addedEmployees, addedTasks, taskManagement).setVisible(true);
        }
    }
}
