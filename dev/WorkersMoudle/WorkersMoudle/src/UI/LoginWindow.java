package UI;

import ServiceLayer.ServiceManager;
import UI.EmployeeUI.EmpMainWindow;
import UI.ManegerUI.ManagerMainWindow;

import java.net.StandardSocketOptions;
import java.util.Scanner;

public class LoginWindow extends Window{
    private int id;


    public LoginWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        boolean loggedIn = false;


        while (!loggedIn){
            System.out.println("LoginWindow");
            System.out.println("Enter your EMP ID:");
            this.id = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            if(!this.serviceManager.getEmployeeService().login(id,password).ErrorOccured()){
                System.out.println("Login successful");
                loggedIn = true;
            }else {
                System.out.println("Login failed");
            }
        }

        ForwardToNextWindow();

    }

    private void ForwardToNextWindow() {
        System.out.println("Forwarding to next window");
        if(!this.serviceManager.getEmployeeService().isManager(id).ErrorOccured())
            new ManagerMainWindow(this.serviceManager, id).run();
        else
            new EmpMainWindow(this.serviceManager, id).run();

    }


}
