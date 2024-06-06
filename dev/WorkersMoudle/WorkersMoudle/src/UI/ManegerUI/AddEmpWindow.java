package UI.ManegerUI;

import ServiceLayer.ServiceManager;
import UI.Window;

public class AddEmpWindow extends Window {
    public AddEmpWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        String name;
        String password;
        double salary;
        int bankAccountNum;

        System.out.println("Enter employee details");
        System.out.println("Employee name:");
        name = scanner.nextLine();
        System.out.println("Employee password");
        password = scanner.nextLine();
        System.out.println("Employee salary");
        salary = scanner.nextDouble();
        scanner.nextLine();


        System.out.println("Employee bank account");
        bankAccountNum = scanner.nextInt();

        serviceManager.getEmployeeService().addEmployee(name,bankAccountNum,salary,password);

        System.out.println("Employee added");
    }
}
