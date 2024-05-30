package DomainLayer.Employees;

import java.util.Date;
import java.util.List;

public class Employee {

    private int id;
    private String name;
    private String password;
    private List<Role> roles;
    private boolean loggedIn;
    private int bankAccountNumber;
    private double salary;
    private Date dateJoined;
    private int branchId;
    private Date dateLeft;

    public Employee(){}

    public Employee(int id, String name, List<Role> roles, boolean loggedIn, int bankAccountNumber, double salary, int branchId) {
        this.id = id;
        this.name = name;
        this.password = null;
        this.roles = roles;
        this.loggedIn = loggedIn;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.dateJoined = Date.from(java.time.Instant.now());
        this.branchId = branchId;
        this.dateLeft = null;
    }

    public Employee(int id, String name, String password, List<Role> roles, boolean loggedIn, int bankAccountNumber, double salary, Date dateJoined, int branchId, Date dateLeft) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.loggedIn = loggedIn;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.dateJoined = dateJoined;
        this.branchId = branchId;
        this.dateLeft = dateLeft;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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
}
