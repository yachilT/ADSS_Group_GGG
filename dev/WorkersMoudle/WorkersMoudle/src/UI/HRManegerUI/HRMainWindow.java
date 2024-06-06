package UI.HRManegerUI;

import ServiceLayer.ServiceManager;
import UI.ManegerUI.AddEmpWindow;
import UI.ManegerUI.PastShifts.ViewPastShiftsWindow;
import UI.Window;

public class HRMainWindow extends Window {
    public HRMainWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        boolean exit = false;
        while (exit) {
            chooseOptions();
            switch (scanner.nextLine()) {
                case "1" -> new AddBranchesWindow(serviceManager).run();
                case "2" -> exit = true;
            }
        }
        System.out.println("Goodbye!");
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Branches");
        System.out.println("2. Exit");

    }
}
