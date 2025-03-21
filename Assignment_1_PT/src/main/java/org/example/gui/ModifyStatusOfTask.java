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
import java.util.Map;

public class ModifyStatusOfTask extends JFrame {
    private TaskManagement taskManagement;

    private final JLabel indicationLabel;
    private final JLabel taskIDsLabel;
    private final JComboBox<Integer> taskIDsComboBox;
    private final JLabel currentStatusLabel;
    private final JButton modifyButton;
    private final JButton doneButton;

    public ModifyStatusOfTask(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

        indicationLabel = new JLabel("Select an employee and a task and modify its status");
        taskIDsLabel = new JLabel("Task IDs:");
        taskIDsComboBox = new JComboBox<>();
        currentStatusLabel = new JLabel("Current status:");
        modifyButton = new JButton("Modify");
        doneButton = new JButton("Done");

        setTitle("Modify the status of an employee's task/s");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel indicationPanel = new JPanel();
        indicationPanel.add(indicationLabel);

        JPanel taskIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskIdPanel.add(taskIDsLabel);
        taskIdPanel.add(taskIDsComboBox);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(currentStatusLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(modifyButton);
        buttonPanel.add(doneButton);

        mainPanel.add(indicationPanel);
        mainPanel.add(taskIdPanel);
        mainPanel.add(statusPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        populateTaskComboBox();
        taskIDsComboBox.addActionListener(e -> updateTaskStatusLabel());
        doneButton.addActionListener(new DoneListener());
        modifyButton.addActionListener(new ModifyListener());

        add(mainPanel);
        setVisible(true);
    }

    public void populateTaskComboBox() {
        List<Task> tasks = taskManagement.getAddedTasks();
        for (Task task : tasks) {
            taskIDsComboBox.addItem(task.getIdTask());
        }
    }

    public void updateTaskStatusLabel() {
        Integer selectedTaskID = (Integer) taskIDsComboBox.getSelectedItem();
        if (selectedTaskID != null) {
            for (Task task : taskManagement.getAddedTasks()) {
                if (task.getIdTask() == (selectedTaskID)) {
                    currentStatusLabel.setText("Current status: " + task.getStatusTask());
                    return;
                }
            }
        }
    }

    public class ModifyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Integer selectedTaskID = (Integer) taskIDsComboBox.getSelectedItem();
            if (selectedTaskID != null) {
                for (Task task : taskManagement.getAddedTasks()) {
                    if (task.getIdTask() == (selectedTaskID)) {
                        int employeeId = taskManagement.findEmployeeIdByTask(task);
                        if (employeeId != -1) {
                            taskManagement.modifyTaskStatus(employeeId, selectedTaskID);
                            updateTaskStatusLabel();
                        } else {
                            JOptionPane.showMessageDialog(null, "Task is not assigned to an employee!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        return;
                    }
                }
            }
        }
    }

    public class DoneListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new Presentation(taskManagement).setVisible(true);
        }
    }
}
