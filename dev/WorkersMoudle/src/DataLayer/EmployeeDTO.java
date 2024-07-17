package DataLayer;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Employee;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;

import java.util.Date;
import java.util.List;

public class EmployeeDTO {

    private Integer id;
    private String name;
    private String password;
    private List<Role> roles;
    private int bankAccountNumber;
    private double salary;
    private Date dateJoined;
    private int branchId;
    private Date dateLeft;
    private Integer weight;

    private List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences;
    private List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork;

    private Integer manager;

    private EmployeeDataManager employeeDataManager;

    public EmployeeDTO(){

    }

    public EmployeeDTO(Employee employee, Integer manager) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.password = employee.getPassword();
        this.roles = employee.getRoles();
        this.bankAccountNumber = employee.getBankAccountNumber();
        this.salary = employee.getSalary();
        this.dateJoined = employee.getDateJoined();
        this.branchId = employee.getBranchId();
        this.dateLeft = employee.getDateLeft();
        this.shiftPreferences = employee.getShiftPreferences();
        this.shiftCantWork = employee.getShiftCantWork();
        this.manager = manager;
        this.weight = employee.getWeight();

        employeeDataManager = new EmployeeDataManager();

        employeeDataManager.insertDTO(this);
    }

    public EmployeeDTO(Integer id, String name, String password, List<Role> roles, int bankAccountNumber, double salary, Date dateJoined, int branchId, Date dateLeft, List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences, List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork, Integer manager, Integer weight) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.dateJoined = dateJoined;
        this.branchId = branchId;
        this.dateLeft = dateLeft;
        this.shiftPreferences = shiftPreferences;
        this.shiftCantWork = shiftCantWork;
        this.manager = manager;
        this.weight = weight;

        employeeDataManager = new EmployeeDataManager();

    }


    private void update() throws Exception{
        if(!employeeDataManager.updateDTO(this))
            throw new Exception("Failed to update");
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) throws Exception{
        this.weight = weight;
        try {
            update();
        }catch (Exception e){
            throw e;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setManager(Integer manager){
        this.manager = manager;
    }

    public Integer getManager(){
        return manager;
    }

    public void setName(String name) throws Exception{
        String temp = this.name;
        this.name = name;
        try{
            update();
        }catch (Exception e){
            this.name = temp;
            throw e;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception{
        String temp = this.password;
        this.password = password;
        try{
            update();
        }catch (Exception e){
            this.password = temp;
            throw e;
        }
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) throws Exception{
        List<Role> temp = this.roles;
        this.roles = roles;
        try{
            update();
        }catch (Exception e){
            this.roles = temp;
            throw e;
        }
    }

    public void loadRoles(List<Role> roles){
        this.roles = roles;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) throws Exception{
        Integer temp = this.bankAccountNumber;
        this.bankAccountNumber = bankAccountNumber;
        try{
            update();
        }catch (Exception e){
            this.bankAccountNumber = temp;
            throw e;
        }
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) throws Exception{
        double temp = this.salary;
        this.salary = salary;
        try{
            update();
        }catch (Exception e){
            this.salary = temp;
            throw e;
        }
    }

    public Date getDateJoined() {
        return dateJoined;
    }


    public int getBranchId() {
        return branchId;
    }

    public Date getDateLeft() {
        return dateLeft;
    }

    public void setDateLeft(Date dateLeft) throws Exception{
        Date temp = this.dateLeft;
        this.dateLeft = dateLeft;
        try{
            update();
        }catch (Exception e){
            this.dateLeft = temp;
            throw e;
        }
    }

    public List<Pair<DayOfTheWeek, PartOfDay>> getShiftPreferences() {
        return shiftPreferences;
    }

    public void setShiftPreferences(List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences){
        this.shiftPreferences = shiftPreferences;
    }

    public void addShiftPreference(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception{
        List<Pair<DayOfTheWeek, PartOfDay>> temp = this.shiftPreferences;
        this.shiftPreferences.add(shift);
        try{
            update();
        }catch (Exception e){
            this.shiftPreferences = temp;
            throw e;
        }
    }

    public void addShiftCantWork(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception{
        List<Pair<DayOfTheWeek, PartOfDay>> temp = this.shiftCantWork;
        this.shiftCantWork.add(shift);
        try{
            update();
        }catch (Exception e){
            this.shiftCantWork = temp;
            throw e;
        }
    }

    public void removeShiftPreference(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception{
        List<Pair<DayOfTheWeek, PartOfDay>> temp = this.shiftPreferences;
        this.shiftPreferences.remove(shift);
        try{
            update();
        }catch (Exception e){
            this.shiftPreferences = temp;
            throw e;
        }
    }

    public void removeShiftCantWork(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception{
        List<Pair<DayOfTheWeek, PartOfDay>> temp = this.shiftCantWork;
        this.shiftCantWork.remove(shift);
        try{
            update();
        }catch (Exception e){
            this.shiftCantWork = temp;
            throw e;
        }
    }

    public List<Pair<DayOfTheWeek, PartOfDay>> getShiftCantWork() {
        return shiftCantWork;
    }

    public void setShiftCantWork(List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork){
        this.shiftCantWork = shiftCantWork;
    }

    public void testMode(){
        this.employeeDataManager.testMode();
    }

}