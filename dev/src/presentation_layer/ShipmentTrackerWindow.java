package presentation_layer;

import service_layer.DestinationToSend;
import service_layer.ProductToSend;
import service_layer.Response;
import service_layer.ShipmentTrackerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShipmentTrackerWindow implements Window {
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
            while(res.isError() && res.getErrorMessage().contains("Weight exceeds truck capacity"))
                res = handleOverWeight(controller.shipmentTrackerService, controller.scanner, nextDestination);

        }
        System.out.println("Shipment Ended");
        // hasNext in the end returns a document object.
        return new MainMenuWindow();
    }

    private float reachedDestination(Scanner scanner, DestinationToSend nextDestination) {
        float newWeight = -1;
        System.out.println("Reached destination: " + nextDestination.getAddress());
        System.out.println("Please reweigh the truck.");
        while (newWeight <= 0){
            newWeight = scanner.nextInt();
            if(newWeight <= 0)
                System.out.println("Error: invalid weight, please reweigh again.");

        }
        return newWeight;
    }
    private Response<Object> handleOverWeight(ShipmentTrackerService shipmentTrackerService, Scanner scanner, DestinationToSend reachedDestination){
        do {
            System.out.println("Please choose one of the following options to address the overweight issue:\n" +
                    "\n" +
                    "1. Replace truck.\n" +
                    "2. Change destination.\n" +
                    "3. Remove destination.\n" +
                    "4. Remove products.");
            switch (scanner.nextInt()){
                case 1:
                    return shipmentTrackerService.changeTruck(shipmentId);
                case 2:
                    return shipmentTrackerService.changeDestination(shipmentId, chooseDestinationToChange(shipmentTrackerService, scanner));
                case 3:
                    return shipmentTrackerService.removeDestination(shipmentId);
                case 4:
                    List<ProductToSend> productsToRemove = chooseProductsToRemove(shipmentTrackerService, scanner, reachedDestination);
                    return shipmentTrackerService.productsToRemain(shipmentId, reachedDestination.getProducts().stream().filter(product -> !productsToRemove.contains(product)).collect(Collectors.toList()));
                default:
                    System.out.println("Error: Invalid selection, please try again\n");
                    continue;

            }

        }while(true);// fix this
    }
    private DestinationToSend chooseDestinationToChange(ShipmentTrackerService shipmentTrackerService, Scanner scanner) {
        List<DestinationToSend> remainingDestinations = shipmentTrackerService.remainingDestinations(shipmentId);
        do {
            System.out.println("Select a destination: ");
            IntStream.range(0,remainingDestinations.size()).forEach(index -> System.out.println((index + 1) + ". " + remainingDestinations.get(index).getAddress()));
            int choice = scanner.nextInt();
            if(choice <= 0 | choice > remainingDestinations.size()){
                System.out.println("Error: Invalid selection! please select again.");
                continue;
            }
            else
                return remainingDestinations.get(choice - 1);
        }while(true);
    }
    private List<ProductToSend> chooseProductsToRemove(ShipmentTrackerService shipmentTrackerService, Scanner scanner, DestinationToSend reachedDestination){
        List<ProductToSend> productsToRemove = new ArrayList<>();
        List<ProductToSend> productsOfDestination = reachedDestination.getProducts();
        do{
            System.out.println("Choose product to Remove " + (productsToRemove.size() == 0 ? "." : "or choose 0 for exist."));
            IntStream.range(0, productsOfDestination.size()).forEach(index -> System.out.println((index + 1) + ". " + productsOfDestination.get(index).getProductName()));
            int choice = scanner.nextInt();
            if(productsToRemove.size() != 0 && choice == 0)
                break;
            if(choice <= 0 | choice > productsOfDestination.size()){
                System.out.println("Error: Invalid Selection! select again.\n");
                continue;
            }
            productsToRemove.add(productsOfDestination.remove(choice - 1));
        } while(productsOfDestination.size() != 0);
        return productsToRemove;
    }
}
