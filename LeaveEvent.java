class LeaveEvent extends Event {
    public LeaveEvent(Customer customer, double time, boolean humanServer) {
        super(customer, time, humanServer);
    }

    

    Pair<ImList<Server>, PQ<Event>> getNextEvent(ImList<Server> servers, PQ<Event> pq) {
        return new Pair<ImList<Server>, PQ<Event>>(servers, pq);
    }


    public int updateLeave(int leave) {
        return leave + 1;
    }

    public String toString() {
        return String.format("%.3f %d leaves\n", time,
            customer.getCustomerID());
    }
}
