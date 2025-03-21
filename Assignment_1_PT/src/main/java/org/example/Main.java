package org.example;

import org.example.business_logic.TaskManagement;
import org.example.data_access.Saver;
import org.example.data_model.ComplexTask;
import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.gui.Presentation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String DATA_FILE = "data.dat";

    public static void main(String[] args) {
          TaskManagement taskManagement = loadData();
          Presentation presentation = new Presentation(taskManagement);
          addShutdownHook(taskManagement);
    }

    private static TaskManagement loadData() {
        TaskManagement taskManagement = (TaskManagement) Saver.deserializeObj(DATA_FILE);

        if (taskManagement == null) {
            System.out.println("No existing data found. Creating new TasksManagement object.");
            taskManagement = new TaskManagement();
        }

        return taskManagement;
    }

    private static void addShutdownHook(TaskManagement taskManagement) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Serializing data to " + DATA_FILE);
            Saver.serializeObj(taskManagement, DATA_FILE);
        }));
    }
}