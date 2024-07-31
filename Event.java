abstract class Event {
    protected final Customer customer;
    protected final double time;
    protected final boolean humanServer;

    public Event(Customer customer, double time, boolean humanServer) {
        this.customer = customer;
        this.time = time;
        this.humanServer = humanServer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getTime() {
        return this.time;
    }

    abstract Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq);

    

    public double updateWait(double time) {
        return time;
    }

    public int updateServe(int serve) {
        return serve;
    }

    public int updateLeave(int leave) {
        return leave;
    }
}
