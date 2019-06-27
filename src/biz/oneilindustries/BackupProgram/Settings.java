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
    private Locations destination;
    //private static final Logger logger = LogManager.getLogger(Settings.class);

    public Settings() {
        if (filesToBackupPath == null) {
            filesToBackupPath = new Locations();
            System.out.println("Creating new settings object");
            if (new File("locations.txt").isFile()) {
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

    public Locations getDestination() {
        return destination;
    }

    public void setDestination(Locations destination) {
        this.destination = destination;
    }

    public Boolean saveSettings() {
        try( BufferedWriter locationFile = new BufferedWriter(new FileWriter("locations.txt", false))   ) {
            for (Location location : filesToBackupPath.getLocations()) {
                locationFile.write(location.getFolderName() + "," + location.getFolderPath() + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getSettings() {
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("locations.txt")))) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String fileName = scanner.next();
                scanner.skip(scanner.delimiter());
                String filePath = scanner.nextLine();
                System.out.println(fileName + " " + filePath);
                filesToBackupPath.addLocation(new Location(fileName,filePath));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
