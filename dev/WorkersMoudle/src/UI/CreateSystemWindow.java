package UI;

import DomainLayer.Employees.Employee;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.HRManegerUI.HRMainWindow;

public class CreateSystemWindow extends Window {

    public CreateSystemWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {

        Response response =null;
        boolean flag =true;

        while(flag){
            response = getInputs();
            if(response.ErrorOccured())
                System.out.println(response.GetErrorMessage());
            else
                flag = false;
        }


        System.out.println("Employee ID:" + response.GetReturnValue());
        System.out.println("HR manager created successfully");
        System.out.println("Forwarding Login window");

    }

    private Response getInputs() {
        String name;
        int bankAccountNum;
        double salary;
        String password;
        System.out.println("CreateSystem");
        System.out.println("Enter HR Manager details");
        System.out.print("Name:");
        name = scanner.nextLine();
        System.out.print("Bank Account Number:");
        bankAccountNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Salary:");
        salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Password:");
        password = scanner.nextLine();

        return serviceManager.getHRManagerService().hrRegister(name,bankAccountNum,salary, password);

    }

    public Window getNextWindow() {
        return new LoginWindow(serviceManager);
    }
}
