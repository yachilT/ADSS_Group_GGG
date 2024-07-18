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
    public Response createBranch(String branchName, String address, String name, int bankAccountNum, double salary) {
        Integer id; //TODO

        try {
            id = employeeController.addBranchManager(name,bankAccountNum,salary,branchController.addBranch(branchName, address));

        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response(id);
    }

    // Method to register an HR manager with given details
    public Response hrRegister(String name, int bankAccountNum, double salary, String password) {
        try {
           return new Response(employeeController.setHrManager(name, password,bankAccountNum, salary, 0));
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response deliveryRegister(String name, int bankAccountNum, double salary, String password) {
        Integer id;
        try {
            id = employeeController.setDeliveryManager(name,password , bankAccountNum, salary, 0);
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response(id);
    }




}
