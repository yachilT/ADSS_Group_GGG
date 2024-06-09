package UI.EmployeeUI;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;

public class PreferencesWindow extends Window {
    private Integer id;
    public PreferencesWindow(ServiceManager serviceManager, Integer id) {
        super(serviceManager);
        this.id = id;
    }

    @Override
    public void run() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Choose Preferable Shifts Window:");
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

        System.out.println("Going Back...");
    }

    private void presentShift(DayOfTheWeek day){
        System.out.println("Morning or Evening? (M/E)");
        String partOfDay = scanner.nextLine();
        if(partOfDay.equals("M")) {
            Response response = serviceManager.getEmployeeService().enterPreferences(id, day, PartOfDay.Morning);
            if(response.ErrorOccured())
                System.out.println(response.GetErrorMessage());
            else
                System.out.println("Preference entered successfully");
        }
        else if(partOfDay.equals("E")) {
            Response response = serviceManager.getEmployeeService().enterPreferences(id, day, PartOfDay.Evening);
            if(response.ErrorOccured())
                System.out.println(response.GetErrorMessage());
            else
                System.out.println("Preference entered successfully");
        }
        else
            System.out.println("Invalid input");
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
