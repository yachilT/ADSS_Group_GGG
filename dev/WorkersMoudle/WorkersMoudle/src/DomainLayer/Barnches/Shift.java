package WorkersMoudle.WorkersMoudle.src.DomainLayer.Barnches;

import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import WorkersMoudle.WorkersMoudle.src.DomainLayer.Employees.Employee;

import java.util.ArrayList;
import java.util.List;

public class Shift {
    private Pair<DayOfTheWeek,PartOfDay> id;
    private List<Role> neededRoles;
    private List<Employee> employees;


    public Shift(){}
    public Shift(Pair id) {
        this.id = id;
        this.neededRoles = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Shift(Pair id, List<Role> neededRoles) {
        this.id = id;
        this.neededRoles = neededRoles;
        this.employees = new ArrayList<>();
    }
    public Shift(Pair id, List<Role> neededRoles, List<Employee> employees) {
        this.id = id;
        this.neededRoles = neededRoles;
        this.employees = employees;
    }

    public void addRole(Role role){
        neededRoles.add(role);
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public void removeEmployee(Employee employee){
        employees.remove(employee);
    }

    public boolean isEmployeeInShift(Employee employee){
        return employees.contains(employee);
    }

    //getters and setters
    public Pair<DayOfTheWeek, PartOfDay> getId() {
        return id;
    }

    public void setId(Pair<DayOfTheWeek, PartOfDay> id) {
        this.id = id;
    }

    public List<Role> getNeededRoles() {
        return neededRoles;
    }

    public void setNeededRoles(List<Role> neededRoles) {
        this.neededRoles = neededRoles;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
