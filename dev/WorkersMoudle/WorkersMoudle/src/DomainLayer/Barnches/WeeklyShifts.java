package DomainLayer.Barnches;

import DomainLayer.Pair;

import java.util.*;

import java.util.*;

public class WeeklyShifts {
    private HashMap<Pair<DayOfTheWeek,PartOfDay>, Shift> shifts;
    private Date firstDayOfWeek;

    public WeeklyShifts() {
        this.shifts = new HashMap<>();
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            for (PartOfDay part : PartOfDay.values()) {
                shifts.put(new Pair<>(day, part), new Shift(new Pair<>(day,part))); // Initialize shift
            }
        }
        this.firstDayOfWeek = Date.from(new Date().toInstant());
    }

    public WeeklyShifts(Date firstDayOfWeek) {
        this.shifts = new HashMap<>();
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            for (PartOfDay part : PartOfDay.values()) {
                shifts.put(new Pair<>(day, part), new Shift(new Pair<>(day,part))); // Initialize shift
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
    public void setShift(Pair<DayOfTheWeek,PartOfDay> pair, Shift shift) {
        Pair<DayOfTheWeek, PartOfDay> key = new Pair<>(pair.getKey(), pair.getValue());
        shifts.put(key, shift);
    }

    // Method to get a shift
    public Shift getShift(Pair<DayOfTheWeek, PartOfDay> pair) {
        Pair<DayOfTheWeek, PartOfDay> key = new Pair<>(pair.getKey(), pair.getValue());
        return shifts.get(key);
    }

}

