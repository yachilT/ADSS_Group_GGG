package presentation_layer;

import domain_layer.DestinationDocument;
import domain_layer.ShipmentDocument;

import javax.swing.text.Document;
import java.lang.annotation.Documented;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ShipmentHistoryWindow implements   Window{

    @Override
    public Window run(Controller controller) {

        ShipmentDocument document = selectDocument(controller.shipmentHistoryService.getDocuments(), controller.scanner);

        do {
            System.out.println("You have the following options:\n" + "\n" + "1. display destination documents of current shipment" + "2. Choose another document\n" + "0. Return to the main menu");
            int choice = controller.scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    DestinationDocument destDoc = chooseDestinationDocument(controller.shipmentHistoryService.getDestinationDocuments(document), controller.scanner);

                }
                case 2 -> {
                    document = selectDocument(controller.shipmentHistoryService.getDocuments(), controller.scanner);
                }
                case 3 -> {
                    return new MainMenuWindow();
                }
                default -> System.out.println("Error: Invalid Selection, try again.\n");
            }

        } while (true);
    }

    private DestinationDocument chooseDestinationDocument(List<DestinationDocument> documents, Scanner scanner) {
        int documentIndex;
        do {
            IntStream.range(0, documents.size()).forEach(index -> System.out.println((index + 1) + ". ID: " + documents.get(index).getId() + " address:" + documents.get(index).getAddress()));
            documentIndex = scanner.nextInt();
            if (documentIndex <= 0 || documentIndex > documents.size())
                System.out.println("Error: Invalid Selection\n");
        } while (documentIndex <= 0 || documentIndex > documents.size());
        return documents.get(documentIndex - 1);
    }

    private ShipmentDocument selectDocument(List<ShipmentDocument> documents, Scanner scanner) {
        if (documents.size() > 0) {
            int documentIndex;
            do {
                IntStream.range(0, documents.size()).forEach(index -> System.out.println((index + 1) + ". ID: " + documents.get(index).getId() + " Origin name: " + documents.get(index).getOriginContactName()));
                documentIndex = scanner.nextInt();
                if (documentIndex <= 0 || documentIndex > documents.size())
                    System.out.println("Error: Invalid Selection\n");
            } while (documentIndex <= 0 || documentIndex > documents.size());

            return documents.get(documentIndex - 1);
        } else {
            System.out.println("No documents found.\n");
            return null;
        }
    }
}
