package UI;

import ServiceLayer.ServiceManager;
import UI.HRManegerUI.HRMainWindow;

public class CreateSystemWindow extends Window {

    public CreateSystemWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        String name;
        int bankAccountNum;
        double salary;
        String password;
        System.out.println("CreateSystem");
        System.out.println("Enter the HR manager's date:");
        System.out.print("Name:");
        name = scanner.nextLine();
        System.out.print("Bank Account Number:");
        bankAccountNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Salary:");
        salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Password:");
        password = scanner.nextLine();

        serviceManager.getHRManagerService().hrRegister(name,bankAccountNum,salary, password);
        System.out.println("HR manager created successfully");
        System.out.println("Forwarding to HR manager window");
        new HRMainWindow(serviceManager).run();

    }
}
