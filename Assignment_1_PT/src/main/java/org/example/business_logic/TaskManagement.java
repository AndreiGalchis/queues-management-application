package org.example.business_logic;

import org.example.data_model.Employee;
import org.example.data_model.SimpleTask;
import org.example.data_model.Task;
import org.example.data_model.ComplexTask;

import java.util.*;

import java.io.*;

public class TaskManagement implements Serializable {
    private Map<Employee, List<Task>> map = new HashMap<>();
    private List<Task> addedTasks = new ArrayList<>();
    private Set<Integer> taskIds = new HashSet<>();

    public TaskManagement() {
//        this.map = new HashMap<>();
    }

    public List<Task> getAddedTasks() {
        return addedTasks;
    }

    public Map<Employee, List<Task>> getMap() {
        return map;
    }

    public void addEmployeeToMap(Employee employee) {
        for (Employee addedEmployee : map.keySet()) {
            if (addedEmployee.getIdEmployee() == employee.getIdEmployee()) {
                throw new IllegalArgumentException("An employee with ID = " + employee.getIdEmployee() + " already exists!");
            }
        }
        map.putIfAbsent(employee, new ArrayList<>());  /// mai intai bag employee in Map   *this.map. ....
    }

    public List<Employee> getAddedEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : map.keySet()) {
            employeeList.add(employee);
        }
        return employeeList;
    }

    public void addTask(Task task) {
        if (taskIds.contains(task.getIdTask())) {
            throw new IllegalArgumentException("A task with ID = " + task.getIdTask() + " already exists!");
        }
        addedTasks.add(task);
        taskIds.add(task.getIdTask());
    }

    public void assignTaskToEmployee(int idEmployee, Task task) {
        for (Employee employee : map.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                map.get(employee).add(task);
                return;
            }
        }
        System.out.println("No employee with id = " + idEmployee);
    }

    public int calculateEmployeeWorkDuration(int idEmployee) {
        int workDuration = 0;
        for (Employee employee : map.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                for (Task task : map.get(employee)) {
                    if (isTaskCompleted(task)) {
                        workDuration += task.estimateDuration();
                    }
                }
            }
        }
        return workDuration;
    }

    private boolean isTaskCompleted(Task task) {
        if (task instanceof SimpleTask) {
            if (task.getStatusTask().equals("Completed")) {
                return true;
            }
            return false;
        }

        if (task instanceof ComplexTask) {
            for (Task subTask : ((ComplexTask) task).getSubTasks()) {
                if (!isTaskCompleted(subTask)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        for (Employee employee : map.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                for (Task task : map.get(employee)) {
                    if (modify(task, idTask, null)) {     /// parintele e null pt ca i primu layer din lista
                        return;
                    }
                }
            }
        }
    }


    private boolean modify(Task task, int idTask, ComplexTask parent) {
        if (task.getIdTask() == idTask) {
            if (task.getStatusTask().equals("Completed")) {   /// daca e Completed -> Uncompleted
                task.setStatusTask("Uncompleted");
                if (parent != null) {     /// modif si parintele daca are
                    parent.setStatusTask("Uncompleted");
                    modifyParentStatus(parent);
                }
            } else {     /// daca e Uncompleted -> Completed
                task.setStatusTask("Completed");
                if (task instanceof ComplexTask) {    /// daca e Complex si Uncompleted
                    ComplexTask complexTask = (ComplexTask) task;
                    /// task.setStatusTask("Completed");     /// il fac Completed
                    modifyAllSubtasksToCompleted(complexTask);  /// toti descdendentii sa devina Completed
                }
                if (parent != null && isTaskCompleted(parent)) {   /// daca are parinte si copiii parintelui is deja Completed => parintele devine Completed
                    parent.setStatusTask("Completed");
                    modifyParentStatus(parent);
                }
            }
            return true;
        }
        if (task instanceof ComplexTask) {     /// cauta un task pe baza id ului daca nu il gaseste in primul layer de taskuri al angajatului si dupa tot asa
            ComplexTask complexTask = (ComplexTask) task;
            for (Task subTask : complexTask.getSubTasks()) {
                if (modify(subTask, idTask, complexTask)) { /// complexTask ii parintele, subTask ii taskul cautat de la inceput
                    if (isTaskCompleted(complexTask)) {
                        complexTask.setStatusTask("Completed");
                        modifyParentStatus(complexTask);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void modifyAllSubtasksToCompleted(ComplexTask complexTask) {
        for (Task subTask : complexTask.getSubTasks()) {
            subTask.setStatusTask("Completed");
            if (subTask instanceof ComplexTask) {
                modifyAllSubtasksToCompleted((ComplexTask) subTask);
            }
        }
    }

    private void modifyParentStatus(Task updatedParentTask) {   /// modifica parintii rec
        for (Employee employee : map.keySet()) {
            for (Task task : map.get(employee)) {
                if (task instanceof ComplexTask) {
                    ComplexTask complexTask = (ComplexTask) task;
                    if (complexTask.getSubTasks().contains(updatedParentTask)) {   /// complexTask ii practic parintele parintelui de mai sus
                        complexTask.setStatusTask("Uncompleted");     /// devine Uncompleted pt ca ii tatal lui updatedParentTask care ii Uncompleted
                        modifyParentStatus(complexTask);  /// le fac Uncompleted pana la root
                    }
                }
            }
        }
    }

    public Employee findEmployee(String name) {
        for (Employee emp : getAddedEmployees()) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
    }

    public Task findTask(int id) {
        for (Task task : getAddedTasks()) {
            if (task.getIdTask() == id) {
                return task;
            }
        }
        return null;
    }


    public String getTaskType(Task task, Integer parentTaskId) {
        if (task instanceof ComplexTask) {
            if (parentTaskId == null) {
                return "Complex";
            } else {
                return "Complex of " + parentTaskId;
            }
        } else {
            if (parentTaskId == null) {
                return "Simple";
            } else {
                return "Simple of " + parentTaskId;
            }
        }
    }

    public int findEmployeeIdByTask(Task targetTask) {
        for (Map.Entry<Employee, List<Task>> entry : getMap().entrySet()) {
            for (Task mainTask : entry.getValue()) {
                if (mainTask == targetTask || isSubTaskOf(mainTask, targetTask)) {
                    return entry.getKey().getIdEmployee();
                }
            }
        }
        return -1;
    }

    public boolean isSubTaskOf(Task parentTask, Task targetTask) {
        if (parentTask instanceof ComplexTask) {
            for (Task subTask : ((ComplexTask) parentTask).getSubTasks()) {
                if (subTask == targetTask || isSubTaskOf(subTask, targetTask)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Task getTaskById(List<Task> addedTasks, int taskId) {
        for (Task task : addedTasks) {
            if (task.getIdTask() == taskId) {
                return task;
            }
        }
        return null;
    }

}