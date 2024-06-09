package UI.ManegerUI.PastShifts;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import ServiceLayer.Response;
import ServiceLayer.ServiceManager;
import UI.Window;

public class ViewPastShiftsWindow extends Window {
    private Integer branchId;
    public ViewPastShiftsWindow(ServiceManager serviceManager,Integer branchId){
        super(serviceManager);
        this.branchId = branchId;
    }

    @Override
    public void run() {
        boolean exit = false;
        Integer week = 0;

        while (!exit) {
            chooseOptions();

            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.println("Morning or Evening? (M/E)");
                    String partOfDay = scanner.nextLine();
                    if(partOfDay.equals("M"))
                        PrintShift(DayOfTheWeek.Sunday, PartOfDay.Morning);
                    else if(partOfDay.equals("E"))
                        PrintShift(DayOfTheWeek.Sunday, PartOfDay.Evening);
                    else
                        System.out.println("Invalid input");
                }
                case "2" -> {
                    System.out.println("Morning or Evening? (M/E)");
                    String partOfDay = scanner.nextLine();
                    if(partOfDay.equals("M"))
                        PrintShift(DayOfTheWeek.Monday, PartOfDay.Morning);
                    else if(partOfDay.equals("E"))
                        PrintShift(DayOfTheWeek.Monday, PartOfDay.Evening);
                    else
                        System.out.println("Invalid input");
                }
                case "3" -> {
                    System.out.println("Morning or Evening? (M/E)");
                    String partOfDay = scanner.nextLine();
                    if(partOfDay.equals("M"))
                        PrintShift(DayOfTheWeek.Tuesday, PartOfDay.Morning);
                    else if(partOfDay.equals("E"))
                        PrintShift(DayOfTheWeek.Tuesday, PartOfDay.Evening);
                    else
                        System.out.println("Invalid input");
                }
                case "4" -> {
                    System.out.println("Morning or Evening? (M/E)");
                    String partOfDay = scanner.nextLine();
                    if(partOfDay.equals("M"))
                        PrintShift(DayOfTheWeek.Wednesday, PartOfDay.Morning);
                    else if(partOfDay.equals("E"))
                        PrintShift(DayOfTheWeek.Wednesday, PartOfDay.Evening);
                    else
                        System.out.println("Invalid input");
                }
                case "5" -> {
                    System.out.println("Morning or Evening? (M/E)");
                    String partOfDay = scanner.nextLine();
                    if(partOfDay.equals("M"))
                        PrintShift(DayOfTheWeek.Thursday, PartOfDay.Morning);
                    else if(partOfDay.equals("E"))
                        PrintShift(DayOfTheWeek.Thursday, PartOfDay.Evening);
                    else
                        System.out.println("Invalid input");
                }
                case "6" -> {
                    System.out.println("Morning or Evening? (M/E)");
                    String partOfDay = scanner.nextLine();
                    if(partOfDay.equals("M"))
                        PrintShift(DayOfTheWeek.Friday, PartOfDay.Morning);
                    else if(partOfDay.equals("E"))
                        PrintShift(DayOfTheWeek.Friday, PartOfDay.Evening);
                    else
                        System.out.println("Invalid input");
                }
                case "7" -> {
                    week += 1;
                    System.out.println();
                    System.out.println("Forwarded to next week!");
                    if(week == 0)
                        System.out.println("You are viewing the current week shifts");
                    else if (week > 0){
                        System.out.println("can't view future week shifts");
                        week = 0;
                    }
                    else
                        System.out.println("You are viewing the week shifts from the past");
                }
                case "8" -> {
                    week -= 1;
                    System.out.println();
                    System.out.println("Backwarded to last week!");
                    if(week == 0)
                        System.out.println("You are viewing the current week shifts");
                    else
                        System.out.println("You are viewing the week shifts from the past");
                }
                case "9" -> exit = true;
            }
        }
        System.out.println("Finished viewing shifts");
    }

    private void PrintShift(DayOfTheWeek day, PartOfDay partOfDay){
        Response response = serviceManager.getBranchManagerService().getShift(branchId,0, day, partOfDay);
        System.out.println();
        if(response.ErrorOccured()){
            System.out.println(response.GetErrorMessage());
        }else{
            System.out.println((String) response.GetReturnValue());
        }
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Sunday");
        System.out.println("2. Monday");
        System.out.println("3. Tuesday");
        System.out.println("4. Wednesday");
        System.out.println("5. Thursday");
        System.out.println("6. Friday");
        System.out.println("7. View next week shifts");
        System.out.println("8. View past week shifts");
        System.out.println("9. Exit");

    }

    public Window getNextWindow() {
        return null;
    }
}
