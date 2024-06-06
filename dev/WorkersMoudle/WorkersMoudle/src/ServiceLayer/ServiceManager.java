package ServiceLayer;

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
        this.employeeService = new EmployeeService();
        this.hrManagerService = new HRManagerService();
        this.branchManagerService = new BranchManagerService();
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
}