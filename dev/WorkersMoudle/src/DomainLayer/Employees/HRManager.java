package DomainLayer.Employees;

import DataLayer.EmployeeDTO;

import java.util.List;

public class HRManager extends BranchManager {
    public static final Integer MANGER_OF_HR = -1;
    public static final Integer HR_MANAGER_ID = 1;
    public HRManager(String name, String password, List<Role> roles, int bankAccountNumber, double salary, int branchId) throws Exception {
        super(HR_MANAGER_ID, name, roles, bankAccountNumber, salary, branchId,MANGER_OF_HR);
        if(password == null || password.isEmpty())
            throw new Exception("Password cannot be empty");
        this.setPassword(password);
    }

    public HRManager(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }
}
