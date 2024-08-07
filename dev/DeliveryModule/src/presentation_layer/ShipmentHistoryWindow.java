package presentation_layer;

import domain_layer.DestinationDocument;
import domain_layer.ShipmentDocument;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ShipmentHistoryWindow implements   Window{

    @Override
    public Window run(Controller controller) {

        ShipmentDocument document = selectDocument(controller.shipmentHistoryService.getDocuments(), controller.scanner);
        System.out.println(document);
        do {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("You have the following options:\n" + "\n" + "1. display destination documents of current shipment\n" + "2. Choose another document\n" + "0. Return to the main menu");
            try {
                int choice = controller.scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        DestinationDocument destDoc = selectDestinationDocument(controller.shipmentHistoryService.getDestinationDocuments(document), controller.scanner);
                        System.out.println(destDoc);
                    }
                    case 2 -> {
                        document = selectDocument(controller.shipmentHistoryService.getDocuments(), controller.scanner);
                        System.out.println(document);
                    }
                    case 0 -> {
                        return new MainMenuWindow();
                    }
                    default -> System.out.println("Error: Invalid Selection, try again.\n");
                }
            }
            catch (NoSuchElementException e){
                controller.scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }

        } while (true);
    }

    private DestinationDocument selectDestinationDocument(List<DestinationDocument> documents, Scanner scanner) {

        int documentIndex = -1;
        do {
            IntStream.range(0, documents.size()).forEach(index -> System.out.println((index + 1) + ". ID: " + documents.get(index).getDestinationDocId() + " address: " + documents.get(index).getAddress()));
            try {
                documentIndex = scanner.nextInt();
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
                continue;
            }

            if (documentIndex <= 0 || documentIndex > documents.size())
                System.out.println("Error: Invalid Selection\n");
        } while (documentIndex <= 0 || documentIndex > documents.size());
        return documents.get(documentIndex - 1);
    }
    // need to fix according to
    private ShipmentDocument selectDocument(List<ShipmentDocument> documents, Scanner scanner) {
        if (documents.size() > 0) {
            System.out.println("Please choose a document by entering its number");
            int documentIndex = -1;
            do {
                IntStream.range(0, documents.size()).forEach(index -> System.out.println((index + 1) + ". ID: " + documents.get(index).getShipmentId() + " Origin address: " + documents.get(index).getOriginAddress()));
                try {
                    documentIndex = scanner.nextInt();
                    if (documentIndex <= 0 || documentIndex > documents.size())
                        System.out.println("Error: Invalid Selection\n");
                }
                catch (NoSuchElementException e){
                    scanner.next();
                    System.out.println("Invalid input: Please enter an integer.");
                }
            } while (documentIndex <= 0 || documentIndex > documents.size());

            return documents.get(documentIndex - 1);
        } else {
            System.out.println("No documents found.\n");
            return null;
        }
    }
}
