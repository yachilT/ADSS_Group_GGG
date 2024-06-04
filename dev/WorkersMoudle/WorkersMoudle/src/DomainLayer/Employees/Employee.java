package DomainLayer.Employees;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import DomainLayer.Employees.Role;
import DomainLayer.Barnches.DayOfTheWeek;
import DomainLayer.Barnches.PartOfDay;
import DomainLayer.Barnches.Shift;
import DomainLayer.Pair;

public class Employee {

    private int id;
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

    public Employee(int id, String name, String password, List<Role> roles,
                    int bankAccountNumber, double salary, int branchId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.dateJoined = new Date(System.currentTimeMillis());
        this.branchId = branchId;
        this.dateLeft = null;
        shiftPreferences = new ArrayList<>();
        shiftCantWork = new ArrayList<>();
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

}

