import java.util.Comparator;

class EventCompare implements Comparator<Event> {

    public int compare(Event x, Event y) {
        if (x.getTime() > y.getTime()) {
            return 1;
        } else if (x.getTime() < y.getTime()) {
            return -1;
        } else { 
            return x.getCustomer().getCustomerID() 
                    - y.getCustomer().getCustomerID();                    
        }
    }
}

