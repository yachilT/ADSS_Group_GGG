package presentation_layer;

import domain_layer.*;
import service_layer.*;

import java.util.*;

public class Controller {
    public Scanner scanner;
    public AreaService areaService;
    public ShipmentSchedulerService shipmentSchedulerService;
    public ShipmentTrackerService shipmentTrackerService;
    public ShipmentHistoryService shipmentHistoryService;
    public Controller(){
        scanner = new Scanner(System.in);

        AreaFacade areaFacade = new AreaFacade();
        List<Area> areas = List.of(
                new Area("North", Set.of(
                        new Site("HaGanim 1, Haifa", "Yossi Cohen", "0586943858"),
                        new Site("Arlozerov 23, Afula", "Yehuda Levi", "05058939955"),
                        new Site("Hertzel 12, Haifa", "Ben Zalman", "0546583385"))
                ),
                new Area("South", Set.of(
                        new Site("Rager 83, Beer Sheva", "Haim Schmidt", "0527073993"),
                        new Site("David Ben Gurion 5, Ashkelon", "Roi Shavit", "0586943858"))
                ),
                new Area("East", new HashSet<>()),
                new Area("West", new HashSet<>())
        );
        areas.forEach(areaFacade::addArea);
        areaService = new AreaService(areaFacade);

        TruckFacade truckFacade = new TruckFacade();
        ShipmentScheduler shipmentScheduler = new ShipmentScheduler(new DriverFacade(),truckFacade);
        shipmentSchedulerService = new ShipmentSchedulerService(shipmentScheduler, areaFacade);

        ShipmentHistory shipmentHistory = new ShipmentHistory();
        shipmentHistoryService = new ShipmentHistoryService(shipmentHistory);

        shipmentTrackerService = new ShipmentTrackerService(shipmentScheduler, truckFacade, shipmentHistory);
    }
    public void run(){
        Window window = new MainMenuWindow();
        while(window != null)
            window = window.run(this);
        System.out.println("End system, goodbye!");
    }

}
