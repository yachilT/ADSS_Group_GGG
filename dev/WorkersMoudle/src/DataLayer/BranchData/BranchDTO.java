package DataLayer.BranchData;

import DomainLayer.Branches.Branch;
import DomainLayer.Branches.WeeklyShifts;

import java.util.List;

public class BranchDTO {

    public final static String COLUMN_NAME_ID = "ID";
    public final static String COLUMN_NAME_NAME = "NAME";
    public final static String COLUMN_NAME_ADDRESS = "ADDRESS";

    private int id;
    private String name;
    private String address;

    private BranchDataManager branchDataManager;

    public BranchDTO(Branch branch){
        this(branch.getId(), branch.getName(), branch.getAddress());
    }

    public BranchDTO(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;

        branchDataManager = new BranchDataManager();

    }

    public void insertDTO(){
        branchDataManager.insertDTO(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


}
