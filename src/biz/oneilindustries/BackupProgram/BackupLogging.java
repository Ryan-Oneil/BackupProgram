package biz.oneilindustries.BackupProgram;

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

public class BackupLogging {

    private File loggedBackups;
    private static ArrayList<BackupInformation> previousBackups;

    public BackupLogging() {
        initializeFile();
        if (previousBackups == null) {
            previousBackups = new ArrayList<>();
            populateArray();
        }
    }

    public ArrayList<BackupInformation> getPreviousBackups() {
        return previousBackups;
    }

    public void setPreviousBackups(ArrayList<BackupInformation> newPreviousBackups) {
        previousBackups = newPreviousBackups;
    }

    private void initializeFile() {
        File programFileDir = new File("Backup/");
        if (!programFileDir.exists()) {
            programFileDir.mkdir();
        }
        loggedBackups = new File(programFileDir.getAbsolutePath() + "previousBackups.txt");
    }

    public BackupInformation logBackup(Locations filesBackedUp, String backupDest) {
        BackupInformation backupInformation = new BackupInformation(LocalDate.now().toString(),backupDest,filesBackedUp);
        while (previousBackups.contains(backupInformation)) {
            int length = backupInformation.getBackupPath().length();
            String newName = backupInformation.getBackupPath().substring(0, length - 1);
            backupInformation.setBackupPath(newName + (Character.getNumericValue(backupInformation.getBackupPath().charAt(length - 1)) + 1));
        }
        previousBackups.add(backupInformation);
        try(BufferedWriter locationFile = new BufferedWriter(new FileWriter("Backup/previousBackups.txt",true))) {
            locationFile.write(  backupInformation.getBackupPath() + "," + backupInformation.getCreationDate() + "\n");
            for (Location location : filesBackedUp.getLocations()) {
                locationFile.write(location.getFolderName() + "," + location.getFolderPath() + "\n");
            }
            locationFile.write("EOL," + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return backupInformation;
    }

    public void populateArray() {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("Backup/previousBackups.txt")))) {
            scanner.useDelimiter(",");
            while(scanner.hasNextLine()) {
                String backupPath = scanner.next();
                scanner.skip(scanner.delimiter());
                String creationDate = scanner.nextLine();

                Locations locations = new Locations();
                String folderName;
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
                previousBackups.add(new BackupInformation(creationDate, backupPath, locations));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
