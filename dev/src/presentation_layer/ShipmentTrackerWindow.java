package presentation_layer;

import service_layer.*;

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
        Response<ShipmentToSend> shipmentResponse = controller.shipmentTrackerService.trackShipment(shipmentId);
        if(shipmentResponse.isError()) {
            System.out.println(shipmentResponse.getErrorMessage());
            return new MainMenuWindow();
        }
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        System.out.println("---------------------------------------");
        System.out.println("Shipment from " + shipmentResponse.getObject().getOrigin().getAddress());
        System.out.println("Driver: " + shipmentResponse.getObject().getDriver().getName());
        TruckToSend truck = shipmentResponse.getObject().getTruck();
        System.out.println("Truck: " + truck.getTruckNumber() + " max weight: " + truck.getMaxWeight() + "kg");
        System.out.println("---------------------------------------");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        Response<Boolean> hasNext = controller.shipmentTrackerService.hasNext(shipmentId);
        while(!hasNext.isError() && hasNext.getObject()) {
            Response<DestinationToSend> response = controller.shipmentTrackerService.nextDestination(shipmentId);

            if(response.isError()) {
                System.out.println(response.getErrorMessage());
                return new MainMenuWindow();
            }
            DestinationToSend nextDestination = response.getObject();

            float newWeight = reachedDestination(controller.scanner, nextDestination);
            Response<Object> res = controller.shipmentTrackerService.updateWeight(shipmentId, newWeight);

            while(res.isError() && res.getErrorMessage().contains("Weight exceeds truck capacity")) {
                System.out.println(res.getErrorMessage());
                res = handleOverWeight(controller.shipmentTrackerService, controller.scanner, nextDestination, newWeight);
            }
            if (res.isError())
                System.out.println(res.getErrorMessage());

            hasNext = controller.shipmentTrackerService.hasNext(shipmentId);
        }

        System.out.println("You've returned to origin " + shipmentResponse.getObject().getOrigin().getAddress() + "!");
        System.out.println("Shipment has ended!");

        Response<Object> finishResponse = controller.shipmentTrackerService.finishTracking(shipmentId);
        if (finishResponse.isError())
            System.out.println(finishResponse.getErrorMessage());
        else {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            System.out.println("Shipment has been saved successfully!");
        }
        return new MainMenuWindow();
    }

    private float reachedDestination(Scanner scanner, DestinationToSend nextDestination) {
        float newWeight = -1;
        System.out.println("Reached destination!: " + nextDestination.getAddress());
        System.out.print("Please weigh the truck: ");
        while (newWeight <= 0){
            newWeight = scanner.nextInt();
            if(newWeight <= 0)
                System.out.println("Error: invalid weight, please reweigh again.");

        }
        return newWeight;
    }
    private Response<Object> handleOverWeight(ShipmentTrackerService shipmentTrackerService, Scanner scanner, DestinationToSend reachedDestination, float newWeight) {
        do {
            System.out.println("Please choose one of the following options to address the overweight issue:\n" +
                    "\n" +
                    "1. Replace truck.\n" +
                    "2. Change destination.\n" +
                    "3. Remove destination.\n" +
                    "4. Remove products.");
            switch (scanner.nextInt()) {
                case 1 -> {
                    Response<TruckToSend> res = shipmentTrackerService.changeTruck(shipmentId, newWeight);
                    if (res.isError())
                        return new Response<>(res.getErrorMessage());
                    else {
                        System.out.println("Truck changed successfully!");
                        System.out.println("New truck: " + res.getObject().toString());
                        return new Response<>();
                    }
                }
                case 2 -> {
                    return shipmentTrackerService.changeDestination(shipmentId, chooseDestinationToChange(shipmentTrackerService, scanner));
                }
                case 3 -> {
                    return shipmentTrackerService.removeDestination(shipmentId);
                }
                case 4 -> {
                    List<ProductToSend> productsToRemove = chooseProductsToRemove(shipmentTrackerService, scanner, reachedDestination);
                    return shipmentTrackerService.productsToRemain(shipmentId, reachedDestination.getProducts().stream().filter(product -> !productsToRemove.contains(product)).collect(Collectors.toList()));
                }
                default -> {
                    System.out.println("Error: Invalid selection, please try again\n");
                    continue;
                }
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
        } while(true);
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
