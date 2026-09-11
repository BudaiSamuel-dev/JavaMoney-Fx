package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MoneyFxPrg extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/penzmozgas-ablak.fxml"));
        Pane pane = loader.load();

        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("MoneyFx");
        stage.show();
    }


}
