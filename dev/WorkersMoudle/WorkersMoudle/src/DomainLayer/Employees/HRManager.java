package DomainLayer.Employees;

import DataLayer.EmployeeDTO;

import java.util.List;

public class HRManager extends BranchManager {
    public HRManager(int id, String name, String password, List<Role> roles, int bankAccountNumber, double salary, int branchId) throws Exception {
        super(id, name, roles, bankAccountNumber, salary, branchId);
        if(password == null || password.isEmpty())
            throw new Exception("Password cannot be empty");
        this.setPassword(password);
    }

    public HRManager(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }
}
