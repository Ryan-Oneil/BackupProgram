package biz.oneilindustries.backup_program;

import java.util.ArrayList;

public class Locations {

    private ArrayList<Location> locations;

    public Locations() {
        locations = new ArrayList<>();
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        if (!this.locations.contains(location)) {
            this.locations.add(location);
        }
    }

    public void deleteLocation(Location location) {
        for (int i = 0; i < locations.size();i++) {
            if (locations.get(i).getFolderPath().equals(location.getFolderPath())) {
                locations.remove(i);
            }
        }
    }
}
