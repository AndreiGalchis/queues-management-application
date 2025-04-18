package org.example.Model;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    int serverID;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private AtomicInteger totalWaitingPeriodPerServer = new AtomicInteger(0);
    private AtomicInteger totalServiceTimePerServer = new AtomicInteger(0);

    public Server(int serverID) {
        this.serverID = serverID;
        tasks = new LinkedBlockingDeque<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public AtomicInteger getTotalWaitingPeriodPerServer() {
        return totalWaitingPeriodPerServer;
    }

    public void setTotalWaitingPeriodPerServer(AtomicInteger totalWaitingPeriodPerServer) {
        this.totalWaitingPeriodPerServer = totalWaitingPeriodPerServer;
    }

    public AtomicInteger getTotalServiceTimePerServer() {
        return totalServiceTimePerServer;
    }

    public void setTotalServiceTimePerServer(AtomicInteger totalServiceTimePerServer) {
        this.totalServiceTimePerServer = totalServiceTimePerServer;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
        if(tasks.size() > 1) {
            int index = 1;
            for (Task task : tasks) {
                if (index == tasks.size()) {
                    break;
                }
                totalWaitingPeriodPerServer.addAndGet(task.getServiceTime());
                index++;
            }
        }
        totalServiceTimePerServer.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task currentTask = tasks.peek();
                if (currentTask != null) {
                    Thread.sleep(1000);
                    synchronized (this) {
                        currentTask.setServiceTime(currentTask.getServiceTime() - 1);
                        waitingPeriod.decrementAndGet();
                        if (currentTask.getServiceTime() == 0) {
                            tasks.poll();
                        }
                    }
                } else {  /// nu i nmn la coada
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }
}
