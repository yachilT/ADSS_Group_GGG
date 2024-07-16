package presentation_layer;

import domain_layer.*;
import service_layer.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Controller {
    public Scanner scanner;
    public AreaService areaService;
    public ShipmentSchedulerService shipmentSchedulerService;
    public ShipmentTrackerService shipmentTrackerService;
    public ShipmentHistoryService shipmentHistoryService;

    public Controller(String dbPath,
                      Function<Predicate<Driver>, Driver> driverGetter,
                      Consumer<String> storeKeeperAssigner,
                      Predicate<String> storeKeeperChecker){
        scanner = new Scanner(System.in);
//        Site haifa1 = new Site("HaGanim 1, Haifa", "Yossi Cohen", "0586943858");
//        Site afula = new Site("Arlozerov 23, Afula", "Yehuda Levi", "05058939955");
//        Site haifa2 = new Site("Hertzel 12, Haifa", "Ben Zalman", "0546583385");

        AreaFacade areaFacade = new AreaFacade(dbPath);
        areaFacade.loadAll();
//        List<Area> areas = List.of(
//                new Area("North", Set.of(
//                        haifa1,
//                        afula,
//                        haifa2)
//                ),
//                new Area("South", Set.of(
//                        new Site("Rager 83, Beer Sheva", "Haim Schmidt", "0527073993"),
//                        new Site("David Ben Gurion 5, Ashkelon", "Roi Shavit", "0586943858"))
//                ),
//                new Area("Jerusalem", Set.of(
//                        new Site("Yitzhak Rabin 12, Jerusalem", "Yossi Cohen", "0586943858"),
//                        new Site("Nissim Bachar 13, Jerusalem", "Menashe Naim", "0503774857"),
//                        new Site("Moshe Dayan 107, Pisgat Ze'ev", "Yoav Duani", "0524995578" )
//                )),
//                new Area("HaMerkaz", Set.of(
//                        new Site("Hertzel 12, Tel Aviv", "Israel Ben Simon", "0546583385"),
//                        new Site("Arlozerov 23, Ramat Gan", "Yossi Shabtai", "05058939955"),
//                        new Site("Haim Bar Lev 5, Givatayim", "Hen Banai", "0544682285"),
//                        new Site("Yigal Alon 12, Herzliya", "Uri Itzkovitch", "0503885739"),
//                        new Site("Menachem Begin 1, Rishon LeZion", "Hezi Sa'ar", "0548885733")
//                ))
//        );
        //areas.forEach(areaFacade::addArea);
        areaService = new AreaService(areaFacade);

        TruckFacade truckFacade = new TruckFacade(true, dbPath);
        truckFacade.loadAll();

//        truckFacade.addTruck(0, "Toyota", 100, 1000);
//        truckFacade.addTruck(1, "Mitsubishi", 200, 2000);

        DriverFacade driverFacade = new DriverFacade(true, dbPath);
        driverFacade.loadAll();
//        driverFacade.addDriver(new Driver(0, "Rami Hen", new License(2100)));
//        driverFacade.addDriver(new Driver(1, "Yossi Cohen", new License(1100)));


//        ShipmentDocument shipmentDocument = new ShipmentDocument(100,
//                "Rager 83, Beer Sheva",
//                "Haim Schmidt",
//                "0527073993",
//                LocalDate.now().toString(),
//                LocalTime.now().toString(),
//                0,
//                "Rami Hen");
        ShipmentHistory shipmentHistory = new ShipmentHistory(true, dbPath);
        int startingIndex = shipmentHistory.loadAll();

        ShipmentScheduler shipmentScheduler = new ShipmentScheduler(driverFacade,
                truckFacade,
                startingIndex,
                driverGetter,
                storeKeeperAssigner);
        shipmentSchedulerService = new ShipmentSchedulerService(shipmentScheduler, areaFacade);
//        shipmentHistory.add(shipmentDocument, List.of(
//                new DestinationDocument(new Destination(haifa1, List.of(new ProductAmount("Milk", 10))), 12, 100, 900),
//                new DestinationDocument(new Destination(haifa2, List.of(new ProductAmount("bread", 100), new ProductAmount("cheese", 200))), 13, 100,800)));
        shipmentHistoryService = new ShipmentHistoryService(shipmentHistory);

        shipmentTrackerService = new ShipmentTrackerService(shipmentScheduler, truckFacade, shipmentHistory, storeKeeperChecker);
    }
    public void run(){
        Window window = new MainMenuWindow();
        while(window != null)
            window = window.run(this);
        System.out.println("End system, goodbye!");
    }

}
