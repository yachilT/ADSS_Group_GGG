package domain_layer;

import java.util.List;
import java.util.Set;

public class Area {
    private final String areaName;
    private final Set<Site> sites;
    public Area(String areaName, Set<Site> sites){
        this.areaName = areaName;
        this.sites = sites;
    }
    public String getAreaName(){
        return areaName;
    }
    public void addSite(Site s) throws Exception {
        if(!sites.add(s))
            throw new Exception("The site is already in the area.");
    }
    public boolean contains(Site site){
        return sites.contains(site);
    }
}
