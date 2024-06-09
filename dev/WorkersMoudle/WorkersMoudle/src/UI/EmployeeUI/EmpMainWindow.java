package UI.EmployeeUI;

import ServiceLayer.ServiceManager;
import UI.Window;

public class EmpMainWindow extends Window {
    private int id;
    public EmpMainWindow(ServiceManager serviceManager, int id) {
        super(serviceManager);
        this.id = id;
    }

    @Override
    public void run() {

        boolean exit = false;
        while (!exit) {
            chooseOptions();

            switch (scanner.nextLine()) {
                case "1" -> {
                    new CantWorkWindow(serviceManager,id).run();
                }
                case "2" -> {
                    new PreferencesWindow(serviceManager,id).run();
                }
                case "3" -> {
                    serviceManager.getEmployeeService().logout(id);
                    exit = true;
                }
            }
        }
        System.out.println("Goodbye!");
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Enter the shifts when you arent available to work");
        System.out.println("2. Enter your preferable shifts");
        System.out.println("3. Exit");

    }

    public Window getNextWindow() {
        return null;
    }
}
