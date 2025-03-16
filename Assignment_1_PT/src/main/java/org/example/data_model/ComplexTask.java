package org.example.data_model;

import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task {
    private final List<Task> tasks;     ///in lista asta stau copiii

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        this.tasks = new ArrayList<>();
    }

    @Override
    public int estimateDuration() {    /// ???
        return 0;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
