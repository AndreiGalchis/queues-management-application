package BusinessLogic;

import DataModel.Employee;
import DataModel.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    private Map<Employee, List<Task>> map;

   public TaskManagement() {
       this.map = new HashMap<>();
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

    public void modifyTaskStatus(int idEmployee, int idTask) {

    }

    public Map<Employee, List<Task>> getMap() {
        return map;
    }

    public void setMap(Map<Employee, List<Task>> map) {
        this.map = map;
    }
}
