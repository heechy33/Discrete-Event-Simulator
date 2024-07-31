class WaitEvent extends Event {
    private final int serverID;
    private final boolean firstTime;

    public WaitEvent(Customer customer, double time, int serverID, boolean humanServer) {
        super(customer, time, humanServer);
        this.serverID = serverID;
        this.firstTime = true;
    }

    public WaitEvent(Customer customer, double time, int serverID,
        boolean humanServer, boolean firstTime) {
        super(customer, time, humanServer);
        this.serverID = serverID;
        this.firstTime = false;

    }


    public Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq) {
        int availableNowServer = serverID;
        
        if (servers.get(availableNowServer - 1).getNextAvailableTime() <= this.time & 
            servers.get(serverID - 1).serveQueue().getCustomerID() 
            == customer.getCustomerID()) {
            Event event = new ServerEvent(servers.get(availableNowServer - 1).serveQueue(), 
                servers.get(availableNowServer - 1).getNextAvailableTime(),
                availableNowServer, humanServer);
            return new Pair<ImList<Server>, PQ<Event>>(servers, pq.add(event));
        }

        if (humanServer == false) {
            availableNowServer = servers.get(availableNowServer - 1)
            .getAvailableNowServer(servers, serverID);
        }
    
    
        double updatedServer = servers.get(serverID - 1).getNextAvailableTime();
        Event event = new WaitEvent(customer, updatedServer, 
            availableNowServer, humanServer, false);
        return new Pair<ImList<Server>, PQ<Event>>(servers, pq.add(event));
        
    }


    public String toString() {
        if (this.firstTime == false) {
            return "";
        } else if (humanServer == true) {
            return String.format("%.3f %d waits at %d\n", time, 
                customer.getCustomerID(), serverID);
        } else {
            return String.format("%.3f %d waits at self-check %d\n", time, 
                customer.getCustomerID(), serverID);
        }
        
    }
}