package DomainLayer.Branches;

import DomainLayer.Pair;

import java.time.temporal.ChronoUnit;
import java.util.*;

public class WeeklyShifts {
    private HashMap<DayOfTheWeek, Pair<Shift,Shift>> shifts;
    private Date lastDayOfWeek;


    public WeeklyShifts(Date firstDayOfWeek) {
        Date currentDate = firstDayOfWeek;
        this.shifts = new HashMap<>();
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            shifts.put(day, new Pair<>(new Shift(new Pair<>(day, PartOfDay.Morning), currentDate), new Shift(new Pair<>(day, PartOfDay.Evening), currentDate))); // Initialize shift
            // Move to the next day
            currentDate = Date.from(currentDate.toInstant().plus(1, ChronoUnit.DAYS));
        }
        // Save the last day of the week
        this.lastDayOfWeek = Date.from(currentDate.toInstant().minus(1, ChronoUnit.DAYS));
    }

    // Getters and setters for lastDayOfWeek
    public Date getLastDayOfWeek() {
        return lastDayOfWeek;
    }

    public void setLastDayOfWeek(Date lastDayOfWeek) {
        this.lastDayOfWeek = lastDayOfWeek;
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

    public String toString(DayOfTheWeek day, PartOfDay part){
        if(part == PartOfDay.Morning)
            return shifts.get(day).getKey().toString();
        else
            return shifts.get(day).getValue().toString();
    }

}

