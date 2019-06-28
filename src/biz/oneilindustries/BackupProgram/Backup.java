package biz.oneilindustries.BackupProgram;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Backup extends Thread {

    private Locations directoriesToBackup;
    private Location destination;

    public Backup(Locations directoriesToBackup, Location destination) {
        this.directoriesToBackup = directoriesToBackup;
        this.destination = destination;
    }

    public Backup() {
    }

    public void copyDirectory(File directoryToBackup, File destinationDirectory) {
        try {
            FileUtils.copyDirectoryToDirectory(directoryToBackup,destinationDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(File fileToBackup, File destinationDirectory) {
        try {
            FileUtils.copyFileToDirectory(fileToBackup,destinationDirectory,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        File dest = new File(this.destination.getFolderPath());

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
