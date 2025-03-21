package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewEmployeesAndTheirTasks extends JFrame {
    private final TaskManagement taskManagement;

    private final JLabel indicationLabel;
    private final JLabel employeesNamesLabel;
    private final JComboBox<Employee> employeesNamesComboBox;
    private final JLabel assignedTasksLabel;
    private final JLabel taskHierarchyLabel;
    private final JButton OKButton;

    public ViewEmployeesAndTheirTasks(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

        indicationLabel = new JLabel("Select an employee to see their assigned tasks (including duration estimation)");
        employeesNamesLabel = new JLabel("Employees:");
        employeesNamesComboBox = new JComboBox<>();
        assignedTasksLabel = new JLabel("Assigned Tasks:");
        taskHierarchyLabel = new JLabel("");
        OKButton = new JButton("OK");

        setTitle("View Employees and Their Assigned Tasks");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(employeesNamesLabel);
        topPanel.add(employeesNamesComboBox);

        JPanel middlePanel = new JPanel(new BorderLayout());
        JPanel tasksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tasksPanel.add(assignedTasksLabel);
        tasksPanel.add(taskHierarchyLabel);

        middlePanel.add(tasksPanel, BorderLayout.WEST);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(OKButton);

        mainPanel.add(indicationLabel);
        mainPanel.add(topPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(bottomPanel);

        populateEmployeeComboBox();
        employeesNamesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTaskLabel();
            }
        });


        OKButton.addActionListener(new OKListener());

        add(mainPanel);

        setVisible(true);
    }

    public void populateEmployeeComboBox() {
        for (Employee employee : taskManagement.getMap().keySet()) {
            employeesNamesComboBox.addItem(employee);
        }
    }

    public void updateTaskLabel() {
        Employee selectedEmployee = (Employee) employeesNamesComboBox.getSelectedItem();
        if (selectedEmployee != null) {
            List<Task> tasks = taskManagement.getMap().get(selectedEmployee);   /// preiau lista de taskuri a angajatului
            if (tasks != null && !tasks.isEmpty()) {
                StringBuilder taskText = new StringBuilder();
                taskText.append("<html>");
                for (Task task : tasks) {
                    appendTaskHierarchy(task, null, taskText);
                }
                taskText.append("</html>");
                taskHierarchyLabel.setText(taskText.toString());
            } else {
                taskHierarchyLabel.setText("No tasks assigned");
            }

        }
    }

    public void appendTaskHierarchy(Task task, Integer parentTaskId, StringBuilder taskText) {
        taskText.append(task.getIdTask())
                .append(" (").append(taskManagement.getTaskType(task, parentTaskId)).append(") ")
                .append(" - ").append(task.estimateDuration()).append("h")
                .append("<br>");

        if (task instanceof ComplexTask) {   /// daca e Complex
            ComplexTask complexTask = (ComplexTask) task;
            List<Task> subTasks = complexTask.getSubTasks();
            if (subTasks != null) {    /// daca are subTaskuri
                for (Task subTask : subTasks) {
                    appendTaskHierarchy(subTask, task.getIdTask(), taskText);
                }
            }
        }
    }

    public class OKListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Presentation(taskManagement).setVisible(true);
            setVisible(false);
        }
    }
}
