package org.example.BusinessLogic;

import org.example.GUI.SimulationFrame;
import org.example.Model.Server;
import org.example.Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int currentTime = 0;
    private int peakTime = 0;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int minProcessingTime;
    public int maxProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;

    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;

    private PrintWriter logFile;

   public SimulationManager(int timeLimit, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime,
                            int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy, Scheduler scheduler,
                            SimulationFrame frame, List<Task> generatedTasks) {
       this.timeLimit = timeLimit;
       this.minArrivalTime = minArrivalTime;
       this.maxArrivalTime = maxArrivalTime;
       this.minProcessingTime = minProcessingTime;
       this.maxProcessingTime = maxProcessingTime;
       this.numberOfServers = numberOfServers;
       this.numberOfClients = numberOfClients;
       this.selectionPolicy = selectionPolicy;
       this.scheduler = scheduler;
       this.frame = frame;
       this.generatedTasks = generatedTasks;
       try {
           logFile = new PrintWriter(new FileWriter("simulation_log.txt"));
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void log(String message) {
       System.out.println(message);
       if(logFile != null) {
           logFile.print(message);
           logFile.flush();
       }

       if (frame != null) {
           frame.appendMessage(message);
       }
   }

    public void generateRandomTasks(int nrOfClients) {
        Random random = new Random();
        int randomTimeArrival;
        int randomTimeProcessing;

        for(int i = 1; i <= nrOfClients; i++) {
            randomTimeArrival = random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            randomTimeProcessing = random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            Task task = new Task(i, randomTimeArrival, randomTimeProcessing);
            generatedTasks.add(task);
            log("Task " + task.getId() + " " + task.getArrivalTime() + " " + task.getServiceTime() + "\n");
            ///System.out.println("Task " + task.getId() + " " + task.getArrivalTime() +  " " + task.getServiceTime());
        }
        log("\n\n\n");
    }

    public int getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(int peakTime) {
        this.peakTime = peakTime;
    }

    @Override
    public void run() {
        try {
            int totalWaitingTime = 0;
            /// int peakTime = 0;
            List<Task> tasksToDelete = new ArrayList<>();
            int maxNoOfTasks = 0;
            while (currentTime < timeLimit) {
                for (Task generatedTask : generatedTasks) {
                    if (generatedTask.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(generatedTask);
                        tasksToDelete.add(generatedTask);
                        if (scheduler.computeTotalNoOfTasksInLine() > maxNoOfTasks) {
                            maxNoOfTasks = scheduler.computeTotalNoOfTasksInLine();
                            setPeakTime(currentTime);
                        }
                    }
                }
                generatedTasks.removeAll(tasksToDelete);
                print();
            }
            log("Simulation ended.\n\n");
            log("Average waiting time: " + scheduler.computeAvgWaitingTime() + "\n");
            log("Average service time: " + scheduler.computeAvgServiceTime() + "\n");
            log("Peak hour: " + peakTime + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (logFile != null) {
            logFile.close();
        }
    }

    private void print() {
        if (currentTime < timeLimit) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Time: ").append(currentTime).append("\n");
            sb.append("Waiting clients: ");
            for (Task generatedTask : generatedTasks) {
                sb.append("(").append(generatedTask.getId()).append(",")
                        .append(generatedTask.getArrivalTime()).append(",")
                        .append(generatedTask.getServiceTime()).append("); ");
            }
            sb.append("\n\n");
            sb.append(scheduler.getQueuesStatus());

            log(sb.toString());

            currentTime++;
        }
    }
}














//    public void print() {
//       if(currentTime < timeLimit) {
//           try {
//               Thread.sleep(1000);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
//           System.out.println("Time: " + currentTime);
//           System.out.print("Waiting clients: ");
//           for(Task generatedTask: generatedTasks) {
//               System.out.print("(" + generatedTask.getId() + "," + generatedTask.getArrivalTime() +  "," + generatedTask.getServiceTime() + "); ");
//           }
//           System.out.println("\n");
//           scheduler.printQueues();
//           currentTime++;
//       }
//    }