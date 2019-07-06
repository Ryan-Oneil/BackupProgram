package biz.oneilindustries.backup_program;

import java.io.File;
import java.util.Objects;
import org.apache.commons.io.FileUtils;

public class BackupInformation {

    private String creationDate;
    private String backupPath;
    private Locations filesBackedUp;
    private String totalBackupSize;

    public BackupInformation(String creationDate, String backupPath, Locations filesBackedUp) {
        this.creationDate = creationDate;
        this.backupPath = backupPath;
        this.filesBackedUp = filesBackedUp;
    }

    public BackupInformation(String creationDate, String backupPath, Locations filesBackedUp, String totalBackupSize) {
        this.creationDate = creationDate;
        this.backupPath = backupPath;
        this.filesBackedUp = filesBackedUp;
        this.totalBackupSize = totalBackupSize;
    }

    public BackupInformation() {
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public String getTotalBackupSize() {
        return totalBackupSize;
    }

    public Locations getFilesBackedUp() {
        return filesBackedUp;
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

    private long getSizeMBytes() {
        File file = new File(this.backupPath);
        if (file.exists() && file.isDirectory()) {
            return FileUtils.sizeOfDirectory(file) / 1000000;
        }
        return 0;
    }

    public void updateSize() {
        this.totalBackupSize = getSizeMBytes() + "MB";
    }

}
