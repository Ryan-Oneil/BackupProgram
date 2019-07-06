package biz.oneilindustries.backup_program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Settings {

    private static Locations filesToBackupPath = new Locations();
    private static Location destination = new Location();
    private static final String BACKUP_PROGRAM_BASE_DIRECTORY  = "Backup/";
    private static final String BACKUP_LOCATIONS = "Backup/locations.txt";
    private static final Logger logger = LogManager.getLogger(Settings.class);

    public Settings() {
        initializeDirectory();
        if (new File(BACKUP_LOCATIONS).isFile()) {
            getSettings();
        }
    }

    private void initializeDirectory() {
        File programFileDir = new File(BACKUP_PROGRAM_BASE_DIRECTORY);

        if (!programFileDir.exists()) {
            programFileDir.mkdir();
        }
    }

    public Locations getFilesToBackupPath() {
        return filesToBackupPath;
    }

    public static void setFilesToBackupPath(Locations locations) {
        filesToBackupPath = locations;
    }

    public Location getDestination() {
        return destination;
    }

    public static void setDestination(Location destinationPath) {
        destination = destinationPath;
    }

    public static void saveSettings() {
        File programFileDir = new File(BACKUP_PROGRAM_BASE_DIRECTORY);
        if (!programFileDir.exists()) {
            programFileDir.mkdir();
        }
        try(BufferedWriter locationFile = new BufferedWriter(new FileWriter(BACKUP_LOCATIONS, false))   ) {
            for (Location location : filesToBackupPath.getLocations()) {
                locationFile.write(location.getFolderName() + "," + location.getFolderPath() + "\n");
            }
            if (destination != null) {
                locationFile.write("backupDest," + destination.getFolderName() + "," + destination.getFolderPath());
            }
        } catch (IOException e) {
            logger.error("Error Saving Settings", e);
        }
    }

    private void getSettings() {
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(BACKUP_LOCATIONS)))) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String fileName = scanner.next();
                scanner.skip(scanner.delimiter());

                if (!fileName.equals("backupDest")) {
                    String filePath = scanner.nextLine();
                    filesToBackupPath.addLocation(new Location(fileName, filePath));
                }else {
                    fileName = scanner.next();
                    scanner.skip(scanner.delimiter());
                    String filePath = scanner.nextLine();
                    destination = new Location(fileName, filePath);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error getting Settings", e);
        }
    }
}
