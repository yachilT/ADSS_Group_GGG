package UI.EmployeeUI;

import ServiceLayer.ServiceManager;
import UI.Window;

public class EmpMainWindow extends Window {
    public EmpMainWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {

        boolean exit = false;
        while (!exit) {
            chooseOptions();

            switch (scanner.nextLine()) {
                case "1" -> {
                    new CantWorkWindow(serviceManager).run();
                }
                case "2" -> {
                    new PreferencesWindow(serviceManager).run();
                }
                case "3" -> {

                    exit = true;
                }
            }
        }
        System.out.println("Goodbye!");
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Cant Work");
        System.out.println("2. Preferences");
        System.out.println("3. Exit");

    }
}
