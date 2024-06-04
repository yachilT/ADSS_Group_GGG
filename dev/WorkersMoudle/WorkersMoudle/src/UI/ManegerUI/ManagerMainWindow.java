package UI.ManegerUI;

import ServiceLayer.ServiceManager;
import UI.ManegerUI.NextShift.EditNextShiftWindow;
import UI.ManegerUI.PastShifts.ViewPastShiftsWindow;
import UI.Window;

public class ManagerMainWindow extends Window {
    private int id;
    public ManagerMainWindow(ServiceManager serviceManager, int id) {
        super(serviceManager);
        this.id = id;
    }

    @Override
    public void run() {
        boolean exit = false;
        while (exit) {
            chooseOptions();
            switch (scanner.nextLine()) {
                case "1" -> new AddEmpWindow(serviceManager).run();
                case "2" -> new ViewPastShiftsWindow(serviceManager).run();
                case "3" -> new EditNextShiftWindow(serviceManager).run();
                case "4" -> exit = true;
            }
        }
        System.out.println("Goodbye!");
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Employee");
        System.out.println("2. View Past Shifts");
        System.out.println("3. Edit Next Shift");
        System.out.println("4. Exit");

    }
}
