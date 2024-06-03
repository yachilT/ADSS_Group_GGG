package DomainLayer.Employees;

import java.util.List;

public class HRManager extends BranchManager {
    public HRManager(int id, String name, String password, List<Role> roles, int bankAccountNumber, double salary, int branchId) {
        super(id, name, password, roles, bankAccountNumber, salary, branchId);
    }
}
