import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfCheckServers;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    Simulator(int numOfServers, int numOfSelfCheckServers, int qmax,  ImList<Double> arrivalTimes, 
                Supplier<Double> serviceTimes, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfCheckServers = numOfSelfCheckServers;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    public String simulate() {
        String finalString = "";
        PQ<Event> pqEvent = new PQ<Event>(new EventCompare());
        ImList<Server> servers = new ImList<Server>();

        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(qmax, restTimes, serviceTimes));
        }

        for (int i = 0; i < this.numOfSelfCheckServers; i++) {
            servers = servers.add(new SelfCheckoutServer(qmax, restTimes, serviceTimes));
        }

        for (int i = 0; i < this.arrivalTimes.size(); i++) {
            double arrive = this.arrivalTimes.get(i);
            Customer newCustomer = new Customer(i + 1, serviceTimes, arrive);
            pqEvent = pqEvent.add(new ArriveEvent(newCustomer, arrive, true));
        }

        double wait = 0;
        int served = 0;
        int leave = 0;

        while (!pqEvent.isEmpty()) {
            Pair<Event, PQ<Event>> pair1 = pqEvent.poll();
            Event currEvent = pair1.first();
            pqEvent = pair1.second();
            finalString += currEvent.toString();

            
            Pair<ImList<Server>, PQ<Event>> result = currEvent.getNextEvent(servers, pqEvent);
            servers = result.first();
            pqEvent = result.second();

            wait = currEvent.updateWait(wait);
            served = currEvent.updateServe(served);
            leave = currEvent.updateLeave(leave);
        }

        if (served == 0) {
            finalString += String.format("[%.3f %d %d]", wait, served, leave);
        } else {
            finalString += String.format("[%.3f %d %d]", wait / served, served, leave);
        }

        return finalString;
    }
}
