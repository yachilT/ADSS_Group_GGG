package presentation_layer;

import service_layer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
                System.out.println("Shipment cancelled");
                return new MainMenuWindow();
            }

            DestinationToSend nextDestination = response.getObject();
            System.out.println("You've reached: " + nextDestination.getAddress() + "!");

            float newWeight = getWeightInput(controller.scanner);
            Response<Object> weightResponse = controller.shipmentTrackerService.updateWeight(shipmentId, newWeight);
            if (weightResponse.isError()) {
                System.out.println(weightResponse.getErrorMessage());
                boolean shouldChooseAction = weightResponse.getErrorMessage().contains("Weight exceeds truck capacity");
                while (shouldChooseAction) {
                    int choice = getActionNumber(controller.scanner);
                    switch (choice) {
                        case 1 -> shouldChooseAction = handleTruckChange(controller.shipmentTrackerService, controller.scanner, newWeight);
                        case 2 -> shouldChooseAction = handleDestinationChange(controller.shipmentTrackerService, controller.scanner);
                        case 3 -> shouldChooseAction = handleDestinationRemoval(controller.shipmentTrackerService);
                        case 4 -> shouldChooseAction = handleProductsRemoval(controller.shipmentTrackerService, controller.scanner, nextDestination);
                    }
                }
            }
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



    private float getWeightInput(Scanner scanner) {
        float newWeight = -1;
        System.out.print("Please weigh the truck: ");
        while (newWeight <= 0){
            try {
                newWeight = scanner.nextInt();
                if (newWeight <= 0)
                    System.out.println("Error: invalid weight, please reweigh again.");
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }

        }
        return newWeight;
    }

    private int getActionNumber(Scanner scanner) {
        int actionNumber = -1;
        while (actionNumber < 0 || actionNumber > 4) {
            System.out.println("Please choose one of the following options to address the overweight issue:\n" +
                    "\n" +
                    "1. Replace truck.\n" +
                    "2. Change destination.\n" +
                    "3. Remove destination.\n" +
                    "4. Remove products.");
            try {
                actionNumber = scanner.nextInt();
                if (actionNumber < 0 || actionNumber > 4)
                    System.out.println("Error: Invalid selection, please try again\n");
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
        }
        return actionNumber;
    }

    // returns true if user should choose action again, false otherwise
    private boolean handleTruckChange(ShipmentTrackerService shipmentTrackerService, Scanner scanner, float newWeight) {
        Response<TruckToSend> res = shipmentTrackerService.changeTruck(shipmentId, newWeight);
        if (res.isError()) {
            System.out.println(res.getErrorMessage());
            return true;
        }

        System.out.println("Truck changed successfully!");
        System.out.println("New truck: " + res.getObject().toString());
        newWeight = getWeightInput(scanner);
        Response<Object> weightResponse = shipmentTrackerService.updateWeight(shipmentId, newWeight);

        if (weightResponse.isError()) {
            System.out.println(weightResponse.getErrorMessage());
            return weightResponse.getErrorMessage().contains("Weight exceeds truck capacity");
        }
        return false;
    }

    private boolean handleDestinationChange(ShipmentTrackerService shipmentTrackerService, Scanner scanner) {
        Response<List<DestinationToSend>> res = shipmentTrackerService.changeDestination(shipmentId, chooseDestinationToChange(shipmentTrackerService, scanner));
        if (res.isError()) {
            System.out.println(res.getErrorMessage());
            return true;
        }

        System.out.println("Destination changed successfully!");
        return printPath(res.getObject());
    }

    private boolean printPath(List<DestinationToSend> path) {
        System.out.println("new shipment plan: ");
        path.forEach(destination -> {
            try { Thread.sleep(700); } catch (InterruptedException ignored) {}
            System.out.print(" -> " + destination.getAddress());
        }
        );
        System.out.println();
        return false;
    }

    private boolean handleDestinationRemoval(ShipmentTrackerService shipmentTrackerService) {
        Response<List<DestinationToSend>> res = shipmentTrackerService.removeDestination(shipmentId);
        if (res.isError()) {
            System.out.println(res.getErrorMessage());
            return true;
        }

        System.out.println("Destination removed successfully!");
        return printPath(res.getObject());
    }

    private boolean handleProductsRemoval(ShipmentTrackerService shipmentTrackerService, Scanner scanner, DestinationToSend reachedDestination) {
        Response<Object> res = shipmentTrackerService.productsToRemain(shipmentId, chooseProductsToRemove(scanner, reachedDestination));
        if (res.isError()) {
            System.out.println(res.getErrorMessage());
            return true;
        }
        System.out.println("Products removed successfully!");

        return false;
    }


    private int chooseDestinationToChange(ShipmentTrackerService shipmentTrackerService, Scanner scanner) {
        List<DestinationToSend> remainingDestinations = shipmentTrackerService.remainingDestinations(shipmentId);
        do {
            System.out.println("Select a destination: ");
            IntStream.range(0,remainingDestinations.size()).forEach(index -> System.out.println((index + 1) + ". " + remainingDestinations.get(index).getAddress()));
            try {
                int choice = scanner.nextInt();
                if (choice <= 0 | choice > remainingDestinations.size()) {
                    System.out.println("Error: Invalid selection! please select again.");
                } else
                    return choice - 1;
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
        } while(true);
    }
    private List<ProductToSend> chooseProductsToRemove(Scanner scanner, DestinationToSend reachedDestination){
        List<ProductToSend> productsOfDestination = reachedDestination.getProducts();
        int originalSize = productsOfDestination.size();
        do{
            System.out.println("Choose product to Remove " + (productsOfDestination.size() == originalSize ? ":" : "or choose 0 for exist:"));
            IntStream.range(0, productsOfDestination.size()).forEach(index -> System.out.println((index + 1) + ". " + productsOfDestination.get(index).getProductName()));
            try {
                int choice = scanner.nextInt();
                if (productsOfDestination.size() != originalSize && choice == 0)
                    break;
                if (choice <= 0 | choice > productsOfDestination.size()) {
                    System.out.println("Error: Invalid Selection! select again.\n");
                    continue;
                }
                productsOfDestination.remove(choice - 1);
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
        } while(productsOfDestination.size() != 0);
        return productsOfDestination;
    }
}
