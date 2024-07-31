class CompletedEvent extends Event {
    private final int serverID;

    public CompletedEvent(Customer customer, double time, int serverID, boolean humanServer) {
        super(customer, time, humanServer);
        this.serverID = serverID;
    }

    Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq) {
        Server currentServer = servers.get(serverID - 1);
        
        Server updatedServer = currentServer.updateRestTimes();
        Event nextEvent = new RestEvent(customer,
            updatedServer.getNextAvailableTime(), serverID, humanServer);
    
        return new Pair<ImList<Server>, PQ<Event>>(servers.set(serverID - 1, 
        updatedServer), pq.add(nextEvent));
    
    
    }
    
    public String toString() {
        if (humanServer) {
            return String.format("%.3f %d done serving by %d\n", time,
                customer.getCustomerID(), serverID);
        }
        
        return String.format("%.3f %d done serving by self-check %d\n", time,
            customer.getCustomerID(), serverID);
        
    }
}
