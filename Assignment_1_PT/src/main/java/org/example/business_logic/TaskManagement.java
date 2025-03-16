package org.example.business_logic;

import org.example.data_model.Employee;
import org.example.data_model.Task;
import org.example.data_model.ComplexTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    private Map<Employee, List<Task>> map;

    public TaskManagement() {
        this.map = new HashMap<>();
    }

    public void addEmployeeToMap(Employee employee) {
        map.putIfAbsent(employee, new ArrayList<>());  /// mai intai bag employee in Map
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        for(Employee employee : map.keySet()) {   /// de modificat in foreach
            if(employee.getIdEmployee() == idEmployee) {
                map.get(employee).add(task);
                return;
            }
        }
        System.out.println("No employee with id = " + idEmployee);
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {     /// ???
        int workDuration = 0;
        for(Employee employee: map.keySet()) {
            if(employee.getIdEmployee() == idEmployee) {
                for(Task task: map.get(employee)) {
                    if(task.getStatusTask().equals("Completed")) {
                        workDuration += task.estimateDuration();
                    }
                }
            }
        }
        return workDuration;
    }

    boolean foundEmployee(int idEmployee) {
        for(Employee employee : map.keySet()) {   /// de modificat in foreach
            if (employee.getIdEmployee() == idEmployee) {
                return true;
            }
        }
        return false;
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {

    }

    public Map<Employee, List<Task>> getMap() {
        return map;
    }

    public void setMap(Map<Employee, List<Task>> map) {
        this.map = map;
    }

    public void printEmployeeTasks() {
        if (map.isEmpty()) {
            System.out.println("No tasks assigned yet.");
            return;
        }

        for (Map.Entry<Employee, List<Task>> entry : map.entrySet()) {
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();
            System.out.println("Employee: " + employee.getName() + " (ID: " + employee.getIdEmployee() + ")");

            if (tasks.isEmpty()) {
                System.out.println("  No tasks assigned.");
            } else {
                for (Task task : tasks) {
                    printTaskWithChildren(task, "  - ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Recursively prints a task and its child tasks if it's a ComplexTask.
     */
    private void printTaskWithChildren(Task task, String indent) {
        System.out.println(indent + "Task ID: " + task.getIdTask() + ", Status: " + task.getStatusTask());

        // If the task is a ComplexTask, print all its subtasks recursively
        if (task instanceof ComplexTask) {
            ComplexTask complexTask = (ComplexTask) task;
            for (Task subTask : complexTask.getTasks()) {
                printTaskWithChildren(subTask, indent + "    ");  // Increase indentation for hierarchy
            }
        }
    }
}
