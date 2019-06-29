package biz.oneilindustries.BackupProgram;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import org.apache.commons.io.FileUtils;

public class Backup extends Thread {

    private Locations directoriesToBackup;
    private Location destination;
    private BackupLogging backupLogging;

    public Backup(Locations directoriesToBackup, Location destination) {
        this.directoriesToBackup = directoriesToBackup;
        this.destination = destination;
        this.backupLogging = new BackupLogging();
    }

    public Backup() {
    }

    private void copyDirectory(File directoryToBackup, File destinationDirectory) {
        try {
            FileUtils.copyDirectoryToDirectory(directoryToBackup,destinationDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile(File fileToBackup, File destinationDirectory) {
        try {
            FileUtils.copyFileToDirectory(fileToBackup,destinationDirectory,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BackupInformation currentBackup = backupLogging.logBackup(this.directoriesToBackup,destination.getFolderPath() + "/"+ (LocalDate.now().toString() + " - 1"));

        File dest = new File(currentBackup.getBackupPath());

        for (Location location : this.directoriesToBackup.getLocations()) {
            File file = new File(location.getFolderPath());
            if(file.isFile()) {
                this.copyFile(file,dest);
            }else {
                this.copyDirectory(file, dest);
            }
        }
    }
}
