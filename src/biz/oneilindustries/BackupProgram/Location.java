package biz.oneilindustries.BackupProgram;

import java.util.Objects;

public class Location {

    private String folderName;
    private String folderPath;

    public Location(String folderName, String folderPath) {
        this.folderName = folderName;
        this.folderPath = folderPath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
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
