package org.example.BusinessLogic;

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
   private List<Server> servers;
   private int maxNoOfServers;
   private int maxTasksPerServer;
   private Strategy strategy;

   public Scheduler(int maxNoOfServers, int maxTasksPerServer) {
       servers = new ArrayList<>();
       for(int i = 0; i < maxNoOfServers; i++) {
           Server server = new Server(i);
           servers.add(server);
           Thread serverThread = new Thread(server);
           serverThread.start();
       }
       this.maxNoOfServers = maxNoOfServers;
       this.maxTasksPerServer = maxTasksPerServer;
   }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void changeStrategy(SelectionPolicy policy) {
       if(policy == SelectionPolicy.SHORTEST_QUEUE) {
           strategy = new ShortestQueueStrategy();
       }
       if(policy == SelectionPolicy.SHORTEST_TIME) {
           strategy = new TimeStrategy();
       }
   }

   public void dispatchTask(Task t) {    /// alege in fucntie de strategy un server la care se trimite t
       strategy.chooseServer(servers, t);
   }

   public double computeAvgWaitingTime() {
       double sum = 0;
       for(Server server: servers) {
           sum += server.getTotalWaitingPeriodPerServer().get();
       }
       return sum / servers.size();
   }

   public double computeAvgServiceTime() {
       double sum = 0;
       for(Server server: servers) {
           sum += server.getTotalServiceTimePerServer().get();
       }
       return sum / servers.size();
   }

   public int computeTotalNoOfTasksInLine() {
       int noOfTasks = 0;
       for(Server server: servers) {
           noOfTasks += server.getTasks().size();
       }
       return noOfTasks;
   }

    public String getQueuesStatus() {
        StringBuilder sb = new StringBuilder();
        for (Server server : servers) {
            List<Task> tasks = new ArrayList<>(server.getTasks());
            if (tasks.isEmpty()) {
                sb.append("Queue ").append(server.getServerID()).append(": closed\n");
            } else {
                sb.append("Queue ").append(server.getServerID()).append(": ");
                for (Task task : tasks) {
                    sb.append("(").append(task.getId()).append(",")
                            .append(task.getArrivalTime()).append(",")
                            .append(task.getServiceTime()).append("); ");
                }
                sb.append("\n");
            }
        }
        sb.append("----------------------------\n\n");
        return sb.toString();
    }
}



//   public void printQueues() {
//       for(Server server: servers) {
//           if(server.getTasks().isEmpty()) {
//               System.out.println("Queue " + server.getServerID() + ": closed");
//           } else {
//               System.out.print("Queue " + server.getServerID() + ": ");
//               for(Task task: server.getTasks()) {
//                   System.out.print("(" + task.getId() + "," + task.getArrivalTime() + "," + task.getServiceTime() + "); ");
//               }
//               System.out.print("\n");
//           }
//       }
//       System.out.println("----------------------------\n");
//   }