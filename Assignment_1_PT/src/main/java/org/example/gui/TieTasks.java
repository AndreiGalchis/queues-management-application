package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.data_model.ComplexTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TieTasks extends JFrame {
    private TaskManagement taskManagement;

    private final JLabel indicationLabel;
    private final JLabel parentTaskLabel;
    private final JTextField parentTaskTextField;
    private final JLabel childTaskLabel;
    private final JTextField childTaskTextField;
    private final JButton tieButton;
    private final JButton backButton;

    public TieTasks(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

        indicationLabel = new JLabel("Select a task and its subtask", SwingConstants.CENTER);
        parentTaskLabel = new JLabel("Select a parent task:");
        parentTaskTextField = new JTextField(10);
        childTaskLabel = new JLabel("Select a child task:");
        childTaskTextField = new JTextField(10);
        tieButton = new JButton("Tie");
        backButton = new JButton("Back");

        setTitle("Tie tasks");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel indicationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        indicationPanel.add(indicationLabel);

        JPanel parentTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        parentTaskPanel.add(parentTaskLabel);
        parentTaskPanel.add(parentTaskTextField);

        JPanel childTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        childTaskPanel.add(childTaskLabel);
        childTaskPanel.add(childTaskTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(tieButton);

        mainPanel.add(indicationPanel);
        mainPanel.add(parentTaskPanel);
        mainPanel.add(childTaskPanel);
        mainPanel.add(buttonPanel);

        tieButton.addActionListener(new TieListener());
        backButton.addActionListener(new BackListener());

        add(mainPanel);

        setVisible(true);
    }

    public class TieListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int parentTaskID = Integer.parseInt(parentTaskTextField.getText());
            int childTaskID = Integer.parseInt(childTaskTextField.getText());

            Task parentTask = taskManagement.getTaskById(taskManagement.getAddedTasks(), parentTaskID);
            Task childTask =  taskManagement.getTaskById(taskManagement.getAddedTasks(), childTaskID);

            if (parentTask instanceof ComplexTask) {
                ((ComplexTask) parentTask).addTask(childTask);
                JOptionPane.showMessageDialog(null, "Child Task successfully added to Parent Task!");
            } else {
                JOptionPane.showMessageDialog(null, "The selected Parent Task is not a ComplexTask!");
            }
        }
    }

    public class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new Presentation(taskManagement).setVisible(true);
        }
    }


}
