package DomainLayer.Branches;

import DomainLayer.Employees.Employee;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;

import java.util.*;

public class Shift {
    private Pair<DayOfTheWeek,PartOfDay> id;
    private List<Role> neededRoles;
    private HashMap<Integer, Role> employees;
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
        employees.put(employee,role);
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

    public void addNeededRoles(List<Role> neededRoles){
        this.neededRoles.addAll(neededRoles);
    }

    public void exchangeShift(Integer id1, Integer id2, Role role2) throws Exception {
        Role role = employees.get(id1);

        if(role == role2){
            employees.remove(id1);
            employees.put(id2,role2);
            return;
        }

        if(neededRoles.contains(role)){
            int counter = 0;
            for(Role r : neededRoles){
                if(r == role){
                    counter++;
                }
            }
            if(counter > 1){
                employees.remove(id1);
                employees.put(id2,role2);
                return;
            }
        }else{
            employees.remove(id1);
            employees.put(id2,role2);
            return;
        }
        throw new Exception("Employees can't exchange shifts");
    }

}
