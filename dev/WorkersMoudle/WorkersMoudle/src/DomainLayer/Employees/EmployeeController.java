package DomainLayer.Employees;

import DomainLayer.Barnches.DayOfTheWeek;
import DomainLayer.Barnches.PartOfDay;
import DomainLayer.Pair;
import ServiceLayer.BranchManegerServices.BranchManagerToSend;
import ServiceLayer.HRManegerServices.HRManagerService;
import DomainLayer.Employees.Role;

import java.util.*;

public class EmployeeController {

    private static final int INITIAL_ID = 0;
    private Map<Integer, Employee> employees; // empId -> employee
    private Map<Integer, BranchManager> branchManagers; // branch id -> branch manager
    private HRManager hrManager;
    private List<Integer> employeesLoggedInIds; // list of employees ids that are logged in

    private int idCounter;
    EmployeeController(String hrManagerName, String hrManagerPassword, int hrManagerBankAccountNumber,
                       double hrManagerSalary, int hrManagerBranchId) {
        employees = new HashMap<>();
        branchManagers = new HashMap<>();
        employeesLoggedInIds = new ArrayList<>();
        idCounter = INITIAL_ID;

        hrManager = new HRManager(idCounter++, hrManagerName, hrManagerPassword, new LinkedList<Role>(),
                hrManagerBankAccountNumber, hrManagerSalary, hrManagerBranchId);
    }

    public void addEmployee(int id, String name, String password, List<Role> roles,
                            int bankAccountNumber, double salary, int branchId) throws Exception {
        if(employees.get(id) != null)
            throw new Exception("Employee already exists");
        if(salary < 0)
            throw new Exception("Salary must be positive");
        if(branchManagers.containsKey(branchId))
            throw new Exception("Branch doesn't exist");

        employees.put(id, new Employee(id, name,password, roles, bankAccountNumber, salary, branchId));
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

    public void removePreferences(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        try {
            for(Pair<DayOfTheWeek, PartOfDay> shiftPreference : shiftPreferences) {
                employees.get(id).removeShiftPreference(shiftPreference);
            }
        } catch (Exception e) {
            throw new Exception("Error in removing preferences");
        }
    }

    public void removeCantWork(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> shiftsCantWork) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        try {
            for(Pair<DayOfTheWeek, PartOfDay> shiftCantWork : shiftsCantWork) {
                employees.get(id).removeShiftCantWork(shiftCantWork);
            }
        } catch (Exception e) {
            throw new Exception("Error in removing cant work shifts");
        }
    }

    public void removeEmployee(Integer id) {
        if(null == employees.remove(id)){
            throw new IllegalArgumentException("Employee not found");
        }
    }

    public boolean isManager(Integer id) {
        return branchManagers.containsKey(id) || id ==INITIAL_ID;
    }
}
