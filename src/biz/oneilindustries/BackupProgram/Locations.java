package biz.oneilindustries.BackupProgram;

import java.util.ArrayList;

public class Locations {

    private ArrayList<Location> locations;

    public Locations() {
        locations = new ArrayList<>();
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public boolean addLocation(Location location) {
        if (!this.locations.contains(location)) {
            this.locations.add(location);
            return true;
        }
        return false;
    }

    public boolean deleteLocation(Location location) {
        for (int i = 0; i < locations.size();i++) {
            if (locations.get(i).getFolderPath().equals(location.getFolderPath())) {
                locations.remove(i);
                return true;
            }
        }
        return false;
    }
}
