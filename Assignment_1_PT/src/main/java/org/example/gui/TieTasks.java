package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.data_model.ComplexTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TieTasks extends JFrame {
    private List<Employee> addedEmployees;
    private List<Task> addedTasks;
    private TaskManagement taskManagement;
    private ComplexTask complexTask;

    private final JLabel indicationLabel;
    private final JLabel parentTaskLabel;
    private final JTextField parentTaskTextField;
    private final JLabel childTaskLabel;
    private final JTextField childTaskTextField;
    private final JButton tieButton;

    public TieTasks(List<Task> addedTasks, List<Employee> addedEmployees, TaskManagement taskManagement, ComplexTask complexTask) {
        this.addedTasks = addedTasks;
        this.addedEmployees = addedEmployees;
        this.taskManagement = taskManagement;
        this.complexTask = complexTask;

        indicationLabel = new JLabel("Select a task and his subtask:");
        parentTaskLabel = new JLabel("Select a parent task:");
        parentTaskTextField = new JTextField();
        childTaskLabel = new JLabel("Select a child task:");
        childTaskTextField = new JTextField();
        tieButton = new JButton("Tie");

        setTitle("Tie tasks");
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tieButton.addActionListener(new TieListener());

        add(indicationLabel);
        add(parentTaskLabel);
        add(parentTaskTextField);
        add(childTaskLabel);
        add(childTaskTextField);
        add(tieButton);

        setVisible(true);
    }

    public class TieListener implements ActionListener {

        boolean checkIfParentExists(List<Task> addedTasks, int parentTaskId) {
            for(Task task : addedTasks) {
                if(task.getIdTask() == parentTaskId) {
                    return true;
                }
            }
            return false;
        }

        boolean checkIfChildExists(List<Task> addedTasks, int childTaskId) {
            for(Task task : addedTasks) {
                if(task.getIdTask() == childTaskId) {
                    return true;
                }
            }
            return false;
        }

        Task getParentTask(List<Task> addedTasks, int parentTaskId) {
            for(Task task : addedTasks) {
                if(task.getIdTask() == parentTaskId) {
                    return task;
                }
            }
            return null;
        }

        Task getChildTask(List<Task> addedTasks, int childTaskId) {
            for(Task task : addedTasks) {
                if(task.getIdTask() == childTaskId) {
                    return task;
                }
            }
            return null;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int parentTaskID = Integer.parseInt(parentTaskTextField.getText());
            int childTaskID = Integer.parseInt(childTaskTextField.getText());

            Task parentTask = getParentTask(addedTasks, parentTaskID);
            Task childTask = getChildTask(addedTasks, childTaskID);

            if (parentTask instanceof ComplexTask) {
                ((ComplexTask) parentTask).addTask(childTask);
                JOptionPane.showMessageDialog(null, "Child Task successfully added to Parent Task!");
            } else {
                JOptionPane.showMessageDialog(null, "The selected Parent Task is not a ComplexTask!");
            }

            setVisible(false);
            Presentation presentation = new Presentation(addedEmployees, addedTasks, taskManagement, complexTask);
        }
    }
}
