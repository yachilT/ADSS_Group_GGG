package DomainLayer.Employees;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Pair;
import ServiceLayer.Response;

import java.util.*;

public class EmployeeController {

    private static final int INITIAL_ID = 0;
    private Map<Integer, Employee> employees; // empId -> employee
    private Map<Integer, BranchManager> branchManagers; // branch id -> branch manager
    private HRManager hrManager;
    private List<Integer> employeesLoggedInIds; // list of employees ids that are logged in

    private Integer idCounter;
    public EmployeeController() {
        employees = new HashMap<>();
        branchManagers = new HashMap<>();
        employeesLoggedInIds = new ArrayList<>();
        idCounter = INITIAL_ID;
    }

    public Integer setHrManager(String hrManagerName, String hrManagerPassword, int hrManagerBankAccountNumber,
    double hrManagerSalary, int hrManagerBranchId) throws Exception{
        hrManager = new HRManager(idCounter++, hrManagerName, hrManagerPassword, new LinkedList<Role>(),
                hrManagerBankAccountNumber, hrManagerSalary, hrManagerBranchId);
        employees.put(hrManager.getId(), hrManager);
        return hrManager.getId();
    }

    public Integer addEmployee(String name, List<Role> roles,
                            int bankAccountNumber, double salary, int branchId) throws Exception {

        if(!branchManagers.containsKey(branchId))
            throw new Exception("Branch doesn't exist");

        Employee employee = new Employee(idCounter, name, roles, bankAccountNumber, salary, branchId);
        employees.put(idCounter++, employee);
        return employee.getId();
    }

    public int addBranchManager(String name,int bankAccountNumber, double salary, int branchId) throws Exception {

        if(branchManagers.containsKey(branchId))
            throw new Exception("Branch already has a manager");

        BranchManager branchManager = new BranchManager(idCounter++, name, new ArrayList<Role>(), bankAccountNumber, salary, branchId);
        branchManagers.put(branchManager.getId(), branchManager);
        employees.put(branchManager.getId(), branchManager);
        return branchManager.getId();
    }

    public void login(int id, String password) throws Exception {
        if(password == null || password.isEmpty())
            throw new Exception("Password is empty");
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

    public void logout(Integer id) throws Exception {
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

    public void enterTimeOut(int id, DayOfTheWeek day, PartOfDay part) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        employees.get(id).addShiftCantWork(new Pair<>(day, part));
    }

    public Integer getBranchId(Integer id) throws Exception{
        if(employees.get(id) == null){
            throw new IllegalArgumentException("Employee not found");
        }
        return employees.get(id).getBranchId();
    }


    public void enterPreferences(Integer id, DayOfTheWeek day, PartOfDay part) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        try {
            employees.get(id).addShiftPreference(new Pair<>(day, part));
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

    public String printEmployeesPref(Integer branchId) throws Exception{
        if(branchManagers.get(branchId) == null){
            throw new Exception("Branch not found");
        }
        String output = "";
        for (Employee employee : employees.values()) {
            if (employee.getBranchId() == branchId && !(branchManagers.get(branchId).getId() != employee.getId())){
                output += employee.toString() + "\n";
            }
        }
        if(output.equals(""))
            throw new Exception("No employees in branch");

        return output;
    }

    public void removeEmployee(Integer id) {
        if(null == employees.remove(id)){
            throw new IllegalArgumentException("Employee not found");
        }
    }

    public void signUp(Integer id, String password) throws Exception {
        if(employees.get(id) == null){
            throw new IllegalArgumentException("Employee not found");
        }
        employees.get(id).setPassword(password);
    }
    public boolean isManager(Integer id) {
        return branchManagers.containsKey(id) || id == hrManager.getId();
    }

    public boolean isHR(Integer id) {
        return id == hrManager.getId();
    }

    public boolean isEmployeeNew(Integer id) throws Exception {
        if(employees.get(id) == null){
            throw new IllegalArgumentException("Employee not found");
        }
        return employees.get(id).isNew();
    }

    public List<String> displayPreferences(Integer id){
        if(employees.get(id) == null){
            throw new IllegalArgumentException("Employee not found");
        }
        List<Pair<DayOfTheWeek,PartOfDay>> list = employees.get(id).getShiftPreferences();
        List<String> res = new ArrayList<>();
        for(Pair<DayOfTheWeek,PartOfDay> p : list){
            res.add(p.getKey().toString() + " " + p.getValue().toString());
        }
        return res;
    }

    public boolean isBranchEMP(Integer empId, Integer branchId){
        return employees.get(empId).getBranchId() == branchId;
    }

    // only for tests
    public HRManager getHrManager() {
        return hrManager;
    }

    public Collection<Employee> getEmployees() {
        return employees.values();
    }

}
