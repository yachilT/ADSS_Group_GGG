package DomainLayer.Branches;

import DataLayer.BranchData.ShiftsDTO;
import DomainLayer.Pair;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class WeeklyShifts {
    private static final int INT_MORNING = 0;

    private HashMap<DayOfTheWeek, Pair<Shift,Shift>> shifts;
    private Date firstDayOfWeek;

    private Date lastDayOfWeek;


    public WeeklyShifts(Date firstDayOfWeek, int branchId) {
        Date currentDate = firstDayOfWeek;
        this.shifts = new HashMap<>();
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            shifts.put(day, new Pair<>(new Shift(new Pair<>(day, PartOfDay.Morning), currentDate, branchId, PartOfDay.Morning ),
                    new Shift(new Pair<>(day, PartOfDay.Evening), currentDate,branchId, PartOfDay.Evening))); // Initialize shift
            // Move to the next day
            currentDate = Date.from(currentDate.toInstant().plus(1, ChronoUnit.DAYS));
        }

        this.firstDayOfWeek = firstDayOfWeek;
        this.lastDayOfWeek = Date.from(firstDayOfWeek.toInstant().plus(6, ChronoUnit.DAYS));
    }

    public WeeklyShifts(List<ShiftsDTO> shiftsDTOs){
        firstDayOfWeek = shiftsDTOs.get(0).getDate();
        shifts = new HashMap<>();

        DayOfTheWeek[] days = DayOfTheWeek.values();

        for(int i =0; i<days.length; i++){
            DayOfTheWeek day = days[i];

            shiftsDTOs = shiftsDTOs.stream().filter(shiftDTO -> dateToDayOfTheWeek(shiftDTO.getDate()) == day).collect(Collectors.toList());

            if(shiftsDTOs.size() != 2){
                throw new IllegalArgumentException("ShiftsDTOs size is not 2");
            }
            ShiftsDTO shiftDTO1 = shiftsDTOs.remove(0);
            Shift shift1 = new Shift(shiftDTO1);

            ShiftsDTO shiftDTO2 = shiftsDTOs.remove(0);
            Shift shift2 = new Shift(shiftDTO2);


            if(shift1.getId().getValue() == PartOfDay.Morning)
                shifts.put(day, new Pair<>(shift1,shift2));
            else
                shifts.put(day, new Pair<>(shift2,shift1));

        }

    }


    // Getters and setters for firstDayOfWeek
    public Date getFirstDayOfWeek() {
        return firstDayOfWeek;
    }



    // Method to assign a shift


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

    public static DayOfTheWeek dateToDayOfTheWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return DayOfTheWeek.values()[dayOfWeek - 1];
    }




    public Date getLastDayOfWeek() {
        return lastDayOfWeek;
    }
}
