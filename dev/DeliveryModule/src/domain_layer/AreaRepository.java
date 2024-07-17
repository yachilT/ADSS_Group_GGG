package domain_layer;

import dataAccess_layer.AreaDAO;
import dataAccess_layer.SiteDAO;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class AreaRepository {
    private final Set<Area> areas;
    private final AreaDAO areaDAO;
    private final SiteDAO siteDAO;

    public AreaRepository(String dbPath) {
        this.areaDAO = new AreaDAO(dbPath);
        this.siteDAO = new SiteDAO(dbPath);
        this.areas = new HashSet<>();
    }

    public Area getArea(Area a) {

        List<Site> sites = siteDAO.getSitesByArea(a.getAreaName());
        sites.forEach(s -> {
            try{ a.addSite(s); } catch (Exception ignored) {}
        }
        );

        return a;
    }

    public List<Area> getAllAreas() {
        List<Area> names = areaDAO.readAll();
        return names.stream().map(this::getArea).toList();
    }
    public void addSite(String address, String contactName, String contactNumber, String areaName) throws Exception {
        loadAll();
        Area area = areas.stream().filter(a -> a.getAreaName().equals(areaName)).findFirst().orElseThrow(() -> new NoSuchElementException("Area not found."));
        Site site = new Site(address, contactName, contactNumber);
        if (area.contains(site))
            throw new Exception("The site is already in the area.");
        siteDAO.create(site, area.getAreaName());
        area.addSite(site);
        areas.add(area);
    }

    public void add(Area area) {
        areas.add(area);

        areaDAO.create(area);
        area.getSites().forEach(site -> siteDAO.create(site, area.getAreaName()));
    }
    public boolean contains(String areaName){
        loadAll();
        return areas.stream().map(Area::getAreaName).toList().contains(areaName);
    }

    public void loadAll(){
        this.areas.addAll(getAllAreas());
    }
    public List<Site> getSites(){
        Set<Site> sites = new HashSet<>();
        for(Area a: areas)
            sites.addAll(a.getSites());
        return sites.stream().toList();
    }
}
