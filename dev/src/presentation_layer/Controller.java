package presentation_layer;

import domain_layer.*;
import service_layer.*;

import java.util.Scanner;

public class Controller {
    public Scanner scanner;
    public AreaService areaService;
    public ShipmentSchedulerService shipmentSchedulerService;
    public ShipmentTrackerService shipmentTrackerService;
    public Controller(){
        scanner = new Scanner(System.in);
        AreaFacade areaFacade = new AreaFacade();
        areaService = new AreaService(areaFacade);
        TruckFacade truckFacade = new TruckFacade();
        ShipmentScheduler shipmentScheduler = new ShipmentScheduler(new DriverFacade(),truckFacade);
        shipmentSchedulerService = new ShipmentSchedulerService(shipmentScheduler, areaFacade);
        shipmentTrackerService = new ShipmentTrackerService(shipmentScheduler, truckFacade);
    }
    public void run(){
        Window window = new MainMenuWindow();
        while(window != null)
            window = window.run(this);
    }

}
