package remoteproxy.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import remoteproxy.DisplayErrors;
import remoteproxy.ReportGeneratorEmployee;
import remoteproxy.Store;
import remoteproxy.User;
import remoteproxy.Validators.BranchValidator;
import remoteproxy.Validators.CompositeValidator;

import java.util.ArrayList;

public class AddStoreController {

    private ReportGeneratorEmployee rGE;

    @FXML
    private ComboBox managerComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button addStoreButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void addStoreButtonPressed(ActionEvent event) {
        ArrayList<String> errors;

        try {
            Store store = new Store();
            store.setName(nameTextField.getText());
            store.setAddress(addressTextField.getText());
            store.setManager(managerComboBox.getValue() != null ? (Integer) (managerComboBox.getValue()) : 0);
            store.setPhone(phoneTextField.getText());

            CompositeValidator<Store> validator = new CompositeValidator<>();
            validator.add(new BranchValidator());

            errors = validator.validate(store);
            if (errors.size() == 0) {
                if (rGE.addStore(store)) {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Sucursal agregada");
                    dialog.setHeaderText(null);
                    dialog.setContentText("La sucursal ha sido registrada correctamente.");
                    dialog.showAndWait();

                    Stage current = (Stage) (addStoreButton.getScene().getWindow());
                    current.close();
                } else {
                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error al agregar sucursal");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Ocurrió el siguiente problema: " + rGE.getMessage());
                    dialog.showAndWait();
                }
            } else {
                DisplayErrors display = new DisplayErrors("Error al agregar", "Problemas de registro",
                        "Ocurrieron los siguientes problemas:");
                display.display(errors);
            }

        } catch (NullPointerException npEx) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Campos vacíos");
            dialog.setHeaderText(null);
            dialog.setContentText("No puede haber campos vacíos en el formulario.");
            dialog.showAndWait();
        }
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        Stage stage = (Stage) (cancelButton.getScene().getWindow());
        stage.close();
    }

    @FXML
    private void initialize() {
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20)
                nameTextField.setText(oldValue);
        });

        addressTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100)
                addressTextField.setText(oldValue);
        });

        phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}"))
                phoneTextField.setText(oldValue);
        });
    }

    public void setrGE(ReportGeneratorEmployee rGE) {
        this.rGE = rGE;
        ArrayList<User> users;

        if ((users = rGE.getUsers()) == null) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error de carga");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido el siguiente error: " + rGE.getMessage());
            dialog.showAndWait();
        } else {
            for (User user : users) {
                if (user.getPosition() != null) {
                    if (user.getPosition().equals("Jefe")) {
                        managerComboBox.getItems().add(user.getId());
                    }
                } else {
                    if (user.getRole() == 'J') {
                        managerComboBox.getItems().add(user.getId());
                    }
                }
            }
        }
    }

}
