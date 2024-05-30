package DomainLayer.Barnches;

import DomainLayer.Employees.Employee;
import DomainLayer.Pair;

import java.util.*;

public class Branch {
    int id;
    String name;
    WeeklyShifts currentWeek;
    List<WeeklyShifts> upcomingweeks;
    List<WeeklyShifts> pastweeks;

    public Branch(){}

    public Branch(int id, String name){
        this.id = id;
        this.name = name;
        currentWeek = null;
        this.upcomingweeks = new ArrayList<>();
        this.pastweeks = new ArrayList<>();
    }

    public Branch(int id, String name, WeeklyShifts currentWeek, List<WeeklyShifts> upcomingweeks, List<WeeklyShifts> pastweeks){
        this.id = id;
        this.name = name;
        this.currentWeek = currentWeek;
        this.upcomingweeks = upcomingweeks;
        this.pastweeks = pastweeks;
    }

    public void AddWeek(Date firstDayOfWeek){
        if(currentWeek == null){
            this.currentWeek = new WeeklyShifts(firstDayOfWeek);
        }else{
            upcomingweeks.add(new WeeklyShifts(firstDayOfWeek));
        }
    }

    public void weekPast(){
        if(currentWeek != null) {
            pastweeks.add(currentWeek);
            if(!upcomingweeks.isEmpty())
                currentWeek = upcomingweeks.remove(0);
            else
                currentWeek = null;
        }
    }

    public void addEmployeeToShift(Employee employee, Pair<DayOfTheWeek,PartOfDay> key) throws Exception {
        if(employee == null ){
            throw new Exception("Employee is null");
        }//also check if the key is legal!
        currentWeek.getShift(key).addEmployee(employee);
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

    public List<WeeklyShifts> getUpcomingweeks() {
        return upcomingweeks;
    }

    public void setUpcomingweeks(List<WeeklyShifts> upcomingweeks) {
        this.upcomingweeks = upcomingweeks;
    }

    public List<WeeklyShifts> getPastweeks() {
        return pastweeks;
    }

    public void setPastweeks(List<WeeklyShifts> pastweeks) {
        this.pastweeks = pastweeks;
    }
}
