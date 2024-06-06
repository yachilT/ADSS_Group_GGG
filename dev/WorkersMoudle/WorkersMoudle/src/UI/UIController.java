package UI;

import ServiceLayer.ServiceManager;

public class UIController {


    public static void main(String[] args) {

        new LoginWindow(ServiceManager.getInstance()).run();

    }

}
