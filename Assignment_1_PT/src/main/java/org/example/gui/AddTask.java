package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTask extends JFrame {
    private final TaskManagement taskManagement;

    private final JTextField IDTextField;
    private final JRadioButton simpleTaskRadio;
    private final JRadioButton complexTaskRadio;
    private final JButton continueButton;
    private final JButton backButton;

    public AddTask(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;

        JLabel indicationLabel = new JLabel("Select the type of task and enter its ID:", SwingConstants.CENTER);
        simpleTaskRadio = new JRadioButton("Simple Task");
        complexTaskRadio = new JRadioButton("Complex Task");
        JLabel IDLabel = new JLabel("Task ID:");
        IDTextField = new JTextField(10);
        continueButton = new JButton("Continue");
        backButton = new JButton("Back");

        ButtonGroup taskTypeGroup = new ButtonGroup();
        taskTypeGroup.add(simpleTaskRadio);
        taskTypeGroup.add(complexTaskRadio);

        JPanel taskTypePanel = new JPanel(new GridLayout(1, 2));
        taskTypePanel.add(simpleTaskRadio);
        taskTypePanel.add(complexTaskRadio);

        JPanel idPanel = new JPanel(new GridLayout(1, 2));
        idPanel.add(IDLabel);
        idPanel.add(IDTextField);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(continueButton);
        buttonPanel.add(backButton);

        continueButton.addActionListener(new ContinueListener());
        backButton.addActionListener(new BackListener());

        setLayout(new GridLayout(5, 1));
        add(indicationLabel);
        add(taskTypePanel);
        add(idPanel);
        add(continueButton);
        add(backButton);

        setTitle("Add Task");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public class ContinueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String taskIdText = IDTextField.getText();

            if (taskIdText.isEmpty()) {
                JOptionPane.showMessageDialog(AddTask.this, "Please enter a Task ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int taskId;
            try {
                taskId = Integer.parseInt(taskIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddTask.this, "Task ID must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Task task : taskManagement.getAddedTasks()) {   /// verif sa fie id ul la Task unic
                if (task.getIdTask() == taskId) {
                    JOptionPane.showMessageDialog(AddTask.this, "Task ID already exists. Please choose a different ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (simpleTaskRadio.isSelected()) {      /// daca SimpleTask e selectat
                new AddSimpleTask(taskId, taskManagement);
                setVisible(false);
            } else if (complexTaskRadio.isSelected()) {   /// daca Complex Task e selectat
                ComplexTask complexTask = new ComplexTask(taskId, "Uncompleted");
                taskManagement.addTask(complexTask);

                JOptionPane.showMessageDialog(AddTask.this, "Complex Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Presentation(taskManagement);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(AddTask.this, "Please select a task type.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Presentation presentation = new Presentation(taskManagement);
            setVisible(false);
            presentation.setVisible(true);
        }
    }

}
