package ServiceLayer.HRManegerServices;

import DomainLayer.Branches.BranchController;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.HRManager;

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
    public void createBranchcreateBranch(String branchName, String address, String name, String password, int bankAccountNum, double salary) {
        try {
            employeeController.addBranchManager(name, password,bankAccountNum,salary,branchController.addBranch(branchName, address));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Method to register an HR manager with given details
    public void hrRegister(int empId, String name, int bankAccountNum, double salary, String password) {
        employeeController.setHrManager(name, password,bankAccountNum, salary, 0);
    }
}
