package biz.oneilindustries.BackupProgram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("BackupProgram.fxml"));
        primaryStage.setTitle("Backup Program");
        primaryStage.setScene(new Scene(root, 600, 607));
        primaryStage.show();
    }

    @Override
    public void stop(){
        Settings settings = new Settings();
        settings.saveSettings();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
