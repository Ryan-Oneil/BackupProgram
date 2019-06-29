package biz.oneilindustries.BackupProgram;

import java.util.Objects;

public class BackupInformation {

    private String creationDate;
    private String backupPath;
    private Locations filesBackedUp;

    public BackupInformation(String creationDate, String backupPath, Locations filesBackedUp) {
        this.creationDate = creationDate;
        this.backupPath = backupPath;
        this.filesBackedUp = filesBackedUp;
    }

    public BackupInformation() {
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public Locations getFilesBackedUp() {
        return filesBackedUp;
    }

    public void setFilesBackedUp(Locations filesBackedUp) {
        this.filesBackedUp = filesBackedUp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BackupInformation that = (BackupInformation) o;
        return getBackupPath().equals(that.getBackupPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBackupPath());
    }

    @Override
    public String toString() {
        return "BackupInformation{" +
            "creationDate='" + creationDate + '\'' +
            ", backupPath='" + backupPath + '\'' +
            ", filesBackedUp=" + filesBackedUp +
            '}';
    }
}
