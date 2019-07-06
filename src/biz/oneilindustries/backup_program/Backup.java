package biz.oneilindustries.backup_program;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Backup extends Thread {

    private Locations directoriesToBackup;
    private Location destination;
    private BackupLogging backupLogging;
    private Button backupButton;
    private TableView backupTable;
    private static final Logger logger = LogManager.getLogger(Backup.class);

    public Backup(Locations directoriesToBackup, Location destination, Button backupButton, TableView backupTable) {
        this.directoriesToBackup = directoriesToBackup;
        this.destination = destination;
        this.backupLogging = new BackupLogging();
        this.backupButton = backupButton;
        this.backupTable = backupTable;
    }

    public Backup() {
    }

    private void copyDirectory(File directoryToBackup, File destinationDirectory) {
        try {
            FileUtils.copyDirectoryToDirectory(directoryToBackup,destinationDirectory);
        } catch (IOException e) {
            logger.error("Error copying directory",e);
        }
    }

    private void copyFile(File fileToBackup, File destinationDirectory) {
        try {
            FileUtils.copyFileToDirectory(fileToBackup,destinationDirectory,true);
        } catch (IOException e) {
            logger.error("Error copying file",e);
        }
    }

    @Override
    public void run() {
        BackupInformation currentBackup = backupLogging.createBackup(this.directoriesToBackup,destination.getFolderPath() + "\\"+ (LocalDate.now().toString() + " - 1"));

        File dest = new File(currentBackup.getBackupPath());

        for (Location location : this.directoriesToBackup.getLocations()) {
            File file = new File(location.getFolderPath());
            if(file.isFile()) {
                this.copyFile(file,dest);
            }else {
                this.copyDirectory(file, dest);
            }
        }
        backupLogging.logBackup(currentBackup);
        Platform.runLater(() -> {
            backupButton.setDisable(false);
            backupTable.getItems().add(getLatestBackup());
        });
    }

    public BackupInformation getLatestBackup() {
        return backupLogging.getLastBackup();
    }
}
