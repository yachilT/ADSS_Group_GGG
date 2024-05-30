package presentation_layer;

import service_layer.DestinationToSend;
import service_layer.Response;

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



        }
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void open() {

    }
}
