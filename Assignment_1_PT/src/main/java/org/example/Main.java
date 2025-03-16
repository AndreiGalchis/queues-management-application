package org.example;

import org.example.business_logic.TaskManagement;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.gui.Presentation;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> addedEmployees = new ArrayList<>();
        List<Task> addedTasks = new ArrayList<>();
        TaskManagement taskManagement = new TaskManagement();
        int id = 0;
        String status = "";
        ComplexTask complexTask = new ComplexTask(id, status);
        Presentation presentation = new Presentation(addedEmployees, addedTasks, taskManagement, complexTask);

        ///taskManagement.printEmployeeTasks();
    }
}