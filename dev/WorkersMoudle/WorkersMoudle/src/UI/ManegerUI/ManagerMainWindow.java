package UI.ManegerUI;

import ServiceLayer.ServiceManager;
import UI.ManegerUI.NextShift.EditNextShiftWindow;
import UI.ManegerUI.PastShifts.ViewPastShiftsWindow;
import UI.Window;

public class ManagerMainWindow extends Window {
    private int id;
    private int branchId;
    private Window nextWindow;
    public ManagerMainWindow(ServiceManager serviceManager, int id, Integer branchId){
        super(serviceManager);
        this.id = id;
        this.branchId = branchId;
    }

    @Override
    public void run() {
        boolean exit = false;
        while (!exit) {
            chooseOptions();
            switch (scanner.nextLine()) {
                case "1" -> new AddEmpWindow(serviceManager, branchId).run();
                case "2" -> new ViewPastShiftsWindow(serviceManager, branchId).run();
                case "3" -> new EditNextShiftWindow(serviceManager, branchId).run();
                case "4" -> exit = true;
            }
        }
        System.out.println("Goodbye!");
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Employee");
        System.out.println("2. View Past&Current Weekly Shifts");
        System.out.println("3. Edit Next Week Shifts");
        System.out.println("4. Exit");

    }

    public Window getNextWindow() {
        return nextWindow;
    }
}
