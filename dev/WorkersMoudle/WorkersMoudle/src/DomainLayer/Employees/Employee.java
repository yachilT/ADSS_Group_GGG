package DomainLayer.Employees;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences;
    private List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork;

    public Employee(){}

    public Employee(Integer id, String name, List<Role> roles,
                    int bankAccountNumber, double salary, int branchId) throws Exception {
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
    }

    public boolean isNew(){
        return password == null;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public void setDateLeft(Date dateLeft) {
        this.dateLeft = dateLeft;
    }

    public List<Pair<DayOfTheWeek, PartOfDay>> getShiftPreferences() {
        return shiftPreferences;
    }

    public List<Pair<DayOfTheWeek, PartOfDay>> getShiftCantWork() {
        return shiftCantWork;
    }

    public void addShiftPreference(Pair<DayOfTheWeek, PartOfDay> shift) {
        shiftPreferences.add(shift);
    }

    public void addShiftCantWork(Pair<DayOfTheWeek, PartOfDay> shift) {
        shiftCantWork.add(shift);
    }

    public void removeShiftPreference(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception {
        if (!shiftPreferences.contains(shift)) {
            throw new Exception("Shift not found in preferences");
        }
        shiftPreferences.remove(shift);
    }

    public void removeShiftCantWork(Pair<DayOfTheWeek, PartOfDay> shift) throws Exception {
        if (!shiftCantWork.contains(shift)) {
            throw new Exception("Shift not found in cant work shifts");
        }
        shiftCantWork.remove(shift);
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

}
