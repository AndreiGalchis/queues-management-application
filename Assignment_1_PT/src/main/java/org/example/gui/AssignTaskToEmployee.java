package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.Employee;
import org.example.data_model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignTaskToEmployee extends JFrame {
    private final TaskManagement taskManagement;
    private final JComboBox<String> employeesComboBox;
    private final JComboBox<Integer> taskIDComboBox;
    private final JButton assignButton;
    private final JButton doneButton;

    public AssignTaskToEmployee(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

        JLabel indicationLabel = new JLabel("Select an employee and the task to assign:", SwingConstants.CENTER);
        JLabel employeeNameLabel = new JLabel("Employee:");
        employeesComboBox = new JComboBox<>();
        JLabel IDLabel = new JLabel("Task ID:");
        taskIDComboBox = new JComboBox<>();
        assignButton = new JButton("Assign");
        doneButton = new JButton("Done");

        populateComboBoxes();

        JPanel employeePanel = new JPanel(new GridLayout(1, 2));
        employeePanel.add(employeeNameLabel);
        employeePanel.add(employeesComboBox);

        JPanel taskPanel = new JPanel(new GridLayout(1, 2));
        taskPanel.add(IDLabel);
        taskPanel.add(taskIDComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(assignButton);
        buttonPanel.add(doneButton);

        assignButton.addActionListener(new AssignListener());
        doneButton.addActionListener(new DoneListener());

        setLayout(new GridLayout(4, 1));
        add(indicationLabel);
        add(employeePanel);
        add(taskPanel);
        add(buttonPanel);

        setTitle("Assign Task to Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void populateComboBoxes() {
        for (Employee employee : taskManagement.getAddedEmployees()) {
            employeesComboBox.addItem(employee.getName());
        }

        for (Task task : taskManagement.getAddedTasks()) {
            taskIDComboBox.addItem(task.getIdTask());
        }
    }

    public class AssignListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeesComboBox.getSelectedItem() == null || taskIDComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(AssignTaskToEmployee.this, "Please select both an employee and a task!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedEmployeeName = employeesComboBox.getSelectedItem().toString();
            int selectedTaskID = (Integer) taskIDComboBox.getSelectedItem();

            Employee foundEmployee = taskManagement.findEmployee(selectedEmployeeName);
            Task foundTask = taskManagement.findTask(selectedTaskID);

            if (foundEmployee != null && foundTask != null) {
                taskManagement.assignTaskToEmployee(foundEmployee.getIdEmployee(), foundTask);
                JOptionPane.showMessageDialog(AssignTaskToEmployee.this, "Task assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(AssignTaskToEmployee.this, "Employee or Task not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

//        private Employee findEmployee(String name) {
//            for (Employee emp : taskManagement.getAddedEmployees()) {
//                if (emp.getName().equals(name)) {
//                    return emp;
//                }
//            }
//            return null;
//        }

//        public Task findTask(int id) {
//            for (Task task : taskManagement.getAddedTasks()) {
//                if (task.getIdTask() == id) {
//                    return task;
//                }
//            }
//            return null;
//        }
    }

    public class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Presentation(taskManagement).setVisible(true);
            setVisible(false);
        }
    }
}
