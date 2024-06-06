package DomainLayer.Branches;

import DomainLayer.Pair;

import java.util.*;

public class WeeklyShifts {
    private HashMap<DayOfTheWeek, Pair<Shift,Shift>> shifts;
    private Date firstDayOfWeek;

    public WeeklyShifts() {
        this.shifts = new HashMap<>();
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            for (PartOfDay part : PartOfDay.values()) {
                shifts.put(day, new Pair<>(new Shift(new Pair<>(day,part)), new Shift(new Pair<>(day,part)))); // Initialize shift
            }
        }
        this.firstDayOfWeek = Date.from(new Date().toInstant());
    }

    public WeeklyShifts(Date firstDayOfWeek) {
        this.shifts = new HashMap<>();
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            for (PartOfDay part : PartOfDay.values()) {
                shifts.put(day, new Pair<>(new Shift(new Pair<>(day,part)), new Shift(new Pair<>(day,part)))); // Initialize shift
            }
        }
        this.firstDayOfWeek = firstDayOfWeek;
    }

    // Getter and setter for firstDayOfWeek
    public Date getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    public void setFirstDayOfWeek(Date firstDayOfWeek) {
        this.firstDayOfWeek = firstDayOfWeek;
    }

    // Method to assign a shift
    public void setShift(DayOfTheWeek day,PartOfDay part, Shift shift) {
        shifts.put(day, new Pair<>(shift,shift));
    }

    // Method to get a shift
    public Shift getShift(DayOfTheWeek day,PartOfDay part) {
        if(part == PartOfDay.Morning)
            return shifts.get(day).getKey();

        return shifts.get(day).getValue();
    }

}

