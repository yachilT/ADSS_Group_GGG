package service_layer;

import domain_layer.Area;
import domain_layer.AreaFacade;
import domain_layer.Site;

import java.util.LinkedList;
import java.util.List;

public class AreaService {
    private final AreaFacade areaFacade;
    public AreaService(AreaFacade areaFacade){
        this.areaFacade = areaFacade;
    }
    public List<AreaToSend> getAreas(){
        List<AreaToSend> areas = new LinkedList<>();
        for(Area a: areaFacade.getAreas()){
            areas.add(new AreaToSend(a));
        }
        return areas;
    }
    public List<SiteToSend> getSites(){
        List<SiteToSend> sites = new LinkedList<>();
        for(Site s: areaFacade.getSites())
            sites.add(new SiteToSend(s));
        return sites;

    }

    
}
