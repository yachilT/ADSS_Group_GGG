package DataLayer.BranchData;

public class ShiftsDTO {
    public ShiftsDTO() {
    }

    private int BID;
    private int EID;
    private String date;
    private int partOfDay;

    public ShiftsDTO(int BID, int EID, String date, int partOfDay) {
        this.BID = BID;
        this.EID = EID;
        this.date = date;
        this.partOfDay = partOfDay;
    }

    public int getBID() {
        return BID;
    }

    public int getEID() {
        return EID;
    }

    public String getDate() {
        return date;
    }

    public int getPartOfDay() {
        return partOfDay;
    }

    public void setBID(int BID) {
        this.BID = BID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPartOfDay(int partOfDay) {
        this.partOfDay = partOfDay;
    }

}
