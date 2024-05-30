package presentation_layer;

import domain_layer.AreaFacade;
import domain_layer.DriverFacade;
import domain_layer.ShipmentScheduler;
import domain_layer.TruckFacade;
import service_layer.*;

import java.util.Scanner;

public class Controller {
    public Scanner scanner;
    public AreaService areaService;
    public ShipmentSchedulerService shipmentSchedulerService;
    public Controller(){
        scanner = new Scanner(System.in);
        AreaFacade areaFacade = new AreaFacade();
        areaService = new AreaService(areaFacade);
        shipmentSchedulerService = new ShipmentSchedulerService(new ShipmentScheduler(new DriverFacade(), new TruckFacade()), areaFacade);
    }
    public void run(){
        Window window = new MainMenuWindow();
        while(window != null)
            window = window.run(this);
    }

}
