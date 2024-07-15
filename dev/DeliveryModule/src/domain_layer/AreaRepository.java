package domain_layer;

import dataAccess_layer.AreaDAO;
import dataAccess_layer.SiteDAO;

import java.util.List;

public class AreaRepository {
    private AreaDAO areaDAO;
    private SiteDAO siteDAO;

    public AreaRepository(String dbPath) {
        this.areaDAO = new AreaDAO(dbPath);
        this.siteDAO = new SiteDAO(dbPath);
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


    public void add(Area area) {
        areaDAO.create(area);
        area.getSites().forEach(site -> siteDAO.create(site, area.getAreaName()));
    }
}
