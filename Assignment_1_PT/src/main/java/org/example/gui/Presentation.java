package org.example.gui;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;

import javax.swing.*;  ///JFame, JPanel, JButton, JLabel, JTextField -> GUI components for building graphical user interfaces, imports classes from javax.swing package
import java.awt.*;   ///Button, Label, TextField, FlowLayout, BorderLayout, java.awt is a lower-level library often used alongside javax.swing,  includes all classes from the java.awt package.
import java.awt.event.ActionEvent;  ///imports the ActionEvent class from the java.awt.event package. ActionEvent represents an event triggered by a component, such as a button click or a menu selection.
import java.awt.event.ActionListener;  /// The ActionListener interface defines a single method, actionPerformed(ActionEvent e), which must be implemented by classes that want to handle ActionEvents
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Presentation extends JFrame {
    private List<Employee> addedEmployees = new ArrayList<>();
    private List<Task> addedTasks = new ArrayList<>();
    private final TaskManagement taskManagement;
    private ComplexTask complexTask;

    private final JLabel chooseOperationLabel;
    private final JRadioButton addEmployeeRadio;
    private final JRadioButton addTaskRadio;
    private final JRadioButton assignTaskToEmplRadio;
    private final JRadioButton viewEmplAndTasksRadio;
    private final JRadioButton modifyTaskStatusRadio;
    private final JRadioButton viewStatisticsRadio;
    private final JRadioButton tieTasks;
    private final JButton continueButton;
    private final JButton printTasksButton;

    public Presentation(List<Employee> addedEmployees, List<Task> addedTasks, TaskManagement taskManagement, ComplexTask complexTask) {
        this.addedEmployees = addedEmployees;
        this.addedTasks = addedTasks;
        this.taskManagement = taskManagement;
        this.complexTask = complexTask;

        chooseOperationLabel = new JLabel("Choose Operation:");
        addEmployeeRadio = new JRadioButton("Add employees");
        addTaskRadio = new JRadioButton("Add task");
        assignTaskToEmplRadio = new JRadioButton("Assign task to employee");
        viewEmplAndTasksRadio = new JRadioButton("View employees and their tasks");
        modifyTaskStatusRadio = new JRadioButton("Modify the status of task");
        viewStatisticsRadio = new JRadioButton("View the statistics");
        tieTasks = new JRadioButton("Tie tasks");
        continueButton = new JButton("Continue");
        printTasksButton = new JButton("Print Assigned Tasks");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(addEmployeeRadio);
        buttonGroup.add(addTaskRadio);
        buttonGroup.add(assignTaskToEmplRadio);
        buttonGroup.add(viewEmplAndTasksRadio);
        buttonGroup.add(modifyTaskStatusRadio);
        buttonGroup.add(viewStatisticsRadio);

        setTitle("Presentation");
        setLayout(new GridLayout(4,2));
        setLocationRelativeTo(null);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        continueButton.addActionListener(new ContinueListener());
        printTasksButton.addActionListener(new PrintTasksListener());

        add(chooseOperationLabel);
        add(addEmployeeRadio);
        add(addTaskRadio);
        add(assignTaskToEmplRadio);
        add(viewEmplAndTasksRadio);
        add(modifyTaskStatusRadio);
        add(viewStatisticsRadio);
        add(tieTasks);
        add(continueButton);
        add(printTasksButton);

        setVisible(true);
    }

    public class ContinueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                if (addEmployeeRadio.isSelected()) {
                    AddEmployees addEmployees = new AddEmployees(addedEmployees, addedTasks, taskManagement, complexTask);
                    setVisible(false);
                } else if (addTaskRadio.isSelected()) {
                    AddTask addTask = new AddTask(addedTasks, addedEmployees, taskManagement, complexTask);
                    setVisible(false);
                } else if (assignTaskToEmplRadio.isSelected()) {
                    AssignTaskToEmployee assignTaskToEmployee = new AssignTaskToEmployee(addedEmployees, addedTasks, taskManagement, complexTask);
                    setVisible(false);
                } else if (viewEmplAndTasksRadio.isSelected()) {
                    ViewEmployeesAndTheirTasks viewEmployeesAndTheirTasks = new ViewEmployeesAndTheirTasks();
                    setVisible(false);
                } else if (modifyTaskStatusRadio.isSelected()) {
                    ModifyStatusOfTask modifyStatusOfTask = new ModifyStatusOfTask();
                    setVisible(false);
                } else if (viewStatisticsRadio.isSelected()) {
                    ViewStatistics viewStatistics = new ViewStatistics();
                    setVisible(false);
                } else if (tieTasks.isSelected()) {
                    TieTasks tieTasks = new TieTasks(addedTasks, addedEmployees, taskManagement, complexTask);
                    setVisible(false);
                } else {
                    System.out.println("No option selected.");
                    return;
                }
        }
    }

    public class PrintTasksListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            taskManagement.printEmployeeTasks();
        }
    }
}