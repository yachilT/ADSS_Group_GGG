package UI;

import ServiceLayer.BranchManegerServices.BranchManagerService;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.EmployeeUI.EmpMainWindow;
import UI.HRManegerUI.HRMainWindow;
import UI.ManegerUI.ManagerMainWindow;

import java.net.StandardSocketOptions;
import java.util.Scanner;

public class LoginWindow extends Window{
    private int id;

    private Window nextWindow;

    private boolean exit = false;


    public LoginWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        boolean loggedIn = false;

        while (!loggedIn){
            System.out.println("LoginWindow");

            //enter the system
            boolean flag = true;
            while(flag){
                System.out.println("Would you like to enter the system? (Y/N)");
                String input = scanner.nextLine();
                if(input.equals("N")){
                    exit = true;
                    return;
                }else if(!input.equals("Y")){
                    System.out.println("Invalid input, please enter Y or N");
                }else{
                    flag = false;
                }
            }
            //end of enter the system

            //login
            System.out.println("Enter your EMP ID:");
            this.id = Integer.parseInt(scanner.nextLine());
            String password = null;
            if(!this.serviceManager.getEmployeeService().isEmployeeNew(id).ErrorOccured()){
                System.out.println("Enter a password for your account:");
                password = scanner.nextLine();
                if(this.serviceManager.getEmployeeService().signUp(id,password).ErrorOccured()){
                    System.out.println("Sign up failed");
                    continue;
                }
                System.out.println("Sign up successful, logging in...");
            }else{
                System.out.println("Enter your password:");
                password = scanner.nextLine();
            }

            if(!this.serviceManager.getEmployeeService().login(id,password).ErrorOccured()){
                System.out.println("Login successful");
                loggedIn = true;
            }else {
                System.out.println("Login failed");
            }
            //end of login


        }

        ForwardToNextWindow();

    }

    public boolean isExit() {
        return exit;
    }





    private void ForwardToNextWindow() {
        Response response = this.serviceManager.getEmployeeService().getBranchId(id);
        if(response.ErrorOccured()){
            System.out.println("Error in getting branch id");
            return;
        }
        Integer branchId = (Integer) response.GetReturnValue();

        System.out.println("Forwarding to next window");
        if(!this.serviceManager.getEmployeeService().isManager(id).ErrorOccured()){
            if(!this.serviceManager.getEmployeeService().isHR(id).ErrorOccured())
                this.nextWindow = new HRMainWindow(this.serviceManager, id);
            else
                this.nextWindow = new ManagerMainWindow(this.serviceManager, id, branchId);
        }else
            this.nextWindow = new EmpMainWindow(this.serviceManager, id);

    }

    public Window getNextWindow() {
        return nextWindow;
    }


}
