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
        Response<Boolean> hasNext = controller.shipmentTrackerService.hasNext(shipmentId);
        while(!hasNext.isError() && hasNext.getObject()) {
            Response<DestinationToSend> response;
            if((response = controller.shipmentTrackerService.nextDestination(shipmentId)).isError()){
                System.out.println(response.getErrorMessage());
                return new MainMenuWindow();
            }
            DestinationToSend nextDestination = response.getObject();
            Response<Object> res = controller.shipmentTrackerService.updateWeight(shipmentId, reachedDestination(controller.scanner, nextDestination));
            if(res.isError() && res.getErrorMessage().equals("Overweight!"))
                handleOverWeight(controller);

        }
        System.out.println("Shipment Ended");
        // hasNext in the end returns a document object.
        return new MainMenuWindow();
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
    private void handleOverWeight(Controller controller){
        do {
            System.out.println("Please choose one of the following options to address the overweight issue:\n" +
                    "\n" +
                    "1. Replace truck.\n" +
                    "2. Change destination.\n" +
                    "3. Remove products.");
            int choice = controller.scanner.nextInt();

        }while(true);// fix this
    }
}
