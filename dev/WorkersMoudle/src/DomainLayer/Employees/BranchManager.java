package DomainLayer.Employees;

import DataLayer.EmployeeDTO;

import java.util.List;

public class BranchManager extends Employee {
    public BranchManager(Integer id ,String name, List<Role> roles, int bankAccountNumber, double salary, int branchId, Integer manager) throws Exception {
        super(id, name, roles, bankAccountNumber, salary, branchId,manager);
    }

    public BranchManager(EmployeeDTO employee) {
        super(employee);
    }



}
