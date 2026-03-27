package org.example.BusinessLogic;

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void chooseServer(List<Server> servers, Task t) {
        int minTasks = Integer.MAX_VALUE;
        Server selectedServer = null;

        for (Server server : servers) {
            if (server.getTasks().size() < minTasks) {
                minTasks = server.getTasks().size();
                selectedServer = server;
            }
        }

        if (selectedServer != null) {
            selectedServer.addTask(t);
        }
    }
}
