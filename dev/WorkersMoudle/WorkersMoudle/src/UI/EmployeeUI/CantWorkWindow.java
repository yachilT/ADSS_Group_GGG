package UI.EmployeeUI;

import ServiceLayer.ServiceManager;
import UI.Window;

public class CantWorkWindow extends Window {
    public CantWorkWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {

    }

    public Window getNextWindow() {
        return null;
    }
}
