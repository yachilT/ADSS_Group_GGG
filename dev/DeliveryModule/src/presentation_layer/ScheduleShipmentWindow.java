package presentation_layer;


import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import service_layer.*;

import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class ScheduleShipmentWindow implements Window {
    public ScheduleShipmentWindow(){
        super();
    }
    public Window run(Controller controller){
        DayOfTheWeek day = selectDayOfWeek(controller.scanner);
        PartOfDay part = selectPartOfDay(controller.scanner);

        SiteToSend origin = chooseOrigin(controller.areaService.getSites(), controller.scanner);
        AreaToSend area = chooseArea(controller.areaService.getAreas(), controller.scanner);

        List<DestinationToSend> destination = chooseDestinations(area.getSites(), controller.scanner, origin);
        Response<Integer> response = controller.shipmentSchedulerService.scheduleShipment(origin, destination, day, part);
        System.out.print("Scheduling shipment: " + origin.getAddress());
        destination.forEach(d -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException ignored) {}
            System.out.print(" -> " + d.getAddress());
        }
        );
        try {
            Thread.sleep(800);
        } catch (InterruptedException ignored) {}
        System.out.println(" -> " + origin.getAddress());


        if(response.isError()) {
            System.out.println(response.getErrorMessage());
            return new MainMenuWindow();
        }
        else{
            System.out.println("Shipment Scheduled successfully!");
            return new ShipmentTrackerWindow(response.getObject());
        }
    }

    private DayOfTheWeek selectDayOfWeek(Scanner scanner) {
        DayOfTheWeek dayOfTheWeek = null;

        while(dayOfTheWeek == null){
            AtomicInteger i = new AtomicInteger(1);
            System.out.println("Please select a day of the week from the options below: ");
            DayOfTheWeek[] days = DayOfTheWeek.values();
            Arrays.stream(days).forEach((day) -> System.out.println(i.getAndIncrement() + ". " + day));
            try {
                int choice = scanner.nextInt() - 1;
                dayOfTheWeek = days[choice];
                }
            catch (InputMismatchException ex){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
        }
        return dayOfTheWeek;
    }

    private PartOfDay selectPartOfDay(Scanner scanner) {
        PartOfDay partOfDay = null;

        while(partOfDay == null){
            AtomicInteger i = new AtomicInteger(1);
            System.out.println("Please select a part of the day from the options below: ");
            PartOfDay[] partsOfDay = PartOfDay.values();
            Arrays.stream(partsOfDay).forEach((part) -> System.out.println(i.getAndIncrement() + ". " + part));
            try {
                int choice = scanner.nextInt() - 1;
                partOfDay = partsOfDay[choice];
            }
            catch (InputMismatchException ex){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
        }
        return partOfDay;
    }

    private SiteToSend chooseOrigin(List<SiteToSend> sites, Scanner scanner){
        SiteToSend origin = null;
        while(origin == null) {
            System.out.println("Choose origin: ");
            int i = 1;
            for (SiteToSend s : sites)
                System.out.println(i++ + ". " + s.getAddress());
            try {
                int originIndex = scanner.nextInt() - 1;
                origin = (0 <= originIndex & originIndex < sites.size()) ? sites.get(originIndex) : null;
                if (origin == null)
                    invalidError();
            }
            catch (InputMismatchException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
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
            try {
                int areaIndex = scanner.nextInt() - 1;
                area = (0 <= areaIndex & areaIndex < areas.size()) ? areas.get(areaIndex) : null;
                if (area == null)
                    invalidError();
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }

        }
        return area;
    }
    private List<DestinationToSend> chooseDestinations(List<SiteToSend> sites,Scanner scanner, SiteToSend origin) {
        sites.removeIf(s -> s.equals(origin));
        List<DestinationToSend> destinations = new LinkedList<>();
        int destIndex = -1;
        while(!(destIndex == 0 | sites.size() == 0)) {
            System.out.println("Choose destination or enter 0 for exit.");
            IntStream.range(0, sites.size()).forEach(index -> System.out.println(index + 1 + ". " + sites.get(index).getAddress()));
            try {
                destIndex = scanner.nextInt();
                if (destIndex == 0 | sites.size() == 0) break;

                if (destIndex > sites.size() | destIndex < 0)
                    invalidError();
                else
                    destinations.add(createDestination(sites.remove(destIndex - 1), scanner));
            }
            catch (NoSuchElementException e){
                scanner.next();
                System.out.println("Invalid input: Please enter an integer.");
            }
        }
        return destinations;
    }

    private DestinationToSend createDestination(SiteToSend site, Scanner scanner) {
        List<ProductToSend> products = new LinkedList<>();
        String productName = "";
        int productAmount = -1;

        do {
            System.out.println(products.isEmpty() ? "Please enter product" : "Please enter product or type 0 for exist");
            productName = scanner.next();

            if (productName.equals("0") && products.size() == 0) {
                System.out.println("Error: No Products Selected");
                continue;
            } else if (productName.equals("0")) {
                break;
            }
            while(productAmount == -1) {
                try {
                    System.out.println("Enter Amount of product " + productName);
                    productAmount = scanner.nextInt();

                    if (productAmount >= 1) products.add(new ProductToSend(productName, productAmount));
                    else invalidError();

                }
                catch (NoSuchElementException e) {
                    scanner.next();
                    System.out.println("Invalid input: Please enter an integer.");
                }
            }
        }
        while(!productName.equals("0"));
        return new DestinationToSend(site, products);
    }
    private void invalidError(){
        System.out.println("Error: Invalid Selection\n");
    }
}
