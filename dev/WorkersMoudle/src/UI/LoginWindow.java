package UI;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import ServiceLayer.BranchManegerServices.BranchManagerService;
import ServiceLayer.Driver;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.EmployeeUI.EmpMainWindow;
import UI.HRManegerUI.HRMainWindow;
import UI.ManegerUI.ManagerMainWindow;

import java.net.StandardSocketOptions;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import presentation_layer.Controller;

public class LoginWindow extends Window{
    private final String deliveryDB = "persisted_layer.db";
    private int id;

    private Window nextWindow;

    private boolean exit = false;
    private Controller deliveryController;


    public LoginWindow(ServiceManager serviceManager) {
        super(serviceManager);
        deliveryController = new Controller(deliveryDB,
                (String address, DayOfTheWeek day, PartOfDay part, Predicate<Driver> driverPred) -> {
                    Response res = serviceManager.getEmployeeService().assignDriver(driverPred, day, part, address);
                    return res.ErrorOccured() ? null : (Driver) res.GetReturnValue();
                },
                (String address, DayOfTheWeek day, PartOfDay part) -> serviceManager.getBranchManagerService().addNeededRoles(address, day, part, List.of(Role.StoreKeeper)),
                (String address, DayOfTheWeek day, PartOfDay part) -> (Boolean) serviceManager.getBranchManagerService().isAssigned(address, day, part, Role.StoreKeeper).ReturnValue);
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
            Response isNew = this.serviceManager.getEmployeeService().isEmployeeNew(id);
            if(isNew.ErrorOccured()){
                System.out.println(isNew.ErrorMessage);
                continue;
            }
            if ((Boolean) isNew.ReturnValue){
                System.out.println("Choose a password for your account:");
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
            else if(!this.serviceManager.getEmployeeService().isDeliveryManager(id).ErrorOccured()) {
                deliveryController.run();
                this.nextWindow = this;
            }
            else
                this.nextWindow = new ManagerMainWindow(this.serviceManager, id, branchId);
        }else
            this.nextWindow = new EmpMainWindow(this.serviceManager, id, branchId);

    }

    public Window getNextWindow() {
        return nextWindow;
    }


}
