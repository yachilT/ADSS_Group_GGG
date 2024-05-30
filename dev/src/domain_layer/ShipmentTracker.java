package domain_layer;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ShipmentTracker implements Iterator<Destination> {
    private Shipment shipment;

    private int currentDstIndex;

    public ShipmentTracker(Shipment shipment) {
        this.shipment = shipment;
    }

    public void changeTruck(TruckFacade truckFacade) throws NoSuchElementException {
        List<Truck> relevantTrucks = truckFacade.getAvailableTrucks().stream().filter(t -> t.isCompatible(shipment.getDriver()) && t.isOverweight(shipment.getCurrentDestination(currentDstIndex).getWeight())).toList();
        if (relevantTrucks.isEmpty())
                throw new NoSuchElementException("No relevant trucks available.");
        shipment.changeTruck(relevantTrucks.get(0));
    }

    public void removeDestination() {
        shipment.removeDestination(currentDstIndex);
    }

    public void ChangeDestination(Destination dst) {
        shipment.changeDestination(currentDstIndex, dst);
    }

    public void productsToRemain(List<ProductAmount> products) {
        shipment.productsToRemain(products, currentDstIndex);

    }


    @Override
    public boolean hasNext() {
        return currentDstIndex < shipment.getDestinationsSize();
    }

    @Override
    public Destination next() {
        return shipment.getCurrentDestination(currentDstIndex++);
    }
}
