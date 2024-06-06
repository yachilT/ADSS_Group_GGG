package DomainLayer.Branches;

import DomainLayer.Employees.Employee;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;

import java.util.*;

public class Shift {
    private Pair<DayOfTheWeek,PartOfDay> id;
    private List<Role> neededRoles;
    private HashMap<Role, Integer> employees;
    private Date date;


    public Shift(){}
    public Shift(Pair id) {
        this.id = id;
        this.neededRoles = new ArrayList<>();
        this.employees = new HashMap<>();
    }

    public Shift(Pair id, Date date) {
        this.id = id;
        this.neededRoles = new ArrayList<>();
        this.employees = new HashMap<>();
        this.date = date;
    }

    public Shift(Pair id, List<Role> neededRoles) {
        this.id = id;
        this.neededRoles = neededRoles;
        this.employees = new HashMap<>();
    }
    public Shift(Pair id, List<Role> neededRoles, List<Employee> employees) {
        this.id = id;
        this.neededRoles = neededRoles;
        this.employees = new HashMap<>();
    }

    public void addRole(Role role){
        neededRoles.add(role);
    }

    public void addEmployee(Integer employee , Role role){
        employees.put(role,employee);
    }

    public boolean isFulfilled(){
        for(Role role : neededRoles){
            if(!employees.containsKey(role)){
                return false;
            }
        }
        return true;
    }

    public void removeEmployee(Employee employee){
        employees.remove(employee);
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

}
