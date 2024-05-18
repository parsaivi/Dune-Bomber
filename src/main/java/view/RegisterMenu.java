package view;


import model.App;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class RegisterMenu extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            App.loadUsers();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/RegisterMenu.fxml"));
            Pane pane = loader.load();
            Scene scene = new Scene(pane);
            RegisterMenuView registerMenuView = loader.getController();
            App.setStage(stage);
            stage.setScene(scene);
            stage.setTitle("Dune Bomber");
            stage.alwaysOnTopProperty();
            registerMenuView.setScene(scene);
            registerMenuView.setStage(stage);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}