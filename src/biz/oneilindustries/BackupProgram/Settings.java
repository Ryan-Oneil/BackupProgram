package biz.oneilindustries.BackupProgram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Settings {

    private static Locations filesToBackupPath;
    private static Location destination;
    //private static final Logger logger = LogManager.getLogger(Settings.class);

    public Settings() {
        if (filesToBackupPath == null) {
            filesToBackupPath = new Locations();
            if (new File("Backup/locations.txt").isFile()) {
                destination = new Location();
                getSettings();
            }
        }
    }

    public Locations getFilesToBackupPath() {
        return filesToBackupPath;
    }

    public void setFilesToBackupPath(Locations locations) {
        filesToBackupPath = locations;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destinationPath) {
        destination = destinationPath;
    }

    public void saveSettings() {
        File programFileDir = new File("Backup/");
        if (!programFileDir.exists()) {
            programFileDir.mkdir();
        }
        try(BufferedWriter locationFile = new BufferedWriter(new FileWriter("Backup/locations.txt", false))   ) {
            for (Location location : filesToBackupPath.getLocations()) {
                locationFile.write(location.getFolderName() + "," + location.getFolderPath() + "\n");
            }
            if (destination != null) {
                locationFile.write("backupDest," + destination.getFolderName() + "," + destination.getFolderPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSettings() {
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("Backup/locations.txt")))) {
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
            e.printStackTrace();
        }
    }
}
