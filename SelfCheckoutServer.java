import java.util.function.Supplier;

class SelfCheckoutServer extends Server {

    SelfCheckoutServer(int maxQueueLength, Supplier<Double> restTime, 
        Supplier<Double> serviceTimes) {
        super(maxQueueLength, restTime, serviceTimes);
    }


    SelfCheckoutServer(Customer customer, int maxQueueLength, boolean isServing,
        double nextAvailableTime, ImList<Customer> queue, Supplier<Double> restTimes) {
        super(customer, maxQueueLength, isServing, nextAvailableTime, queue, restTimes);
    }

    public boolean isHuman() {
        return false;
    }

    public Server serve(Customer customer) {
        return new SelfCheckoutServer(customer, this.maxQueueLength, 
                false, customer.getDoneTime(), this.queue, this.restTimes);

    }

    public Server addCustomerToQueue(Customer customer) {
        return new SelfCheckoutServer(customer, this.maxQueueLength, 
                    false, this.nextAvailableTime, this.queue.add(customer), this.restTimes);
    }

    public Server removeCustomerFromQueue() {
        return new SelfCheckoutServer(customer, this.maxQueueLength, 
            false, this.nextAvailableTime, this.queue.remove(0), this.restTimes);
        
    }

    public Server updateNextAvailableTime() {
        return new SelfCheckoutServer(this.customer, this.maxQueueLength, 
            false, this.customer.getServiceTime() + this.nextAvailableTime, 
            this.queue, this.restTimes);
    }

    public Server updateRestTimes() {
        return this;
    }

}
