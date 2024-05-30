
package WorkersMoudle.WorkersMoudle.src.ServiceLayer.EmployeeServices;

import java.util.Date;
import java.util.List;

public class EmployeeToSend {
    public final int empId;  // Employee ID
    public final String name;  // Name of the employee
    public final int bankAccountNum;  // Bank account number
    public final double salary;  // Salary of the employee
    public final Date dateJoined;  // Date when the employee joined
    public final int branchId;  // Branch ID where the employee works
    public final List<String> qualification;  // List of qualifications of the employee

    // Complete constructor
    public EmployeeToSend(int empId, String name, int bankAccountNum, double salary, Date dateJoined, int branchId, List<String> qualification) {
        this.empId = empId;
        this.name = name;
        this.bankAccountNum = bankAccountNum;
        this.salary = salary;
        this.dateJoined = dateJoined;
        this.branchId = branchId;
        this.qualification = qualification;
    }

}
