package DomainLayer.Branches;

import DomainLayer.Employees.Role;


import java.util.ArrayList;
import java.util.Calendar;
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
        create6weeks();
    }

    public Branch(int id, String name, WeeklyShifts currentWeek, List<WeeklyShifts> upcomingweeks, List<WeeklyShifts> pastweeks){
        this.id = id;
        this.name = name;
        this.currentWeek = currentWeek;
        this.upcomingweeks = upcomingweeks;
        this.pastweeks = pastweeks;
    }

    public void create6weeks() {
        Calendar calendar = Calendar.getInstance();

        // Adjust to the next Sunday if today is not Sunday
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK));
        }

        // Now, 'calendar' should be set to the next Sunday
        Date nextWeekStartDate = calendar.getTime();

        for (int i = 0; i < 6; i++) {
            AddWeek(nextWeekStartDate);
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            nextWeekStartDate = calendar.getTime();
        }
    }

    public void AddWeek(Date firstDayOfWeek) {
        if (currentWeek == null) {
            this.currentWeek = new WeeklyShifts(firstDayOfWeek);
        } else {
            upcomingweeks.add(new WeeklyShifts(firstDayOfWeek));
        }
    }

    public void checkWeekPast() { //if date changes call this function
        if (currentWeek != null) {
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            if (today.after(currentWeek.getLastDayOfWeek())) {
                pastweeks.add(currentWeek);
                currentWeek = upcomingweeks.remove(0);

                // Ensure there's always at least one upcoming week
                if (upcomingweeks.isEmpty()) {
                    upcomingweeks.add(createNextWeek());
                }
            }
        }
    }

    private WeeklyShifts createNextWeek() {
        Calendar calendar = Calendar.getInstance();

        // Adjust to the next Sunday if today is not Sunday
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK));
        }

        // Add one week to get to the next week's Sunday
        calendar.add(Calendar.WEEK_OF_YEAR, 1);

        Date nextWeekStartDate = calendar.getTime();
        return new WeeklyShifts(nextWeekStartDate);
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
