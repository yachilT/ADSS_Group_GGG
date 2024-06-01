package presentation_layer;


import service_layer.*;

import java.util.LinkedList;
import java.util.List;

import java.util.Scanner;


public class ScheduleShipmentWindow extends Window {
    public ScheduleShipmentWindow(){
        super();
    }
    public Window run(Controller controller){
        SiteToSend origin = chooseOrigin(controller.areaService.getSites(), controller.scanner);
        AreaToSend area = chooseArea(controller.areaService.getAreas(), controller.scanner);

        List<DestinationToSend> destination = chooseDestinations(area.getSites(), controller.scanner);
        Response<Integer> response = controller.shipmentSchedulerService.scheduleShipment(origin, destination);
        if(response.isError()) {
            System.out.println(response.getErrorMessage());
            return new MainMenuWindow();
        }
        else{
            return new ShipmentTrackerWindow(response.getObject());
        }
    }
    public void close(){

    }
    public void open(){

    }
    private SiteToSend chooseOrigin(List<SiteToSend> sites, Scanner scanner){
        SiteToSend origin = null;
        while(origin == null) {
            System.out.println("Choose origin: ");
            int i = 1;
            for (SiteToSend s : sites)
                System.out.println(i++ + ". " + s.getAdress());
            int originIndex = scanner.nextInt() - 1;
            origin = (0 <= originIndex & originIndex < sites.size()) ? sites.get(originIndex) : null;
            if(origin == null)
                invalidError();
        }
        return origin;
    }
    private AreaToSend chooseArea(List<AreaToSend> areas, Scanner scanner){
        AreaToSend area = null;
        while(area == null){
            System.out.println("Choose area: ");
            int i = 1;
            for(AreaToSend a: areas)
                System.out.println(i++ + ". " + a.getAreaName());
            int areaIndex = scanner.nextInt() - 1;
            area = (0 <= areaIndex & areaIndex < areas.size()) ? areas.get(areaIndex) : null;
            if(area == null)
                invalidError();

        }
        return area;
    }
    private List<DestinationToSend> chooseDestinations(List<SiteToSend> sites,Scanner scanner) {
        List<DestinationToSend> destinations = new LinkedList<>();
        int destIndex = -1;
        while(!(destIndex == 0 | sites.size() == 0)) {
            int index = 1;
            System.out.println("Choose destination or enter 0 for exit.");
            for (SiteToSend s : sites) {
                System.out.println(index++ + ". " + s.getAdress());
                destIndex = scanner.nextInt();
                if(destIndex == 0 | sites.size() == 0)
                    break;
                destIndex--;
                if(destIndex >= sites.size())
                    invalidError();
                else{
                    SiteToSend site = sites.remove(destIndex);
                    destinations.add(createDestination(site, scanner));
                }
            }
        }
        return destinations;
    }
    private DestinationToSend createDestination(SiteToSend site, Scanner scanner) {
        List<ProductToSend> products = new LinkedList<>();
        String productName = "";
        int productAmount = -1;
        while(products.isEmpty()) {
            System.out.println("Please enter product");
            productName = scanner.nextLine();
            System.out.println("Enter Amount of product " + productName);
            productAmount = scanner.nextInt();
           if(!(productName.equals("0") & productAmount <= 0))
               products.add(new ProductToSend(productName, productAmount));
           else
            invalidError();
        }
        do{
            System.out.println("Please enter product or type 0 for exist");
            productName = scanner.nextLine();
            System.out.println("Enter Amount of product " + productName);
            productAmount = scanner.nextInt();

            if(productName.equals("X")) break;

            if(productAmount >= 1)  products.add(new ProductToSend(productName, productAmount));
            else invalidError();
        }
        while(productName.equals("0"));
        return new DestinationToSend(site, products);
    }
    private void invalidError(){
        System.out.println("Error: Invalid Selection\n");
    }
}
