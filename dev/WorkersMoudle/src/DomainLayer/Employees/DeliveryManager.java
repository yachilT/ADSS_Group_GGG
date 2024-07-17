package DomainLayer.Employees;

import DataLayer.EmployeeDTO;

import java.util.ArrayList;
import java.util.LinkedList;

public class DeliveryManager extends Employee{

    public static final Integer MANGER_OF_DELIVERY = -2;
    public static final Integer DELIVERY_MANAGER_ID = 2;

    public DeliveryManager( String name, LinkedList<Role> roles, String password, int bankAccountNumber, double salary, int branchId) throws Exception {
        super(DELIVERY_MANAGER_ID, name, roles, bankAccountNumber, salary, branchId, MANGER_OF_DELIVERY );
        if(password == null || password.isEmpty())
            throw new Exception("Password cannot be empty");
        this.setPassword(password);
    }

    public DeliveryManager(EmployeeDTO employeeDTO){
        super(employeeDTO);
    }
}
