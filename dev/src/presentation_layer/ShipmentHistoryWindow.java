package presentation_layer;

import domain_layer.ShipmentDocument;
import service_layer.ShipmentHistoryService;

import java.util.List;
import java.util.stream.IntStream;

public class ShipmentHistoryWindow extends  Window{

    @Override
    public Window run(Controller controller) {
        List<ShipmentDocument> documents  = controller.shipmentHistoryService.getDocuments();
        IntStream.range(0, documents.size()).forEach(index -> System.out.println(""));
    }

    @Override
    public void close() {

    }

    @Override
    public void open() {

    }
}
