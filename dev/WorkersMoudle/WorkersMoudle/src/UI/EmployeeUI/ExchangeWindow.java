package UI.EmployeeUI;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;

public class ExchangeWindow extends Window {
    private Integer id;

    private Integer branchId;
    public ExchangeWindow(ServiceManager serviceManager, Integer id, Integer branchId) {
        super(serviceManager);
        this.id = id;
        this.branchId = branchId;
    }

    @Override
    public void run() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Choose Shifts Window:");
            System.out.println("Employee's id to exchange with: (enter 0 to exit)");
            int otherId = Integer.parseInt(scanner.nextLine());
            if(otherId == 0){
                exit = true;
                continue;
            }
            Response response = serviceManager.getEmployeeService().isEmployeeExist(otherId, branchId);
            if(response.ErrorOccured()) {
                System.out.println(response.GetErrorMessage());
                continue;
            }

            Role role = null;
            while (role == null) {
                System.out.println("Role to fulfill: ");
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
            }

            chooseOptions();

            switch (scanner.nextLine()) {
                case "1" -> {
                    presentShift(DayOfTheWeek.Sunday, otherId, role);
                }
                case "2" -> {
                    presentShift(DayOfTheWeek.Monday, otherId, role);
                }
                case "3" -> {
                    presentShift(DayOfTheWeek.Tuesday, otherId, role);
                }
                case "4" -> {
                    presentShift(DayOfTheWeek.Wednesday, otherId, role);
                }
                case "5" -> {
                    presentShift(DayOfTheWeek.Thursday, otherId, role);
                }
                case "6" -> {
                    presentShift(DayOfTheWeek.Friday, otherId, role);
                }
                case "7" -> {
                    exit = true;
                }
            }
        }

        System.out.println("Going Back...");
    }

    private void presentShift(DayOfTheWeek day, int otherId, Role role){
        System.out.println("Morning or Evening? (M/E)");
        String partOfDay = scanner.nextLine();
        if(partOfDay.equals("M")) {
            Response response = serviceManager.getEmployeeService().exchangeShift(branchId, id,otherId, day, PartOfDay.Morning, role);
            if(response.ErrorOccured())
                System.out.println(response.GetErrorMessage());
            else
                System.out.println("Exchanged successfully");
        }
        else if(partOfDay.equals("E")) {
            Response response = serviceManager.getEmployeeService().exchangeShift(branchId, id,otherId, day, PartOfDay.Evening, role);
            if(response.ErrorOccured())
                System.out.println(response.GetErrorMessage());
            else
                System.out.println("Exchanged successfully");
        }
        else
            System.out.println("Invalid input");
    }

    private void chooseOptions() {
        System.out.println("Choose the day of the shift:");
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
