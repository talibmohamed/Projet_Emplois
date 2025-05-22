package org.example.projet_emplois;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.projet_emplois.controller.MainController;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projet_emplois/views/main.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Emploi du Temps");
        stage.setScene(scene);

        stage.setMinWidth(800);
        stage.setMinHeight(600);

        stage.show();

        MainController controller = loader.getController();
        controller.loadDashboard("login");
    }

    public static void main(String[] args) {
        launch();
    }
}
