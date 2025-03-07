package DataModel;

import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task {
    private final List<Task> tasks;

    public ComplexTask(int idTask, String statusTask, List<Task> tasks) {
        super(idTask, statusTask);
        this.tasks = tasks;
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
