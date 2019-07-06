package biz.oneilindustries.backup_program;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    private Settings settings;
    private Locations foldersToBackup;
    private Location backupDestPath;

    @FXML
    private AnchorPane MainWindow;

    @FXML
    private AnchorPane homePage;

    @FXML
    private AnchorPane settingsPage;

    @FXML
    private TableColumn folderName;

    @FXML
    private TableColumn folderPath;

    @FXML
    private TableView<Location> backupFoldersTable;

    @FXML
    private TextField backupDest;

    @FXML
    private TableView previousBackupsTable;

    @FXML
    private TableColumn previousBackupDate;

    @FXML
    private TableColumn previousBackupSize;

    @FXML
    private TableColumn previousBackupPath;

    @FXML
    private Button backupButton;

    private Stage stage;

    private BackupLogging backupLogging;

    public void initialize() {
        settings = new Settings();
        backupLogging = new BackupLogging();

        foldersToBackup = settings.getFilesToBackupPath();
        backupDestPath = settings.getDestination();
        backupDest.setText(backupDestPath.getFolderPath());

        folderName.setCellValueFactory(new PropertyValueFactory<>("folderName"));
        folderPath.setCellValueFactory(new PropertyValueFactory<>("folderPath"));

        previousBackupDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        previousBackupSize.setCellValueFactory(new PropertyValueFactory<>("totalBackupSize"));
        previousBackupPath.setCellValueFactory(new PropertyValueFactory<>("backupPath"));

        updateTableList();
        updatePreviousBackupTableList();
    }

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void addLocation(File fileToBackup) {
        foldersToBackup.addLocation(new Location(fileToBackup.getName(),fileToBackup.getAbsolutePath()));
        updateTableList();
    }

    @FXML
    public void openHomePage() {
        if (!homePage.isVisible()) {
            settingsPage.setVisible(false);
            homePage.setVisible(true);
        }
    }

    @FXML
    public void openSettingsPage() {
        if (!settingsPage.isVisible()) {
            homePage.setVisible(false);
            settingsPage.setVisible(true);
        }
    }

    private void updateTableList() {
        backupFoldersTable.getItems().clear();
        for (Location path: foldersToBackup.getLocations()) {
            backupFoldersTable.getItems().add(path);
        }
        settings.setFilesToBackupPath(foldersToBackup);
    }

    private void updatePreviousBackupTableList() {
        for(BackupInformation backupInformation : backupLogging.getPreviousBackups()) {
            previousBackupsTable.getItems().add(backupInformation);
        }
    }

    @FXML
    public void addFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            addLocation(selectedDirectory);
        }
    }

    @FXML
    public void addFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            addLocation(selectedFile);
        }
    }

    @FXML
    public void deleteFolder() {
        foldersToBackup.deleteLocation(backupFoldersTable.getSelectionModel().getSelectedItem());
        updateTableList();
    }

    @FXML
    public void doBackup() {
        backupButton.setDisable(true);
        Thread backup = new Backup(settings.getFilesToBackupPath(),settings.getDestination(),backupButton,previousBackupsTable);
        backup.start();
    }

    @FXML
    public void chooseDirectoryDest() {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File directory = directoryChooser.showDialog(stage);

        if (directory != null) {
            backupDestPath = new Location(directory.getName(),directory.getAbsolutePath());
            backupDest.setText(backupDestPath.getFolderPath());
            Settings.setDestination(backupDestPath);
        }
    }
}
