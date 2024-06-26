package domain_layer;

import dataAccess_layer.AreaDAO;
import dataAccess_layer.SiteDAO;

import java.util.List;

public class AreaRepository {
    private AreaDAO areaDAO;
    private SiteDAO siteDAO;

    public AreaRepository() {
        this.areaDAO = new AreaDAO();
        this.siteDAO = new SiteDAO();
    }

    public Area getArea(String name) {
        Area area = areaDAO.read(name);
        List<Site> sites = siteDAO.getSitesByArea(name);
        sites.forEach(s -> {
            try{ area.addSite(s); } catch (Exception ignored) {}
        }
        );
        return area;
    }

    public List<Area> getAllAreas() {
        List<String> names = areaDAO.readAllNames();
        return names.stream().map(this::getArea).toList();
    }


    public void add(Area area) {
        areaDAO.create(area);
        area.getSites().forEach(site -> siteDAO.create(site, area.getAreaName()));
    }
}
