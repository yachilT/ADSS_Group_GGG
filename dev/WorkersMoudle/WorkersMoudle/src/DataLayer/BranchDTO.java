package DataLayer;

import DomainLayer.Branches.Branch;
import DomainLayer.Branches.WeeklyShifts;

import java.util.List;

public class BranchDTO {

    int id;
    String name;
    String address;
    WeeklyShifts currentWeek;
    List<WeeklyShifts> upcomingweeks;
    List<WeeklyShifts> pastweeks;

    public BranchDTO(){}

    public BranchDTO(int id, String name, String address, WeeklyShifts currentWeek, List<WeeklyShifts> upcomingweeks, List<WeeklyShifts> pastweeks){
        this.id = id;
        this.name = name;
        this.address = address;
        this.currentWeek = currentWeek;
        this.upcomingweeks = upcomingweeks;
        this.pastweeks = pastweeks;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public WeeklyShifts getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(WeeklyShifts currentWeek) {
        this.currentWeek = currentWeek;
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
