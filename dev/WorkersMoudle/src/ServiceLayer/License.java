package ServiceLayer;

public class License {
    public final float allowedWeight;

    public License(float allowedWeight) {
        this.allowedWeight = allowedWeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        License license = (License) obj;
        return license.allowedWeight == this.allowedWeight;
    }
}


