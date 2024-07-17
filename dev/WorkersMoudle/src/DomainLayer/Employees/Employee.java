package DomainLayer.Employees;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DataLayer.EmployeeDTO;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Pair;

public class Employee {

    private Integer id;
    private String name;
    private String password;
    private List<Role> roles;
    private int bankAccountNumber;
    private double salary;
    private Date dateJoined;
    private int branchId;
    private Date dateLeft;
    private Float weight;

    private List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences;
    private List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork;

    protected EmployeeDTO employeeDTO;

    public Employee(){}

    public Employee(Integer id, String name, List<Role> roles,
                    int bankAccountNumber, double salary, int branchId, Integer manager) throws Exception {
        if(salary < 0)
            throw new Exception("Salary must be positive");
        this.id = id;
        this.name = name;
        this.password = null;
        this.roles = roles;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.dateJoined = new Date(System.currentTimeMillis());
        this.branchId = branchId;
        this.dateLeft = null;
        shiftPreferences = new ArrayList<>();
        shiftCantWork = new ArrayList<>();
        employeeDTO = new EmployeeDTO(this, manager);
        this.weight = null;
    }



    public Employee(EmployeeDTO employeeDTO){
        this.employeeDTO = employeeDTO;
        this.id = employeeDTO.getId();
        this.name = employeeDTO.getName();
        this.password = employeeDTO.getPassword();
        this.roles = employeeDTO.getRoles();
        this.bankAccountNumber = employeeDTO.getBankAccountNumber();
        this.salary = employeeDTO.getSalary();
        this.dateJoined = employeeDTO.getDateJoined();
        this.branchId = employeeDTO.getBranchId();
        this.dateLeft = employeeDTO.getDateLeft();
        this.shiftPreferences = employeeDTO.getShiftPreferences();
        this.shiftCantWork = employeeDTO.getShiftCantWork();
        this.weight = employeeDTO.getWeight();
    }

    public boolean isNew(){
        return password == null;
    }
    public void setPassword(String password) throws Exception{
        try{
            employeeDTO.setPassword(password);
            this.password = password;
        }catch (Exception e) {
            throw e;
        }
    }

    //getters and setters
    public boolean isDriver(){
        return roles.contains(Role.Driver);
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) throws Exception{
        this.weight = weight;
        employeeDTO.setWeight(weight);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) throws Exception{
        try{
            employeeDTO.setRoles(roles);
            this.roles = roles;
        }catch (Exception e){
            throw e;
        }
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Date getDateLeft() {
        return dateLeft;
    }

    public void setDateLeft(Date dateLeft) throws Exception {
        try {
            employeeDTO.setDateLeft(dateLeft);
            this.dateLeft = dateLeft;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Pair<DayOfTheWeek, PartOfDay>> getShiftPreferences() {
        return shiftPreferences;
    }

    public List<Pair<DayOfTheWeek, PartOfDay>> getShiftCantWork() {
        return shiftCantWork;
    }

    public void addShiftPreference(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception{
        try{
            if(shiftCantWork.stream().anyMatch(s -> s.getKey().equals(shift.getKey()) && s.getValue().equals(shift.getValue())))
                throw new Exception("Shift already in cant work shifts");
            employeeDTO.addShiftPreference(shift);
            shiftPreferences.add(shift);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addShiftCantWork(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception {
        try{
            if(shiftPreferences.stream().anyMatch(s -> s.getKey().equals(shift.getKey()) && s.getValue().equals(shift.getValue())))
                throw new Exception("Shift already in preferences");
            employeeDTO.addShiftCantWork(shift);
            shiftCantWork.add(shift);
        } catch (Exception e) {
            throw e;
        }
    }

    public void removeShiftPreference(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception {
        if (!shiftPreferences.contains(shift)) {
            throw new Exception("Shift not found in preferences");
        }

        try{
            employeeDTO.removeShiftPreference(shift);
            shiftPreferences.remove(shift);
        } catch (Exception e) {
            throw e;
        }
    }

    public void removeShiftCantWork(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception {
        if (!shiftCantWork.contains(shift)) {
            throw new Exception("Shift not found in cant work shifts");
        }

        try{
            employeeDTO.removeShiftCantWork(shift);
            shiftCantWork.remove(shift);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String toString(){
        if(shiftPreferences.isEmpty() && shiftCantWork.isEmpty()){
            return "Employee id: " + id + "\n" +
                    "name: " + name + "\n" +
                    "roles: " + roles + "\n" +
                    "shiftPreferences: " + "None" + "\n" +
                    "shiftCantWork: " + "None";
        }

        if(shiftPreferences.isEmpty()){
            return "id: " + id + "\n" +
                    "name: " + name + "\n" +
                    "roles: " + roles + "\n" +
                    "shiftPreferences: " + "None" + "\n" +
                    "shiftCantWork: " + "\n" + shiftsToString(shiftCantWork);
        }

        if(shiftCantWork.isEmpty()){
            return "id: " + id + "\n" +
                    "name: " + name + "\n" +
                    "roles: " + roles + "\n" +
                    "shiftPreferences: " + "\n" + shiftsToString(shiftPreferences) + "\n" +
                    "shiftCantWork: " + "None";
        }


        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "roles: " + roles + "\n" +
                "shiftPreferences: " + "\n" + shiftsToString(shiftPreferences) + "\n" +
                "shiftCantWork: " + "\n" +shiftsToString(shiftCantWork);
    }

    public String shiftsToString(List<Pair<DayOfTheWeek, PartOfDay>> list){
        String output = "";
        for(Pair<DayOfTheWeek, PartOfDay> shift : list){
            output += shift.getKey().toString() + "-" + shift.getValue().toString() + "\n";
        }
        return output;
    }

    public void testMode(){
        this.employeeDTO.testMode();
    }

}