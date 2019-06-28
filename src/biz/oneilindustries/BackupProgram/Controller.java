package biz.oneilindustries.BackupProgram;

import java.io.File;
import javafx.fxml.FXML;
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

    private Stage stage;

    public void initialize() {
        settings = new Settings();
        foldersToBackup = settings.getFilesToBackupPath();
        backupDestPath = settings.getDestination();

        folderName.setCellValueFactory(new PropertyValueFactory<>("folderName"));
        folderPath.setCellValueFactory(new PropertyValueFactory<>("folderPath"));

        updateTableList(foldersToBackup, backupFoldersTable);
    }

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void addLocation(File fileToBackup) {
        foldersToBackup.addLocation(new Location(fileToBackup.getName(),fileToBackup.getAbsolutePath()));
        updateTableList(foldersToBackup, backupFoldersTable);
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

    public void updateTableList(Locations locations, TableView table) {
        table.getItems().clear();
        for (Location path: locations.getLocations()) {
            table.getItems().add(path);
        }
        settings.setFilesToBackupPath(locations);
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
        //stage = (Stage) MainWindow.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            addLocation(selectedFile);
        }
    }

    @FXML
    public void deleteFolder() {
        foldersToBackup.deleteLocation(backupFoldersTable.getSelectionModel().getSelectedItem());
        updateTableList(foldersToBackup, backupFoldersTable);
    }

    @FXML
    public void doBackup() {
        Thread backup = new Backup(settings.getFilesToBackupPath(),settings.getDestination());
        backup.start();
    }

    @FXML
    public void chooseDirectoryDest() {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File directory = directoryChooser.showDialog(stage);

        if (directory != null) {
            backupDestPath = new Location(directory.getName(),directory.getAbsolutePath());
            backupDest.setText(backupDestPath.getFolderPath());
            settings.setDestination(backupDestPath);
        }
    }
}
