package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class AddTask extends JFrame {
    private final List<Task> addedTasks;
    private final List<Employee> addedEmployees;
    private TaskManagement taskManagement;
    private ComplexTask complexTask;

    private final JLabel indicationLabel;
    private final JRadioButton simpleTaskRadio;
    private final JRadioButton complexTaskRadio;
    private final JLabel IDLabel;
    private final JTextField IDTextField;
    private final JButton continueButton;
    private final JButton backButton;

    public AddTask(List<Task> addedTasks, List<Employee> addedEmployees, TaskManagement taskManagement, ComplexTask complexTask) {
        this.addedTasks = addedTasks;
        this.addedEmployees = addedEmployees;
        this.taskManagement = taskManagement;
        this.complexTask = complexTask;

        indicationLabel = new JLabel("Select the type of task you want to add and its ID:");
        simpleTaskRadio = new JRadioButton("Simple task");
        complexTaskRadio = new JRadioButton("Complex task");
        IDLabel = new JLabel("ID:");
        IDTextField = new JTextField();
        continueButton = new JButton("Continue");
        backButton = new JButton("Back");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(simpleTaskRadio);
        buttonGroup.add(complexTaskRadio);

        setTitle("Choose task");
        setLayout(new GridLayout(3,2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        continueButton.addActionListener(new ContinueListener());
        backButton.addActionListener(new BackListener());

        add(indicationLabel);
        add(simpleTaskRadio);
        add(complexTaskRadio);
        add(IDLabel);
        add(IDTextField);
        add(continueButton);
        add(backButton);

        setVisible(true);
    }

    public class ContinueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!IDTextField.getText().isEmpty()) {
                int selectedID = Integer.parseInt(IDTextField.getText());

                if (simpleTaskRadio.isSelected() && !complexTaskRadio.isSelected() && !IDTextField.getText().isEmpty()) {  ///create simple task
                    AddSimpleTask simpleTask = new AddSimpleTask(selectedID, addedTasks, addedEmployees, taskManagement, complexTask);
                    setVisible(false);
                    simpleTask.setVisible(true);
                } else if (complexTaskRadio.isSelected()) {    ///create complex task
                    ComplexTask complexTask = new ComplexTask(selectedID, "Uncompleted");
                    addedTasks.add(complexTask);

                    JOptionPane.showMessageDialog(AddTask.this, "Complex Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    setVisible(false);
                    new Presentation(addedEmployees, addedTasks, taskManagement, complexTask).setVisible(true);
//                    Presentation presentation = new Presentation(addedEmployees, addedTasks, taskManagement);
//                    presentation.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(AddTask.this, "Please enter a Task ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Presentation presentation = new Presentation(addedEmployees, addedTasks, taskManagement, complexTask);
            setVisible(false);
            presentation.setVisible(true);
        }
    }
}
