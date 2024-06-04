package service_layer;
import domain_layer.Area;
import domain_layer.Site;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AreaToSend {
    private final String areaName;
    private final Set<SiteToSend> sites;
    public AreaToSend(Area area){
        areaName = area.getAreaName();
        sites = area.getSites().stream().map(SiteToSend::new).collect(Collectors.toSet());

    }
    public String getAreaName(){
        return areaName;
    }

    public List<SiteToSend> getSites() {
        return new ArrayList<>(sites);
    }
}
