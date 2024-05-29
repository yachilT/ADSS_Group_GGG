package presentation_layer;

import domain_layer.AreaFacade;
import service_layer.*;

import java.util.Scanner;

public class Controller {
    public Scanner scanner;
    public AreaService areaService;
    public ShipmentSchedulerService shipmentSchedulerService;
    public Controller(){
        scanner = new Scanner(System.in);
        areaService = new AreaService(new AreaFacade());
        shipmentSchedulerService = new ShipmentSchedulerService();
    }
    public void run(){
        Window window = new MainMenuWindow();
        while(window != null)
            window = window.run(this);
    }

}
