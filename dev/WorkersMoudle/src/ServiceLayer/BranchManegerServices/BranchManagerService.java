package ServiceLayer.BranchManegerServices;

import DomainLayer.Branches.BranchController;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Branches.Shift;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import ServiceLayer.Driver;
import ServiceLayer.Response;
import jdk.jshell.spi.ExecutionControl;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class BranchManagerService {

    BranchController branchController;
    EmployeeController employeeController;

    public BranchManagerService(BranchController branchController,EmployeeController employeeController){
        this.branchController = branchController;
        this.employeeController = employeeController;
    }

    // Method to register an employee with given ID and employee data
    public Response empRegister(String name, int bankAccountNum, double salary, int branchId, List<Role> qualification, Float weight) {
        Integer id;
        try{
            id = employeeController.addEmployee(name, qualification, bankAccountNum, salary, branchId);
            if(weight!=null)
                employeeController.setDriver(id,weight);

        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response(id);
    }

    // Method to prepare shifts with a list of roles (add roles to the shift)
    public Response prepareShift(int branchId, DayOfTheWeek day, PartOfDay partOfDay, List<Role> roles) {
        this.branchController.setUpShift(branchId, day, partOfDay, roles);
        return new Response();
    }

    // Method to assign an employee to a shift
    public Response assignToShift(Integer id,Integer branchId, Role role, DayOfTheWeek day, PartOfDay partOfDay) {
        try {
            this.employeeController.isEmployeeExist(id, branchId);
            this.employeeController.canWork(id, day, partOfDay);
            this.branchController.addEmployeeToShift(id, branchId, role, day, partOfDay);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to unAssign an employee from a shift
    public Response unAssignFromShift(Integer id,Integer branchId, DayOfTheWeek day, PartOfDay partOfDay) {
        try {
            this.branchController.deleteEmployeeFromShift(branchId, id, day, partOfDay);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to display preferences of all employees (return Response with List<String>)
    public Response displayPreferences(Integer id) {
        return new Response(this.employeeController.displayPreferences(id));
    }

    public Response isWeekExists(Integer branchId,Integer week){
        try {
            this.branchController.isWeekExists(branchId, week);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addNeededRoles(Integer branchId,DayOfTheWeek day,PartOfDay part,List<Role> list){
        try {
            this.branchController.addNeededRoles(branchId, day, part, list);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response addNeededRoles(String address,DayOfTheWeek day,PartOfDay part,List<Role> list){
        try {
            int bId= branchController.findBranchByAdress(address);
            this.branchController.addNeededRoles(bId, day, part, list);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response getShift(Integer branchId, Integer week,DayOfTheWeek day, PartOfDay partOfDay) {
        String shift = null;
        try {
            shift = this.branchController.getStringShift(branchId, week, day, partOfDay);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        if(shift == null)
            return new Response("Shift not found");

        return new Response(shift, null);
    }

    public Response loadDatabase() {
        branchController.loadDatabase();
        try {
            employeeController.loadDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response();
    }

    public void deleteData() {
        branchController.deleteData();
        employeeController.deleteData();

    }


    public Response assignDriver(Predicate<Driver> driverPred, DayOfTheWeek day, PartOfDay part, String address) {
        throw new UnsupportedOperationException();
    } //TODO

    public Response isAssigned(String address, DayOfTheWeek day, PartOfDay part, Role role) {
        throw new UnsupportedOperationException();
        //TODO
    }
}


