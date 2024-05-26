package domain_layer;

import java.util.List;

public class Area {
    private String areaName;
    private List<Site> sites;
    public Area(String areaName, List<Site> sites){
        this.areaName = areaName;
        this.sites = sites;
    }
    public String getAreaName(){
        return areaName;
    }
    public void addSite(Site s) throws Exception {
        if(sites.contains(s))
            sites.add(s);
        else
            throw new Exception("The site is already in the area.");
    }
    public boolean contains(Site site){
        return sites.contains(site);
    }
}
