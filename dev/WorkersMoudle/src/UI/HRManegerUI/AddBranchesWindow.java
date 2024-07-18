package UI.HRManegerUI;

import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;
import presentation_layer.Controller;
import service_layer.AreaToSend;

import java.util.List;

public class AddBranchesWindow extends Window {
    private Controller deliveryController;
    public AddBranchesWindow(ServiceManager serviceManager, Controller deliveryController) {
        super(serviceManager);
        this.deliveryController = deliveryController;

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
        System.out.println("Choose Area (enter index)");
        List<AreaToSend> areas =deliveryController.getAreaService().getAreas();

        for (int i = 0; i < areas.size(); i++) {
            System.out.println(i + ". " + areas.get(i).getAreaName());
        }
        int areaIndex = Integer.parseInt(scanner.nextLine());




        System.out.println("Enter branch manager details");
        System.out.println("Manager name");

        name = scanner.nextLine();

        System.out.println("Manager bank account");
        bankAccountNum = scanner.nextInt();
        System.out.println("Manager salary");
        salary = scanner.nextDouble();

        System.out.println("Manager phone");
        String phone = scanner.nextLine();

        // Consume the newline character left by nextDouble()
        scanner.nextLine();



        Response response = serviceManager.getHRManagerService().createBranch(branchName,address,name,bankAccountNum,salary);
        if(response.ErrorOccured()){
            System.out.println(response.GetErrorMessage());
            return;
        }

        deliveryController.getAreaService().addSite(address, name, phone, areas.get(areaIndex).getAreaName());

        System.out.println("Branch Manager Id: " + response.GetReturnValue());
        System.out.println("Branch added");

    }

    public Window getNextWindow() {
        return null;
    }
}
