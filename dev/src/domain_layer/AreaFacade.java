package domain_layer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AreaFacade {
    private final Set<Area> areas;
    private AreaRepository areaRepository;
    public AreaFacade(){
        areas = new HashSet<>();
        areaRepository = new AreaRepository();
    }

    public void LoadAll() {
        List<Area> areas = areaRepository.getAllAreas();
        areas.forEach(this::addArea);
    }
    public void addArea(Area area){
        areaRepository.add(area);
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
    public List<Area> getAreas(){
        return areas.stream().toList();
    }
    public List<Site> getSites(){
        Set<Site> sites = new HashSet<>();
        for(Area a: areas)
            sites.addAll(a.getSites());
        return sites.stream().toList();
    }
}
