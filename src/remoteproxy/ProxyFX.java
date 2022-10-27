package remoteproxy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import remoteproxy.Controllers.LoginController;

import java.io.IOException;

public class ProxyFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/LoginView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Inicio de sesión");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();

        LoginController login = fxmlLoader.getController();
        ReportGeneratorEmployee rGE = new ReportGeneratorEmployee();
        login.setrGE(rGE);
    }
}
