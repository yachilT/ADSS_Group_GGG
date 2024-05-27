package domain_layer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class AreaFacade {
    private final Set<Area> areas;
    public AreaFacade(){
        areas = new HashSet<>();
    }
    public void addArea(Area area){
        areas.add(area);
    }
    public boolean removeArea(Area area){
        return areas.remove(area);
    }
    public Area getArea(String areaName) throws Exception {
        if(!areas.stream().map(Area::getAreaName).toList().contains(areaName))
            throw new Exception("Area not found.");
        return areas.stream().filter((a) -> a.getAreaName().equals(areaName)).toList().get(0);
    }
    public void addSite(Site site, Area area) throws Exception {
        if(areas.stream().anyMatch((a) -> a.contains(site)))
            throw new Exception("site already found in an area.");
        area.addSite(site);
        areas.add(area);
    }
}
