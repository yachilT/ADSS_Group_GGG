package UI;

import ServiceLayer.ServiceManager;

import java.net.StandardSocketOptions;
import java.util.Scanner;

public class LoginWindow extends Window{
    public LoginWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        boolean loggedIn = false;
        while (!loggedIn){
            System.out.println("LoginWindow");
            System.out.println("Enter your EMP ID:");
            String empID = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            if(this.serviceManager.getEmployeeService().login(Integer.valueOf(empID),password)){
                System.out.println("Login successful");
                loggedIn = true;
                System.out.println("ForwardToNextWindow");
            }else {
                System.out.println("Login failed");
            }
        }

        ForwardToNextWindow();

    }

    private void ForwardToNextWindow() {

    }
}
