package DomainLayer.Branches;

import DomainLayer.Employees.Role;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Branch {
    int id;
    String name;
    String address;
    WeeklyShifts currentWeek;
    List<WeeklyShifts> upcomingweeks;
    List<WeeklyShifts> pastweeks;

    public Branch(){}

    public Branch(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
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

    public void addEmployeeToShift(Integer employee, Role role, DayOfTheWeek day, PartOfDay part) throws Exception {
        if(employee == null ){
            throw new Exception("Employee is null");
        }//also check if the key is legal!
        currentWeek.getShift(day,part).addEmployee(employee,role);
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
