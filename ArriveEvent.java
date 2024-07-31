class ArriveEvent extends Event {
    public ArriveEvent(Customer customer, double time, boolean humanServer) {
        super(customer, time, humanServer);
    }

    

    

    public Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq) {
        for (Server server : servers) {
            if (server.isIdle(customer.getArrivalTime())) {
                Server updatedServer = server.serve(this.getCustomer());
                int index = servers.indexOf(server);
                Event event = new ServerEvent(customer, time, index + 1, server.isHuman());
                return new Pair<ImList<Server>, PQ<Event>>(servers
.set(index, updatedServer), pq.add(event));
            }
        }
        for (int i = 0; i < servers.size(); i++) { 
            if (servers.get(i).canQueue()) {
                ImList<Server> updatedServers = servers.set(i, 
                    servers.get(i).addCustomerToQueue(customer));
                
                if (updatedServers.get(i).isHuman() == false) {
                    for (int k = i + 1; k < updatedServers.size(); k++) {
                        updatedServers = updatedServers.set(k, 
                            updatedServers.get(k).addCustomerToQueue(customer));
                        break;
                    }
                } 

                Event event = new WaitEvent(customer, time, i + 1, servers.get(i).isHuman());
                return new Pair<ImList<Server>, PQ<Event>>(updatedServers, pq.add(event));
            }
        }
        Event event = new LeaveEvent(customer, time, humanServer);
        return new Pair<ImList<Server>, PQ<Event>>(servers, pq.add(event));
    }

    public String toString() {
        return String.format("%.3f %d arrives\n", time, customer.getCustomerID());
    }
}
