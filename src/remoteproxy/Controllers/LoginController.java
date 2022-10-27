package remoteproxy.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import remoteproxy.ProxyFX;
import remoteproxy.ReportGeneratorEmployee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LoginController {

    private ReportGeneratorEmployee rGE;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView imageView;

    @FXML
    private void loginButtonPressed(ActionEvent event) {
        if (userTextField.getText().isEmpty() || passField.getText().isEmpty()) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Parámetros de inicio de sesión");
            dialog.setHeaderText(null);
            dialog.setContentText("Debes llenar todos los campos antes de continuar.");
            dialog.showAndWait();
        } else {
            String username = userTextField.getText();
            String password = passField.getText();

            if (rGE.login(username, password)) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/remoteproxy/Views/ClientView.fxml"));
                    Parent root = fxmlLoader.load();

                    ClientController client = fxmlLoader.getController();
                    client.setrGE(rGE);

                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.setTitle("Cliente");
                    stage.setOnCloseRequest(closeEvent -> Platform.exit());
                    stage.show();

                    Stage thisStage = (Stage) (loginButton.getScene().getWindow());
                    thisStage.setOnCloseRequest(null);
                    thisStage.close();
                } catch (IOException ioEx) {
                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Ha ocurrido un error inesperado al ejecutar la interfaz.");
                    dialog.showAndWait();
                }
            } else {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error de inicio de sesión");
                dialog.setHeaderText(null);
                dialog.setContentText(rGE.getMessage());
                dialog.showAndWait();
            }
        }
    }

    @FXML
    private void registerButtonPressed(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/remoteproxy/Views/RegisterView.fxml"));

            Parent root = fxmlLoader.load();
            RegisterController register = fxmlLoader.getController();
            register.setrGE(rGE);

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Registro");
            stage.show();
        } catch (IOException ioEx) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido un error inesperado. Excepción: " + ioEx.getMessage());
            dialog.showAndWait();
        }
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void initialize() {
        loginButton.setDisable(true);

        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty())
                loginButton.setDisable(true);
            else
                loginButton.setDisable(false);

            if (newValue.length() > 15)
                userTextField.setText(oldValue);
        });

        passField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20)
                passField.setText(oldValue);
        });
    }

    public void setrGE(ReportGeneratorEmployee rGE) {
        this.rGE = rGE;

        ArrayList<String> engines = new ArrayList<>();
        engines.add("PostgreSQL");
        engines.add("MySQL");
        engines.add("Microsoft SQL Server");
        engines.add("Remote MySQL");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("PostgreSQL", engines);
        dialog.setTitle("Motor de base de datos");
        dialog.setHeaderText("Escoge el motor de base de datos");
        dialog.setContentText("Motor:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            switch (result.get()) {
                case "PostgreSQL":
                    rGE.setDatabaseEngine(1);
                    break;
                case "MySQL":
                    rGE.setDatabaseEngine(2);
                    break;
                case "Microsoft SQL Server":
                    rGE.setDatabaseEngine(3);
                    break;
                case "Remote MySQL":
                    rGE.setDatabaseEngine(4);
            }
        } else {
            Platform.exit();
        }
    }

}
