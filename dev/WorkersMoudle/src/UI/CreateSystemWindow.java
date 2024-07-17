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
        serviceManager.getEmployeeService().loadDatabase();
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

        if(!serviceManager.getEmployeeService().isHRExist().ErrorOccured()) {
            Integer a = 0;
            return new Response(a);
        }

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

        Response response = serviceManager.getHRManagerService().hrRegister(name,bankAccountNum,salary, password);

        System.out.println("Enter Delivery Manager details");
        System.out.print("Name:");
        name = scanner.nextLine();
        System.out.print("Bank Account Number:");
        bankAccountNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Salary:");
        salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Password:");
        password = scanner.nextLine();

        Response response2 = serviceManager.getHRManagerService().deliveryRegister(name,bankAccountNum,salary, password);

        if(response.ErrorOccured() || response2.ErrorOccured())
            return new Response(response.GetErrorMessage() + response2.GetErrorMessage());

        return new Response();

    }

    public Window getNextWindow() {
        return new LoginWindow(serviceManager);
    }
}
