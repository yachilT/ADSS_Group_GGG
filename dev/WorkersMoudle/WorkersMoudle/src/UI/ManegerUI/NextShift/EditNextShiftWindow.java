package UI.ManegerUI.NextShift;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;

import java.util.ArrayList;
import java.util.List;

public class EditNextShiftWindow extends Window {

    private Integer branchId;
    public EditNextShiftWindow(ServiceManager serviceManager,Integer branchId){
        super(serviceManager);
        this.branchId = branchId;
    }

    @Override
    public void run() {
        boolean exit = false;

        while (!exit) {
            chooseOptions();

            switch (scanner.nextLine()) {
                case "1" -> {
                    presentShift(DayOfTheWeek.Sunday);
                }
                case "2" -> {
                    presentShift(DayOfTheWeek.Monday);
                }
                case "3" -> {
                    presentShift(DayOfTheWeek.Tuesday);
                }
                case "4" -> {
                    presentShift(DayOfTheWeek.Wednesday);
                }
                case "5" -> {
                    presentShift(DayOfTheWeek.Thursday);
                }
                case "6" -> {
                    presentShift(DayOfTheWeek.Friday);
                }
                case "7" -> {
                    exit = true;
                }
            }
        }

        System.out.println("Finished editing shifts");
    }

    private void presentShift(DayOfTheWeek day){
        System.out.println("Morning or Evening? (M/E)");
        String partOfDay = scanner.nextLine();
        if(partOfDay.equals("M")) {
            PrintShift(day, PartOfDay.Morning);
            editShift(day, PartOfDay.Morning);
        }
        else if(partOfDay.equals("E")) {
            PrintShift(day, PartOfDay.Evening);
            editShift(day, PartOfDay.Evening);
        }
        else
            System.out.println("Invalid input");
    }

    private void editShift(DayOfTheWeek day, PartOfDay part) {
        boolean exitShift = false;
        while (!exitShift) {
            chooseEditOptions();

            switch (scanner.nextLine()) {
                case "1" -> {
                    Response response = serviceManager.getEmployeeService().printEmployeesPref(branchId);
                    if(response.ErrorOccured()){
                        System.out.println(response.GetErrorMessage());
                        continue;
                    }
                    System.out.println();
                    System.out.println((String) response.GetReturnValue());
                    System.out.println();
                    System.out.println("Enter employee details");
                    System.out.println("Employee Id:");
                    String idStr = scanner.nextLine();
                    Integer id = Integer.parseInt(idStr);
                    Role role = null;
                    while (role == null) {
                        System.out.println("Enter employee role:");
                        System.out.println("1. Cashier");
                        System.out.println("2. Usher");
                        System.out.println("3. Shift Manager");
                        System.out.println("4. StoreKeeper");
                        System.out.println("5. HR Manager");
                        System.out.println("6. Butcher");
                        System.out.println("7. CheeseMan");

                        switch (scanner.nextLine()) {
                            case "1" -> role = Role.Cashier;
                            case "2" -> role = Role.Usher;
                            case "3" -> role = Role.ShiftManager;
                            case "4" -> role = Role.StoreKeeper;
                            case "6" -> role = Role.Butcher;
                            case "7" -> role = Role.CheeseMan;
                        }

                        if (role == null) {
                            System.out.println("You must choose a valid role");
                        }

                        response = serviceManager.getEmployeeService().checkEmployeeRole(id, role);
                    }
                    response = serviceManager.getBranchManagerService().assignToShift(id,branchId, role, day, part);
                    if (response.ErrorOccured()) {
                        System.out.println(response.GetErrorMessage());
                    } else {
                        System.out.println("Employee added successfully");
                    }
                }
                case "2" -> {
                    System.out.println("Enter Employee Id:");
                    String idStr = scanner.nextLine();
                    Integer id = Integer.parseInt(idStr);
                    Response response = serviceManager.getBranchManagerService().unAssignFromShift(id, branchId, day, part);
                    if (response.ErrorOccured()) {
                        System.out.println(response.GetErrorMessage());
                    } else {
                        System.out.println("Employee removed from shift successfully");
                    }
                }
                case "3" -> {
                    boolean flag = true;
                    List<Role> list = new ArrayList<>();

                    while (flag){
                        System.out.println("Choose a role to add:");
                        System.out.println("1. Cashier");
                        System.out.println("2. Usher");
                        System.out.println("3. Shift Manager");
                        System.out.println("4. StoreKeeper");
                        System.out.println("5. HR Manager");
                        System.out.println("6. Butcher");
                        System.out.println("7. CheeseMan");
                        System.out.println("8. No more roles");


                        switch (scanner.nextLine()) {
                            case "1" -> list.add(Role.Cashier);
                            case "2" -> list.add(Role.Usher);
                            case "3" -> list.add(Role.ShiftManager);
                            case "4" -> list.add(Role.StoreKeeper);
                            case "6" -> list.add(Role.Butcher);
                            case "7" -> list.add(Role.CheeseMan);
                            case "8" -> flag = false;
                        }
                    }

                    if(list.size() == 0){
                        System.out.println("No roles were added");
                        continue;
                    }

                    Response response = serviceManager.getBranchManagerService().addNeededRoles(branchId, day, part, list);
                    if (response.ErrorOccured())
                        System.out.println(response.GetErrorMessage());
                    else
                        System.out.println("Roles added successfully");
                }
                case "4" -> exitShift = true;
            }
        }
    }

    private void chooseEditOptions(){
        System.out.println("Choose an option:");
        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. Add Needed Role");
        System.out.println("4. Exit");
    }

    private void PrintShift(DayOfTheWeek day, PartOfDay partOfDay){
        Response response = serviceManager.getBranchManagerService().getShift(branchId,1, day, partOfDay);
        System.out.println();
        if(response.ErrorOccured()){
            System.out.println(response.GetErrorMessage());
        }else{
            System.out.println((String) response.GetReturnValue());
        }
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Sunday");
        System.out.println("2. Monday");
        System.out.println("3. Tuesday");
        System.out.println("4. Wednesday");
        System.out.println("5. Thursday");
        System.out.println("6. Friday");
        System.out.println("7. Exit");

    }

    public Window getNextWindow() {
        return null;
    }


}
