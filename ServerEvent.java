class ServerEvent extends Event {
    private final int serverID;

    public ServerEvent(Customer customer, double time, int serverID, boolean humanServer) {
        super(customer, time, humanServer);
        this.serverID = serverID;
    }

    public Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq) {
        Server currentServer = servers.get(serverID - 1);
        if (time != customer.getArrivalTime()) {
                
            currentServer = currentServer.removeCustomerFromQueue();
            Server updatedServer = currentServer.updateNextAvailableTime();
            
            if (humanServer == false) {
                for (Server server : servers) {
                    int index = servers.indexOf(server);
                    if (server.isHuman() == false & index != (serverID - 1)) {
                        updatedServer = updatedServer.removeCustomerFromQueue();
                    }
                }
            }

            double updatedServerTime = updatedServer.getNextAvailableTime();

            Event nextEvent = new CompletedEvent(customer, updatedServerTime, 
                serverID, humanServer);

            return new Pair<ImList<Server>, PQ<Event>>(servers
.set(serverID - 1, updatedServer), pq.add(nextEvent));
        }

        double currentServerTime = servers.get(serverID - 1).getNextAvailableTime();
        Event nextEvent = new CompletedEvent(customer, currentServerTime, serverID, humanServer);

        return new Pair<ImList<Server>, PQ<Event>>(servers
.set(serverID - 1, currentServer), pq.add(nextEvent));
    }

    public int updateServe(int serve) {
        return serve + 1;
    }

    public double updateWait(double waitTime) {
        double waitDuration = time - customer.getArrivalTime();
        return waitTime + waitDuration;
    }

    public String toString() {
        if (humanServer) {
            return String.format("%.3f %d serves by %d\n", time, 
                customer.getCustomerID(), serverID);
        } else {
            return String.format("%.3f %d serves by self-check %d\n", time, 
                customer.getCustomerID(), serverID);
        }
    }
}
