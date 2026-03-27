package org.example.BusinessLogic;

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void chooseServer(List<Server> servers, Task t) {
        int minWaitingTime = Integer.MAX_VALUE;
        Server selectedServer = null;

        for (Server server : servers) {
            if (server.getWaitingPeriod().get() < minWaitingTime) {
                minWaitingTime = server.getWaitingPeriod().get();
                selectedServer = server;
            }
        }

        if (selectedServer != null) {
            selectedServer.addTask(t);
        }
    }
}
