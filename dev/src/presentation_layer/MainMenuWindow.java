package presentation_layer;

import java.util.Scanner;

public class MainMenuWindow implements Window{
    Scanner scanner = new Scanner(System.in);
    private boolean open;
    public MainMenuWindow(){
        this.open = false;
    }
    public Window run(Controller controller){
        open = true;
        while(open){
            System.out.println("Main Menu:\n" + "1 - Schedule shipment.\n" + "2 - Show shipment's history.\n" + "0 - Exist");
            String input = scanner.next();
            switch(input){
                case "1":
                    return new ScheduleShipmentWindow();
                case "2":
                    // history window.
                    break;
                case "0":
                    close();
                    break;
                default:
                    System.out.println("Error: Invalid Selection");
            }
        }
        return null;
    }
    private void close(){
        open = false;
    }

}
