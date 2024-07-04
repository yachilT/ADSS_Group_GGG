package UI;

import DomainLayer.Employees.Role;
import ServiceLayer.BranchManegerServices.BranchManagerService;
import ServiceLayer.EmployeeServices.EmployeeService;
import ServiceLayer.HRManegerServices.HRManagerService;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;

import java.util.ArrayList;
import java.util.Scanner;

public class UIController {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Run example? (Y/Other)");
//        String input = scanner.nextLine();
//        boolean runExample = input.equals("Y");
//        if(runExample)
//            example();
//        else
//            new CreateSystemWindow(ServiceManager.getInstance()).run();

        System.out.println("Load data or create new system? (L/C)");
        String input = scanner.nextLine();
        boolean loadData = input.equals("L");
        if(loadData)
            ServiceManager.getInstance().loadDatabase();
        else{
            ServiceManager.getInstance().getBranchManagerService().deleteData();
            new CreateSystemWindow(ServiceManager.getInstance()).run();
        }



        Window nextWindow = null;
        boolean exit = false;
        do{
            LoginWindow login = new LoginWindow(ServiceManager.getInstance());
            login.run();
            if(login.isExit())
                exit = true;
            else{
                nextWindow = login.getNextWindow();
                nextWindow.run();
            }
        }while(!exit);
    }

    public static void example(){
        ServiceManager serviceManager = ServiceManager.getInstance();
        EmployeeService employeeService = serviceManager.getEmployeeService();
        HRManagerService hrManagerService = serviceManager.getHRManagerService();
        BranchManagerService branchManagerService = serviceManager.getBranchManagerService();

        // Create Hr Manager
        hrManagerService.hrRegister("Dor", 233321,1000000, "123");
        // Create Branch Manager & branch
        hrManagerService.createBranch("Tel Aviv", "Dizengoff 50", "Yonatan", 233322, 10000);
        // Create Employees
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.Cashier); roles.add(Role.ShiftManager); roles.add(Role.Usher);
        branchManagerService.empRegister("Yossi", 233323, 10000, 1, roles);
        ArrayList<Role> roles2 = new ArrayList<>();
        roles2.add(Role.Cashier); roles2.add(Role.Usher);
        branchManagerService.empRegister("Moshe", 233324, 10000, 1, roles2);

    }

}
