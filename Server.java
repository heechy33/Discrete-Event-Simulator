import java.util.function.Supplier;

class Server {
    protected final Customer customer;
    protected final int maxQueueLength;
    protected final boolean isServing;
    protected final double nextAvailableTime;
    protected final ImList<Customer> queue;
    protected final Supplier<Double> restTimes;

    Server(int maxQueueLength, Supplier<Double> restTimes, Supplier<Double> serviceTimes) {
        this.customer = new Customer();
        this.maxQueueLength = maxQueueLength;
        this.isServing = true;
        this.nextAvailableTime = 0.0;
        this.queue = new ImList<Customer>();
        this.restTimes = restTimes;

    }

    Server(Customer customer, int maxQueueLength, boolean isServing, 
                double nextAvailableTime, ImList<Customer> queue, Supplier<Double> restTimes) {
        this.customer = customer;
        this.maxQueueLength = maxQueueLength;
        this.isServing = false;
        this.nextAvailableTime = nextAvailableTime;
        this.queue = queue;
        this.restTimes = restTimes;
    }

    public boolean isHuman() {
        return true;
    }

    public ImList<Customer> getQueue() {
        return this.queue;
    }

    public int getQueueLength() {
        return this.maxQueueLength;
    }

    public boolean isIdle(double startTime) {
        return this.isServing || this.nextAvailableTime <= startTime;
    }

    public boolean canQueue() {
        return this.queue.size() < this.maxQueueLength;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    public Server addCustomerToQueue(Customer customer) {
        return new Server(customer, this.maxQueueLength, 
                    false, this.nextAvailableTime, this.queue.add(customer), this.restTimes);

    }
    
    public int getAvailableNowServer(ImList<Server> servers, int serverID) {
        int availableNowServer = serverID;
        
        for (int i = 0; i < servers.size(); i++) {
            if (!servers.get(i).isHuman() && servers.get(i).getNextAvailableTime() <
                servers.get(availableNowServer - 1).getNextAvailableTime()) {
                availableNowServer = i + 1;
            }
        }
    
        return availableNowServer;
    }


    public boolean haveQueue() {
        return this.queue.size() != this.maxQueueLength - 1;
    }

    public Customer serveQueue() {
        return this.queue.get(0);
    }

    public Server serve(Customer customer) {
        return new Server(customer, this.maxQueueLength, 
                false, customer.getDoneTime(), this.queue, this.restTimes);

    }

    public Server updateRestTimes() {
        return new Server(this.customer, this.maxQueueLength, false, 
            this.nextAvailableTime + this.restTimes.get(), this.queue, this.restTimes);
    }

    public Server updateNextAvailableTime() {
        return new Server(this.customer, this.maxQueueLength, false, 
            this.customer.getServiceTime() + this.nextAvailableTime, this.queue, this.restTimes);
    }
    
    public Server removeCustomerFromQueue() {
        return new Server(customer, this.maxQueueLength, 
            false, this.nextAvailableTime, this.queue.remove(0), this.restTimes);
        
    }

    public boolean hasWaitingCustomers() {
        return this.queue.size() > 0;
    }
}
