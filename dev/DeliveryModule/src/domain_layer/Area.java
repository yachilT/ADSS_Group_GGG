package domain_layer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Area {
    private final String areaName;
    private final Set<Site> sites;
    public Area(String areaName, Set<Site> sites){
        this.areaName = areaName;
        this.sites = sites;
    }

    public Area(String areaName) {
        this(areaName, new HashSet<>());
    }
    public void addSite(Site s) throws Exception {
        if(!sites.add(s))
            throw new Exception("The site is already in the area.");
    }
    public boolean contains(Site site){
        return sites.contains(site);
    }

    public Set<Site> getSites() { return sites; }
    public String getAreaName() { return areaName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(areaName, area.areaName) && Objects.equals(sites, area.sites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaName, sites);
    }
}
