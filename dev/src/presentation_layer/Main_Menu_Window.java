package presentation_layer;

import java.util.Scanner;

public class Main_Menu_Window {
    Scanner scanner = new Scanner(System.in);
    private boolean open;
    public Main_Menu_Window(){
        this.open = false;
    }
    public void run(){
        open = true;
        while(open){
            System.out.println("Main Menu:\n" + "1 - Schedule shipment.\n" + "2 - Show shipment's history.\n" + "0 - Exist");
            String input = scanner.nextLine();
            switch(input){
                case "1":
                    // shipment window.
                    break;
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
    }
    public void close(){
        open = false;
    }

}
