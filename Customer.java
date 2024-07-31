import java.util.function.Supplier;

class Customer {
    private final int customerID;
    private final Supplier<Double> defaultServiceTime;
    private final double arrivalTime;

    Customer() {
        this.customerID = 0;
        this.defaultServiceTime = new DefaultServiceTime();
        this.arrivalTime = 0.0;
    }

    Customer(int customerID, Supplier<Double> defaultServiceTime, double arrivalTime) {
        this.customerID = customerID;
        this.defaultServiceTime = defaultServiceTime;
        this.arrivalTime = arrivalTime;
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public double getServiceTime() {
        return this.defaultServiceTime.get();
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getDoneTime() {
        return this.arrivalTime + this.defaultServiceTime.get();
    }

    public String toString() {
        return "" + this.getCustomerID();
    }
}
