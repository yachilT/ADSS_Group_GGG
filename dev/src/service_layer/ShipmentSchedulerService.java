package service_layer;

import domain_layer.AreaFacade;
import domain_layer.Destination;
import domain_layer.ShipmentScheduler;
import domain_layer.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShipmentSchedulerService {
    private ShipmentScheduler shipmentScheduler;
    private AreaFacade areaFacade;

    public ShipmentSchedulerService(ShipmentScheduler shipmentScheduler, AreaFacade areaFacade) {
        this.shipmentScheduler = shipmentScheduler;
        this.areaFacade = areaFacade;
    }

    public void scheduleShipment(SiteToSend originToSend, List<DestinationToSend> sitesToSend) {
        List<Site> originList = areaFacade.getSites().stream().filter(s -> s.getAddress().equals(originToSend.getAdress())).toList();
        if (originList.size() != 1) {
            // return Error
        }

        List<Destination> dsts = new ArrayList<>();
        for (DestinationToSend dst : sitesToSend) {
            List<Site> dstList = areaFacade.getSites().stream().filter(s -> s.getAddress().equals(dst.getAdress())).toList();
            if (dstList.size() != 1) {
                // return Error
            }
            dsts.add(new Destination(dstList.get(0), dst.getProducts().stream().map(ProductToSend::toProductAmount).toList()));
        }


        try {
            shipmentScheduler.scheduleShipment(originList.get(0), dsts);
        } catch (Exception e) {

        }

    }

}
