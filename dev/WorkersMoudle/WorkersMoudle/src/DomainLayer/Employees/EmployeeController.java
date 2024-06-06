package DomainLayer.Employees;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Pair;

import java.util.*;

public class EmployeeController {

    private Map<Integer, Employee> employees;
    private Map<Integer, BranchManager> branchManagers;
    private HRManager hrManager;
    private List<Integer> employeesLoggedInIds;

    EmployeeController() {

    }

    public void addEmployee(int id, String name, String password, List<Role> roles,
                            int bankAccountNumber, double salary, int branchId) throws Exception {
        if(employees.get(id) != null)
            throw new Exception("Employee already exists");
        if(salary < 0)
            throw new Exception("Salary must be positive");
        if(branchManagers.containsKey(branchId))
            throw new Exception("Branch doesn't exist");

        employees.put(id, new Employee(id, name,password, roles, bankAccountNumber, salary, branchId, null));
        employeesLoggedInIds.add(id);// assuming the employee is logged in after register

    }

    public void login(int id, String password) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        if(!employees.get(id).checkPassword(password)) {
            throw new Exception("Wrong password");
        }
        if (employeesLoggedInIds.contains(id)){
            throw new Exception("Employee is already logged in");
        }
        employeesLoggedInIds.add(id);
    }

    public void logout(int id) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        if (!employeesLoggedInIds.contains(id)){
            throw new Exception("Employee is not logged in");
        }

        employeesLoggedInIds.remove(id);
    }

    public List<Integer> getBranchEmployeesIDs(int branchId) {
        List<Integer> branchEmployees = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (employee.getBranchId() == branchId) {
                branchEmployees.add(employee.getId());
            }
        }
        return branchEmployees;
    }

    public void enterTimeOut(int id, List<Pair<DayOfTheWeek, PartOfDay>> times) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        for (Pair<DayOfTheWeek, PartOfDay> time : times) {
            employees.get(id).addShiftCantWork(time);
        }
    }


    public void enterPreferences(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        try {
            for(Pair<DayOfTheWeek, PartOfDay> shiftPreference : shiftPreferences) {
                employees.get(id).addShiftPreference(shiftPreference);
            }
        } catch (Exception e) {
            throw new Exception("Error in setting preferences");
        }
    }
}
