package ServiceLayer.HRManegerServices;

import WorkersMoudle.WorkersMoudle.src.ServiceLayer.BranchManegerServices.BranchManagerToSend;

import java.util.Date;
import java.util.List;

public class HRManagerToSend extends BranchManagerToSend {

    public HRManagerToSend(int empId, String name, int bankAccountNum, double salary, Date dateJoined, int branchId, List<String> qualification) {
        super(empId, name, bankAccountNum, salary, dateJoined, branchId, qualification);
    }

    // Additional methods or fields specific to HRManagerToSend can be added here
}