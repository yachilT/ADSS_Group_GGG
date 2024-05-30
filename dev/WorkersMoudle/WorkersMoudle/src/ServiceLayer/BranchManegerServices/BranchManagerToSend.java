package WorkersMoudle.WorkersMoudle.src.ServiceLayer.BranchManegerServices;

import WorkersMoudle.WorkersMoudle.src.ServiceLayer.EmployeeServices.EmployeeToSend;

import java.util.Date;
import java.util.List;

public class BranchManagerToSend extends EmployeeToSend {

    // Constructor
    public BranchManagerToSend(int empId, String name, int bankAccountNum, double salary, Date dateJoined, int branchId, List<String> qualification) {
        super(empId, name, bankAccountNum, salary, dateJoined, branchId, qualification);
    }

    // Additional methods or fields specific to BranchManagerToSend can be added here
}