package ServiceLayer;

import DomainLayer.Branches.BranchController;
import DomainLayer.Employees.BranchManager;
import DomainLayer.Employees.EmployeeController;
import ServiceLayer.EmployeeServices.EmployeeService;
import ServiceLayer.HRManegerServices.HRManagerService;
import ServiceLayer.BranchManegerServices.BranchManagerService;

public class ServiceManager {

    private static ServiceManager instance = null;

    private EmployeeService employeeService;
    private HRManagerService hrManagerService;
    private BranchManagerService branchManagerService;

    private ServiceManager() {
        // Initialize services here
        EmployeeController employeeController = new EmployeeController();
        BranchController branchController = new BranchController(employeeController);
        this.employeeService = new EmployeeService(employeeController,branchController);
        this.hrManagerService = new HRManagerService(employeeController,branchController);
        this.branchManagerService = new BranchManagerService(branchController,employeeController);
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }

    public HRManagerService getHRManagerService() {
        return this.hrManagerService;
    }

    public BranchManagerService getBranchManagerService() {
        return this.branchManagerService;
    }

    public void loadDatabase() {
        branchManagerService.loadDatabase();
    }

    public void deleteDatabase() {branchManagerService.deleteDatabase();}
}