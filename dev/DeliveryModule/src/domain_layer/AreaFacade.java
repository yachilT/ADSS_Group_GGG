package domain_layer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaFacade {

    private final AreaRepository areaRepository;

    public AreaFacade(String dbPath){
        areaRepository = new AreaRepository(dbPath);

    }

    public void loadAll() {
        this.areaRepository.loadAll();
    }
    public void addArea(Area area){
        areaRepository.add(area);
    }

    public Area getArea(String areaName) throws Exception {
        if(!areaRepository.contains(areaName))
            throw new Exception("Area not found.");
        return areaRepository.getAllAreas().stream().filter((a) -> a.getAreaName().equals(areaName)).toList().get(0);
    }
    public void addSite(String address, String contactName, String contactNumber, String areaName) throws Exception {
        areaRepository.addSite(address, contactName, contactNumber, areaName);
    }
    public List<Area> getAreas(){
        return areaRepository.getAllAreas().stream().toList();
    }
    public List<Site> getSites(){
        return areaRepository.getSites();
    }
}
