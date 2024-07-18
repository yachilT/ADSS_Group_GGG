package DomainLayer.Branches;

import DataLayer.BranchData.ShiftsDTO;
import DataLayer.DateEncryptDecrypt;
import DomainLayer.Employees.Employee;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import java.text.SimpleDateFormat;

import java.util.*;

public class Shift {
    private Pair<DayOfTheWeek,PartOfDay> id;
    private List<Role> neededRoles;
    private HashMap<Integer, Role> employees;
    private Date date;

    private ShiftsDTO shiftsDTO;


    public Shift(Pair id, Date date, int branchId, PartOfDay partOfDay) {
        this.id = id;
        this.neededRoles = new ArrayList<>();
        this.employees = new HashMap<>();
        this.date = date;

        shiftsDTO = new ShiftsDTO(branchId,employees, date, partOfDay, neededRoles);
    }
    public Shift(ShiftsDTO shiftsDTO) {
        this.id = new Pair<>(WeeklyShifts.dateToDayOfTheWeek(shiftsDTO.getDate()), shiftsDTO.getPartOfDay());
        this.neededRoles = new ArrayList<>();
        this.employees = new HashMap<>();
        this.date =shiftsDTO.getDate();

        this.shiftsDTO = shiftsDTO;
    }
    public void addRole(Role role){
        neededRoles.add(role);
    }

    public void addEmployee(Integer employee , Role role) throws Exception {
        if(employees.containsKey(employee)){
            throw new Exception("Employee already in the shift");
        }
        employees.put(employee,role);
        shiftsDTO.addEmployee(employee, role);
    }

    public boolean isFulfilled(){
        for(Role role : neededRoles){
            if(!employees.containsKey(role)){
                return false;
            }
        }
        return true;
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
        shiftsDTO.addNeededRoles(neededRoles);
    }

    public boolean isThereADriver() {
        for (Role role : employees.values()) {
            if (role == Role.Driver) {
                return true;
            }
        }
        return false;
    }

    public void exchangeShift(Integer id1, Integer id2, Role role2) throws Exception {
        if(!employees.containsKey(id1)){
            throw new Exception("You are not in this shift");
        }

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


    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

        if(neededRoles.isEmpty() && employees.isEmpty()){
            return "Shift{\n" +
                    id.getKey().toString() + " " +id.getValue().toString() + ",\n" +
                    "  neededRoles=" + "N/A" + ",\n" +
                    "  employees-roles=" + "N/A" + "\n" +
                    "  date=" + dateFormat.format(date) + "\n" +
                    '}';
        }

        if(employees.isEmpty()){
            return "Shift{\n" +
                    id.getKey().toString() + " " +id.getValue().toString() + ",\n" +
                    "  neededRoles=" + neededRoles + ",\n" +
                    "  employees-roles=\n" + "N/A" + "\n" +
                    "  date=" + dateFormat.format(date) + "\n" +
                    '}';
        }

        if(neededRoles.isEmpty()){
            return "Shift{\n" +
                    id.getKey().toString() + " " +id.getValue().toString() + ",\n" +
                    "  neededRoles=" + "N/A" + ",\n" +
                    "  employees-roles=" +  employeesToString() + "\n" +
                    "  date=" + dateFormat.format(date) + "\n" +
                    '}';
        }

        return "Shift{\n" +
                id.getKey().toString() + " " +id.getValue().toString() + ",\n" +
                "  neededRoles=" + neededRoles + ",\n" +
                "  employees-roles=\n" + employeesToString() + "\n" +
                "  date=" + dateFormat.format(date) + "\n" +
                '}';
    }

    public void deleteEmployee(Integer employeeId) throws Exception{
        if(employees.isEmpty()){
            throw new Exception("No employees in the shift");
        }
        if(!employees.containsKey(employeeId)){
            throw new Exception("Employee not found in the shift");
        }
        employees.remove(employeeId);

        shiftsDTO.removeEmployee(employeeId);
    }

    public String employeesToString() {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Integer, Role> entry : employees.entrySet()) {
            str.append("\n").append("Employee:").append(entry.getKey()).append(" ,")
                    .append("Role:").append(entry.getValue()).append("\n");
        }
        return str.toString();
    }

    // for tests

    public Set<Integer> getEmployees() {
        return employees.keySet();

    }

    public boolean isAssigned(Role role) {
        return neededRoles.contains(role);
    }

    public boolean assignDriver(Employee driver) {
        employees.put(driver.getId(), Role.Driver);
        shiftsDTO.addEmployee(driver.getId(), Role.Driver);
        return employees.containsValue(Role.Driver);
    }
}