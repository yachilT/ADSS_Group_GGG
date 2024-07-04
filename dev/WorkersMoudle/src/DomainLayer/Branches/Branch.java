package DomainLayer.Branches;

import DataLayer.BranchData.BranchDTO;
import DataLayer.BranchData.ShiftsDTO;
import DataLayer.DateEncryptDecrypt;
import DomainLayer.Employees.Role;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Branch {
    private int id;
    private String name;
    private String address;
    private WeeklyShifts currentWeek;
    private List<WeeklyShifts> upcomingweeks;
    private List<WeeklyShifts> pastweeks;

    private BranchDTO branchDTO;

    public Branch(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        currentWeek = null;
        this.upcomingweeks = new ArrayList<>();
        this.pastweeks = new ArrayList<>();
        create6weeks();
        branchDTO = new BranchDTO(this);

        branchDTO.insertDTO();
    }

    public Branch(BranchDTO branchDTO, List<ShiftsDTO> Shifts) {
        this.id = branchDTO.getId();
        this.name = branchDTO.getName();
        this.address = branchDTO.getAddress();
        this.branchDTO = branchDTO;
        manageShifts(Shifts);
    }

    private void manageShifts(List<ShiftsDTO> shifts) {
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        List<List<ShiftsDTO>> groupedShifts = groupShiftsByWeek(shifts);

        for (List<ShiftsDTO> weekShifts : groupedShifts) {

            WeeklyShifts weeklyShifts = new WeeklyShifts(weekShifts);


            if (weeklyShifts.getLastDayOfWeek().before(Date.from(sunday.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                pastweeks.add(weeklyShifts);
            } else if (weeklyShifts.getLastDayOfWeek().after(Date.from(sunday.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                upcomingweeks.add(weeklyShifts);
            } else {
                currentWeek = weeklyShifts;
            }
        }

    }

    public void setUpShift(DayOfTheWeek day, PartOfDay partOfDay, List<Role> roles) {
        upcomingweeks.get(0).getShift(day, partOfDay).addNeededRoles(roles);
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
            this.currentWeek = new WeeklyShifts(firstDayOfWeek, id);
        } else {
            upcomingweeks.add(new WeeklyShifts(firstDayOfWeek, id));
        }
    }

    public String getShiftToString(Integer week, DayOfTheWeek day, PartOfDay part) throws Exception {
        if (week == 0) {
            return currentWeek.toString(day, part);
        } else if (week < 0) {
            week = (week * -1) - 1;
            if (week >= pastweeks.size()) {
                return null;
            }
            return pastweeks.get(week).toString(day, part);
        }
        week -= 1;
        if (week >= upcomingweeks.size()) {
            throw new Exception("No such week");
        }
        return upcomingweeks.get(week).toString(day, part);
    }

    public void deleteEmployeeFromShift(int employeeId, DayOfTheWeek day, PartOfDay partOfDay) throws Exception {
        if (upcomingweeks.get(0) != null) {
            Shift shift = upcomingweeks.get(0).getShift(day, partOfDay);
            if (shift != null)
                shift.deleteEmployee(employeeId);
            else
                throw new Exception("Shift not found");
        }
    }

    public void checkWeekPast() { //if date changes call this function
        if (currentWeek != null) {
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            if (today.after(currentWeek.getLastDayOfWeek())) { //TODO: fix this
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
        return new WeeklyShifts(nextWeekStartDate, id);
    }

    public void addEmployeeToShift(Integer employee, Role role, DayOfTheWeek day, PartOfDay part) throws Exception {
        if (employee == null) {
            throw new Exception("Employee is null");
        }
        upcomingweeks.get(0).getShift(day, part).addEmployee(employee, role);
    }

    public void isWeekExists(Integer week) throws Exception {
        if (pastweeks.isEmpty())
            throw new Exception("No past weeks");

        week = (week * -1) - 1;
        if (week > pastweeks.size())
            throw new Exception("Threre is no such week");
    }

    public void addNeededRoles(DayOfTheWeek day, PartOfDay part, List<Role> roles) {
        upcomingweeks.get(0).getShift(day, part).addNeededRoles(roles);
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


    public List<WeeklyShifts> getPastweeks() {
        return pastweeks;
    }



    public void exchangeShift(Integer id1, Integer id2, DayOfTheWeek day, PartOfDay part, Role role) throws Exception {
        try {
            upcomingweeks.get(0).getShift(day, part).exchangeShift(id1, id2, role);
        } catch (Exception e) {
            throw e;
        }
    }

    public WeeklyShifts getCurrentWeek() {
        return currentWeek;
    }

    public String getAddress() {
        return address;
    }


    private static List<List<ShiftsDTO>> groupShiftsByWeek(List<ShiftsDTO> shifts) {
        // Sort the list of shifts by date
        shifts.sort(Comparator.comparing(shift -> shift.getDate()));

        List<List<ShiftsDTO>> groupedShifts = new ArrayList<>();
        List<ShiftsDTO> currentWeek = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Date currentSunday = null;

        for (int i= 0; i< shifts.size(); i++) {
            ShiftsDTO shift = shifts.get(i);

            Date shiftDate = shift.getDate();
            calendar.setTime(shiftDate);
            // Find the Sunday of the week
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date sundayOfWeek = calendar.getTime();

            if (!sundayOfWeek.equals(currentSunday)) {
                // Start a new week
                if (!currentWeek.isEmpty()) {
                    groupedShifts.add(new ArrayList<>(currentWeek));
                }
                currentWeek.clear();
                currentSunday = sundayOfWeek;
            }
            currentWeek.add(shift);
        }

        // Add the last week if it's not empty
        if (!currentWeek.isEmpty()) {
            groupedShifts.add(currentWeek);
        }

        return groupedShifts;
    }
}
