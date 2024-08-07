package service_layer;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import domain_layer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ShipmentSchedulerService {
    private ShipmentScheduler shipmentScheduler;
    private AreaFacade areaFacade;

    public ShipmentSchedulerService(ShipmentScheduler shipmentScheduler, AreaFacade areaFacade) {
        this.shipmentScheduler = shipmentScheduler;
        this.areaFacade = areaFacade;
    }

    public Response<Integer> scheduleShipment(SiteToSend originToSend, List<DestinationToSend> sitesToSend, DayOfTheWeek day, PartOfDay part) {
        List<Site> originList = areaFacade.getSites().stream().filter(s -> s.getAddress().equals(originToSend.getAddress())).toList();
        if (originList.size() != 1) {
            return new Response<>("Error: Origin not found");
        }

        List<Destination> dsts = new ArrayList<>();
        for (DestinationToSend dst : sitesToSend) {
            List<Site> dstList = areaFacade.getSites().stream().filter(s -> s.getAddress().equals(dst.getAddress())).toList();
            if (dstList.size() != 1) {
                return new Response<>("Error: Destination not found");
            }
            dsts.add(new Destination(dstList.get(0), dst.getProducts().stream().map(ProductToSend::toProductAmount).collect(Collectors.toList())));
        }


        try {
            return new Response<>(shipmentScheduler.scheduleShipment(originList.get(0), dsts, day, part));
        } catch (NoSuchElementException e) {
            return new Response<>("Error: " + e.getMessage());
        }
    }
    public Response<List<Integer>> getShipmentIds() {
        return new Response<>(shipmentScheduler.getShipmentIds());
    }
}
