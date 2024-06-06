package UI;

import ServiceLayer.ServiceManager;

import java.util.Scanner;

public abstract class Window {

    protected ServiceManager serviceManager;
    protected Scanner scanner;
    public Window(ServiceManager serviceManager){
        this.serviceManager = serviceManager;
        this.scanner = new Scanner(System.in);
    }

    public abstract void run();
}
