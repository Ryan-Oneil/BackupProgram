package biz.oneilindustries.backup_program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupLogging {

    private static ArrayList<BackupInformation> previousBackups;
    private static boolean outOfDateLog = false;
    private static final String PREVIOUS_BACKUPS_TXT = "Backup/previousBackups.txt";
    private static final Logger logger = LogManager.getLogger(BackupLogging.class);

    public BackupLogging() {
        initializeFile();
        if (previousBackups == null) {
            previousBackups = new ArrayList<>();
            populateArray();
        }

    }

    public static boolean isOutOfDateLog() {
        return outOfDateLog;
    }

    public ArrayList<BackupInformation> getPreviousBackups() {
        return previousBackups;
    }

    private void initializeFile() {
        File programFileDir = new File("Backup/");
        File previousBackupsTextFile = new File(PREVIOUS_BACKUPS_TXT);

        if (!programFileDir.exists()) {
            programFileDir.mkdir();
        }
        if (!previousBackupsTextFile.isFile()) {
            try {
                previousBackupsTextFile.createNewFile();
            } catch (IOException e) {
                logger.error("Error creating previous backup text file", e);
            }
        }
    }

    //Creates backup location and changes the path number if it already exists
    public BackupInformation createBackup(Locations filesBackedUp, String backupDest) {
        BackupInformation backupInformation = new BackupInformation(LocalDate.now().toString(),backupDest,filesBackedUp);

        while (previousBackups.contains(backupInformation)) {
            int length = backupInformation.getBackupPath().length();
            String newName = backupInformation.getBackupPath().substring(0, length - 1);

            backupInformation.setBackupPath(newName + (Character.getNumericValue(backupInformation.getBackupPath().charAt(length - 1)) + 1));
        }
        previousBackups.add(backupInformation);
        return backupInformation;
    }

    public void logBackup(BackupInformation backupInformation) {
        backupInformation.updateSize();
        try(BufferedWriter locationFile = new BufferedWriter(new FileWriter(PREVIOUS_BACKUPS_TXT,true))) {
            writeToFile(locationFile, backupInformation);
        } catch (IOException e) {
            logger.error("Error writing backup log", e);
        }
    }

    public void populateArray() {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(PREVIOUS_BACKUPS_TXT)))) {
            scanner.useDelimiter(",");
            while(scanner.hasNextLine()) {
                //Getting BackupInformation object data
                String backupPath = scanner.next();
                String creationDate = scanner.next();
                scanner.skip(scanner.delimiter());
                String totalSize = scanner.nextLine();

                Locations locations = new Locations();
                String folderName;
                //Getting Location object data
                while (scanner.hasNextLine()) {
                    folderName = scanner.next();
                    if (folderName.trim().equals("EOL")) {
                        scanner.nextLine();
                        break;
                    }
                    scanner.skip(scanner.delimiter());
                    String folderPath = scanner.nextLine();
                    locations.addLocation(new Location(folderName,folderPath));
                }
                BackupInformation newBackup = new BackupInformation(creationDate, backupPath, locations,totalSize);
                //Checks to ensure the backup still exists if not we dump it
                if (new File(newBackup.getBackupPath()).isDirectory()) {
                    previousBackups.add(newBackup);
                } else {
                    outOfDateLog = true;
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error getting previous backups from file", e);
        }
    }

    public BackupInformation getLastBackup() {
        int lastIndex = previousBackups.size() - 1;
        return previousBackups.get(lastIndex);
    }

    //Overwrites the whole file instead of appending
    public void updateLog() {
        try(BufferedWriter locationFile = new BufferedWriter(new FileWriter(PREVIOUS_BACKUPS_TXT,false))) {

            for (BackupInformation backupInformation : previousBackups) {
                writeToFile(locationFile, backupInformation);
            }
        } catch (IOException e) {
            logger.error("Error updating previous backup logs on program exit", e);
        }
    }

    private void writeToFile(BufferedWriter locationFile, BackupInformation backupInformation) throws IOException {
        locationFile.write(backupInformation.getBackupPath() + "," + backupInformation.getCreationDate() + "," + backupInformation.getTotalBackupSize()
                + "\n");

        for (Location location : backupInformation.getFilesBackedUp().getLocations()) {
            locationFile.write(location.getFolderName() + "," + location.getFolderPath() + "\n");
        }

        locationFile.write("EOL," + "\n");
    }
}
