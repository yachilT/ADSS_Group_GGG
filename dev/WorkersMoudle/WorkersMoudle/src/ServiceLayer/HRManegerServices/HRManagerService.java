package ServiceLayer.HRManegerServices;

import DomainLayer.Branches.BranchController;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.HRManager;
import ServiceLayer.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HRManagerService {

    EmployeeController employeeController;
    BranchController branchController;

    public HRManagerService(EmployeeController employeeController, BranchController branchController){
        this.employeeController = employeeController;
        this.branchController = branchController;
    }
    public Response createBranchcreateBranch(String branchName, String address, String name, String password, int bankAccountNum, double salary) {
        try {
            employeeController.addBranchManager(name, password,bankAccountNum,salary,branchController.addBranch(branchName, address));
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to register an HR manager with given details
    public Response hrRegister(String name, int bankAccountNum, double salary, String password) {
        employeeController.setHrManager(name, password,bankAccountNum, salary, 0);
        return new Response();
    }
}
