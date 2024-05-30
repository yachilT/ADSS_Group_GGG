package service_layer;
import domain_layer.Area;
import domain_layer.Site;

import java.util.List;
import java.util.Set;

public class AreaToSend {
    private String areaName;
    private Set<SiteToSend> sites;
    public AreaToSend(Area area){
        areaName = area.getAreaName();
        for (Site s: area.getSites()) {
            sites.add(new SiteToSend(s));
        }
    }
    public String getAreaName(){
        return areaName;
    }

    public List<SiteToSend> getSites() {
        return sites.stream().toList();
    }
}
