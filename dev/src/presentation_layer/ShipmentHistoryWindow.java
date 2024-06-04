package presentation_layer;

import domain_layer.ShipmentDocument;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ShipmentHistoryWindow implements   Window{

    @Override
    public Window run(Controller controller) {
        selectAndDisplayDocument(controller.shipmentHistoryService.getDocuments(), controller.scanner);
        while(true) { // has a stop
            System.out.println("You have the following options:\n" + "\n" + "1. Choose another document\n" + "2. Return to the main menu");
            int choice = controller.scanner.nextInt();
            switch (choice) {
                case 1:
                    return new ShipmentHistoryWindow();
                case 2:
                    return new MainMenuWindow();
                default:
                    System.out.println("Error: Invalid Selection, try again.\n");
            }
        }
    }

    private void selectAndDisplayDocument(List<ShipmentDocument> documents, Scanner scanner){
        int documentIndex;
        do {
            IntStream.range(0, documents.size()).forEach(index -> System.out.println((index + 1) + ". ID: " + documents.get(index).getId() + " Origin name: " + documents.get(index).getOriginContactName()));
            documentIndex = scanner.nextInt();
            if(documentIndex <= 0 || documentIndex > documents.size()) System.out.println("Error: Invalid Selection\n");
        } while(documentIndex <= 0 || documentIndex > documents.size());
        System.out.println(documents.get(documentIndex - 1).toString());
    }
}
