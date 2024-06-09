package UI.HRManegerUI;

import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;

public class AddBranchesWindow extends Window {
    public AddBranchesWindow(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @Override
    public void run() {
        String branchName;
        String address;
        String name;
        int bankAccountNum;
        double salary;
        String password;

        System.out.println("Enter branch and branch manager details ");
        System.out.println("Branch name:");
        branchName = scanner.nextLine();
        System.out.println("Branch address");
        address = scanner.nextLine();
        System.out.println("Manager name");
        name = scanner.nextLine();
        System.out.println("Manager bank account");
        bankAccountNum = scanner.nextInt();
        System.out.println("Manager salary");
        salary = scanner.nextDouble();

        // Consume the newline character left by nextDouble()
        scanner.nextLine();

        // Read the password
        System.out.println("Manager password");
        password = scanner.nextLine();



        Response response = serviceManager.getHRManagerService().createBranch(branchName,address,name,bankAccountNum,salary);
        if(response.ErrorOccured()){
            System.out.println(response.GetErrorMessage());
            return;
        }
        System.out.println("Branch Manager Id: " + response.GetReturnValue());
        System.out.println("Branch added");

    }

    public Window getNextWindow() {
        return null;
    }
}