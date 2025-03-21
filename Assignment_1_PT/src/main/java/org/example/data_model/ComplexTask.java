package org.example.data_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task implements Serializable {
    private final List<Task> tasks;  ///in lista asta stau copiii
    private static final long serialVersionUID = 1L;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        this.tasks = new ArrayList<>();
    }

    @Override
    public int estimateDuration() {    /// ???
        int estimatedDuration = 0;
        for(Task subTask: tasks) {
            estimatedDuration += subTask.estimateDuration();
        }
        return estimatedDuration;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getSubTasks() {
        return tasks;
    }
}
