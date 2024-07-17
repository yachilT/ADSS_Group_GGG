package UI.ManegerUI;

import DomainLayer.Employees.Role;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddEmpWindow extends Window {

    private Integer branchId;
    public AddEmpWindow(ServiceManager serviceManager, Integer branchId) {
        super(serviceManager);
        this.branchId = branchId;
    }

    @Override
    public void run() {
        String name;
        double salary;
        int bankAccountNum;

        System.out.println("Enter employee details");
        System.out.println("Employee name:");
        name = scanner.nextLine();
        System.out.println("Employee salary");
        salary = scanner.nextDouble();
        System.out.println("Employee bank account");
        bankAccountNum = scanner.nextInt();

        boolean flag = true;
        List<Role> list = new ArrayList<>();
        int counter = 0;
        Integer weight = null;

        while(flag){
            System.out.println("Choose a role to add:");
            System.out.println("1. Cashier");
            System.out.println("2. Usher");
            System.out.println("3. Shift Manager");
            System.out.println("4. StoreKeeper");
            System.out.println("5. Driver");
            System.out.println("6. Butcher");
            System.out.println("7. CheeseMan");
            System.out.println("8. No more roles");

            String choice = "";
            do {
                choice = scanner.nextLine();
            }while(choice.equals(""));
            switch (choice) {
                case "1" -> {
                    list.add(Role.Cashier);
                    counter++;
                }
                case "2" -> {
                    list.add(Role.Usher);
                    counter++;
                }
                case "3" -> {
                    list.add(Role.ShiftManager);
                    counter++;
                }
                case "4" -> {
                    list.add(Role.StoreKeeper);
                    counter++;
                }
                case "5" -> {
                    list.add(Role.Driver);
                    counter++;
                    System.out.println("Enter the weight for the driver:");
                    while (weight == null) {
                        try {
                            weight = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for the weight.");
                        }
                    }
                }
                case "6" -> {
                    list.add(Role.Butcher);
                    counter++;
                }
                case "7" -> {
                    list.add(Role.CheeseMan);
                    counter++;
                }
                case "8" -> {flag = false;}
            }

            if(counter == 0){
                System.out.println("You must choose at least one role");
                counter = 0;
                flag = true;
            }
        }

        Response response = serviceManager.getBranchManagerService().empRegister(name,bankAccountNum,salary,branchId,list, weight);
        if(response.ErrorOccured()){
            System.out.println(response.GetErrorMessage());
            return;
        }
        System.out.println("Employee added");
        System.out.println("Employee ID:" + response.GetReturnValue());
    }

    public Window getNextWindow() {
        return null;
    }
}
