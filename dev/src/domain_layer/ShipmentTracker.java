package domain_layer;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ShipmentTracker implements Iterator<Destination> {
    private final Shipment shipment;
    private final ShipmentHistory shipmentHistory;
    private int currentDstIndex;

    public ShipmentTracker(Shipment shipment, ShipmentHistory shipmentHistory) {
        this.currentDstIndex = 0;
        this.shipment = shipment;
        this.shipmentHistory = shipmentHistory;
    }

    public Truck changeTruck(TruckFacade truckFacade) throws NoSuchElementException {
        List<Truck> relevantTrucks = truckFacade.getAvailableTrucks().stream().filter(t -> t.isCompatible(shipment.getDriver()) && t.isOverweight(shipment.getCurrentDestination(currentDstIndex).getWeight())).toList();
        if (relevantTrucks.isEmpty())
            throw new NoSuchElementException("No relevant trucks available.");
        shipment.changeTruck(relevantTrucks.get(0));
        return shipment.getTruck();
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
        return currentDstIndex + 1 < shipment.getDestinationsSize();
    }

    @Override
    public Destination next() {
        return shipment.getCurrentDestination(++currentDstIndex);
    }


    public void setWeight(float newWeight) {
        shipment.setWeightForDst(currentDstIndex, newWeight);
    }

    private void tryFinishShipment() {
        if (!hasNext()) {
            shipment.finish();
            shipmentHistory.add(shipment.createDocument(), shipment.createDestinationDocuments());
        }

    }


    public List<Destination> getRemainingDestinations() {
        return shipment.getDestinationsFrom(currentDstIndex);
    }
}
