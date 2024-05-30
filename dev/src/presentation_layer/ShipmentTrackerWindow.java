package presentation_layer;

import service_layer.DestinationToSend;
import service_layer.Response;

import java.util.Scanner;

public class ShipmentTrackerWindow extends Window {
    private int shipmentId;
    public ShipmentTrackerWindow(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public Window run(Controller controller) {
        controller.shipmentTrackerService.trackShipment(shipmentId);
        Response<Boolean> hasNext = controller.shipmentTrackerService.hasNext();
        while(!hasNext.isError() && hasNext.getObject()) {
            Response<DestinationToSend> response;
            if((response = controller.shipmentTrackerService.nextDestination(shipmentId)).isError()){
                System.out.println(response.getErrorMessage());
                return new MainMenuWindow();
            }
            DestinationToSend nextDestination = response.getObject();
            Response<String> r = controller.shipmentTrackerService.updateWeight(shipmentId, reachedDestination(controller.scanner, nextDestination));
            if(r.isError()){
                System.out.println(r.getErrorMessage());
                return new MainMenuWindow();
            }
        }
        System.out.println("Shipment Ended");
        return new MainMenuWindow();
    }

    @Override
    public void close() {

    }

    @Override
    public void open() {

    }
    private float reachedDestination(Scanner scanner, DestinationToSend nextDestination) {
        float newWeight = -1;
        System.out.println("Reached destination: " + nextDestination.getAdress());
        System.out.println("Please reweigh the truck.");
        while (newWeight <= 0){
            newWeight = scanner.nextInt();
            if(newWeight <= 0)
                System.out.println("Error: invalid weight, please reweigh again.");

        }
        return newWeight;
    }
}
