package biz.oneilindustries.backup_program;

import java.util.Objects;

public class Location {

    private String folderName;
    private String folderPath;

    public Location(String folderName, String folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }

    public Location() {
    }

    public String getFolderName() {
        return folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(getFolderName(), location.getFolderName()) &&
            Objects.equals(getFolderPath(), location.getFolderPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFolderName(), getFolderPath());
    }

    @Override
    public String toString() {
        return "Location{" +
            "folderName='" + folderName + '\'' +
            ", folderPath='" + folderPath + '\'' +
            '}';
    }
}
