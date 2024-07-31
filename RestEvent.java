class RestEvent extends Event {
    private final int serverID;

    public RestEvent(Customer customer, double time, int serverID, boolean humanServer) {
        super(customer, time, humanServer);
        this.serverID = serverID;
    }

    Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq) {

        return new Pair<ImList<Server>, PQ<Event>>(servers, pq);

    }

    public String toString() {
        return "";
    }
}
