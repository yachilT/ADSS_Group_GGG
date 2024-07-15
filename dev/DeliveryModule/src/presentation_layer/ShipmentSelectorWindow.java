package presentation_layer;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ShipmentSelectorWindow implements Window{

    @Override
    public Window run(Controller controller) {
        Scanner scanner = controller.scanner;
        boolean choose = false;
        do{
            AtomicInteger i = new AtomicInteger(1);
            List<Integer> shipmentIds = controller.shipmentSchedulerService.getShipmentIds().getObject();
            if(shipmentIds.size() == 0){
                System.out.println("No shipments to track. Returning to the main menu.");
                return new MainMenuWindow();
            }

            System.out.println("Choose a shipment to track or choose 0 for exit:\n" + shipmentIds.stream().reduce("", (acc, id) -> acc + i.getAndIncrement() + ". " + id + "\n", (acc1, acc2) -> acc1 + acc2) + "0. Return to the main menu");
            try {
                int choice = scanner.nextInt();

                if (choice == 0) {
                    return new MainMenuWindow();
                } else if (shipmentIds.contains(choice)) {
                    return new ShipmentTrackerWindow(choice);
                } else {
                    System.out.println("Error: Invalid Selection, try again.\n");
                }
            }  catch(Exception e){
                System.out.println("Invalid input: Please enter an integer.");
                continue;
            }
        } while(true);
    }
}
