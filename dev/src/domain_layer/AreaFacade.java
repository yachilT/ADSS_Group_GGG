package domain_layer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AreaFacade {
    private List<Area> areaList;
    public AreaFacade(){
        areaList = new LinkedList<>();
    }
    public void addArea(Area area){
        areaList.add(area);
    }
    public boolean removeArea(Area area){
        return areaList.remove(area);
    }
    public Area getArea(String areaName) throws Exception {
        if(!areaList.stream().map(Area::getAreaName).toList().contains(areaName))
            throw new Exception("Area not found.");
        return areaList.stream().filter((a) -> a.getAreaName().equals(areaName)).toList().get(0);
    }
    public void addSite(Site site, Area area) throws Exception {
        if(areaList.stream().anyMatch((a) -> a.contains(site)))
            throw new Exception("site already found in an area.");
        area.addSite(site);
        if(!areaList.contains(area))
            areaList.add(area);
    }
}
