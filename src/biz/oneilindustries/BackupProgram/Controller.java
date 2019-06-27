package biz.oneilindustries.BackupProgram;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    private Settings settings;
    private Locations foldersToBackup;

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

    private Stage stage;

    public void initialize() {
        settings = new Settings();
        foldersToBackup = settings.getFilesToBackupPath();

        folderName.setCellValueFactory(new PropertyValueFactory<>("folderName"));
        folderPath.setCellValueFactory(new PropertyValueFactory<>("folderPath"));

        updateTableList(foldersToBackup, backupFoldersTable);
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
        stage = (Stage) MainWindow.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            addLocation(selectedDirectory);
        }
    }

    @FXML
    public void addFile() {
        stage = (Stage) MainWindow.getScene().getWindow();

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
}
