package UI;

import ServiceLayer.ServiceManager;

public class UIController {


    public static void main(String[] args) {
        new CreateSystemWindow(ServiceManager.getInstance()).run();
        Window nextWindow = null;
        boolean exit = false;
        do{
            LoginWindow login = new LoginWindow(ServiceManager.getInstance());
            login.run();
            if(login.isExit())
                exit = true;
            else{
                nextWindow = login.getNextWindow();
                nextWindow.run();
            }
        }while(!exit);

    }

}
