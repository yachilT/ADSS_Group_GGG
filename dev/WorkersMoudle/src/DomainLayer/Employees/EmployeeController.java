package DomainLayer.Employees;

import DataLayer.EmployeeDTO;
import DataLayer.EmployeeDataManager;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Pair;
import ServiceLayer.Driver;
import ServiceLayer.License;
import ServiceLayer.Response;

import java.util.*;
import java.util.function.Predicate;

public class EmployeeController {



    private static final int INITIAL_ID = 3;
    private Map<Integer, Employee> employees; // empId -> employee
    private Map<Integer, BranchManager> branchManagers; // branch id -> branch manager
    private HRManager hrManager;
    private List<Integer> employeesLoggedInIds; // list of employees ids that are logged in
    private DeliveryManager deliveryManager;

    private Integer idCounter;
    public EmployeeController() {
        employees = new HashMap<>();
        branchManagers = new HashMap<>();
        employeesLoggedInIds = new ArrayList<>();
        idCounter = INITIAL_ID;
    }

    public Integer setHrManager(String hrManagerName, String hrManagerPassword, int hrManagerBankAccountNumber,

    double hrManagerSalary, int hrManagerBranchId) throws Exception{
        hrManager = new HRManager(hrManagerName, hrManagerPassword, new LinkedList<Role>(),

                hrManagerBankAccountNumber, hrManagerSalary, hrManagerBranchId);
        employees.put(hrManager.getId(), hrManager);
        return hrManager.getId();
    }

    public Integer setDeliveryManager(String deliveryManagerName, String deliveryManagerPassword, int deliveryManagerBankAccountNumber,

    double deliveryManagerSalary, int deliveryManagerBranchId) throws Exception{
        deliveryManager = new DeliveryManager(deliveryManagerName, new LinkedList<Role>(), deliveryManagerPassword,
                deliveryManagerBankAccountNumber, deliveryManagerSalary, deliveryManagerBranchId);
        employees.put(deliveryManager.getId(), deliveryManager);
        return deliveryManager.getId();
    }

    public boolean isDeliveryManager(Integer id) {
        return id == deliveryManager.getId();
    }

    public Integer addEmployee(String name, List<Role> roles,
                               int bankAccountNumber, double salary, int branchId) throws Exception {

        if(!branchManagers.containsKey(branchId))
            throw new Exception("Branch doesn't exist");

        Employee employee = new Employee(idCounter, name, roles, bankAccountNumber, salary, branchId,0);
        employees.put(idCounter++, employee);
        return employee.getId();
    }

    public int addBranchManager(String name,int bankAccountNumber, double salary, int branchId) throws Exception {

        if(branchManagers.containsKey(branchId))
            throw new Exception("Branch already has a manager");

        BranchManager branchManager = new BranchManager(idCounter++, name, new ArrayList<Role>(), bankAccountNumber, salary, branchId,1);
        branchManagers.put(branchManager.getBranchId(), branchManager);
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
//        if (employeesLoggedInIds.contains(id)){
//            throw new Exception("Employee is already logged in");
//        }
//        employeesLoggedInIds.add(id);
    }

    public void logout(Integer id) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
//        if (!employeesLoggedInIds.contains(id)){
//            throw new Exception("Employee is not logged in");
//        }
//
//        employeesLoggedInIds.remove(id);
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

    public void isEmployeeExist(Integer id, Integer branchId) throws Exception{
        if(!employees.containsKey(id))
            throw new Exception("Employee not found");
    }

    public void isHrManagerExist() throws Exception{
        if(hrManager == null)
            throw new Exception("HR manager not found");
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
            if (employee.getBranchId() == branchId && !(branchManagers.get(branchId).getId() == employee.getId())){
                output += employee.toString() + "\n";
            }
        }
        if(output.equals(""))
            throw new Exception("No employees in branch");

        return output;
    }

    public void checkEmployeeRole(Integer id, Role role) throws Exception{
        if(employees.get(id) == null){
            throw new IllegalArgumentException("Employee not found");
        }
        if(!employees.get(id).getRoles().contains(role)){
            throw new IllegalArgumentException("Employee doesn't have the role");
        }
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
        boolean output = false;
        for(BranchManager manager : branchManagers.values()){
            if(manager.getId() == id){
                output = true;
            }
        }
        return output || id == hrManager.getId() || id == deliveryManager.getId();
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

    public void canWork(Integer id, DayOfTheWeek day, PartOfDay partOfDay) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        if(employees.get(id).getShiftCantWork().contains(new Pair<>(day, partOfDay))){
            throw new Exception("Employee can't work at this time");
        }

    }

    public Driver assignDriver(Predicate<Driver> driverPred, DayOfTheWeek day, PartOfDay part, Integer bId){
        for(Employee employee : employees.values()){
            if(employee.getBranchId()==bId && employee.getRoles().contains(Role.Driver)){
                Driver driver = new Driver(employee.getId(),employee.getName(),new License(employee.getWeight()));
                if(driverPred.test(driver)){
                    return driver;
                }
            }

        }
        return null;
    }

    public void setDriver(Integer id, Float weight) throws Exception {
        if(employees.get(id) == null){
            throw new Exception("Employee not found");
        }
        employees.get(id).setWeight(weight);
    }

    public void loadDatabase() throws Exception{
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        List<EmployeeDTO> employees;
        try {
            employees = employeeDataManager.loadDatabase();
        } catch (Exception e) {
            throw new Exception("Error loading employees data");
        }
        for (EmployeeDTO employee : employees) {
            if(employee.getManager().equals(HRManager.MANGER_OF_HR)) {
                hrManager = new HRManager(employee);
                this.employees.put(hrManager.getId(), hrManager);
            }
            else if(employee.getManager().equals(HRManager.HR_MANAGER_ID)) {
                branchManagers.put(employee.getBranchId(), new BranchManager(employee));
                this.employees.put(employee.getId(), new BranchManager(employee));
            }
            else if(employee.getManager().equals(DeliveryManager.MANGER_OF_DELIVERY)) {
                deliveryManager = new DeliveryManager(employee);
                this.employees.put(deliveryManager.getId(), deliveryManager);
            }
            else
                this.employees.put(employee.getId(), new Employee(employee));
        }

        if(!employees.isEmpty())
            idCounter = employees.stream().map(EmployeeDTO::getId).max(Integer::compare).get() + 1;
    }

    public void testMode(){
        for(Employee employee : employees.values()){
            employee.testMode();
        }
        for(BranchManager manager : branchManagers.values()){
            manager.testMode();
        }
        hrManager.testMode();
    }

    public void deleteData() {
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        employeeDataManager.deleteData();
    }

    public Employee assignStoreKeeper(DayOfTheWeek day, PartOfDay part, Integer bId) {
        for(Employee employee : employees.values()){
            if(employee.getBranchId()==bId && employee.getRoles().contains(Role.StoreKeeper)){
                return employee;
            }
        }
        return null;
    }
}