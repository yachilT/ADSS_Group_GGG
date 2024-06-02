package ServiceLayer;

import ServiceLayer.EmployeeServices.EmployeeService;
import ServiceLayer.HRManegerServices.HRManagerService;
import WorkersMoudle.WorkersMoudle.src.ServiceLayer.BranchManegerServices.BranchManagerService;

public class ServiceManager {
    private EmployeeService employeeService;
    private HRManagerService hrManagerService;
    private BranchManagerService branchManagerService;

    public ServiceManager() {
        // Initialize services here
        this.employeeService = new EmployeeService();
        this.hrManagerService = new HRManagerService();
        this.branchManagerService = new BranchManagerService();
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

