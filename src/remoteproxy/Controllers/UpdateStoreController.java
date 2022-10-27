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

public class UpdateStoreController {

    private ReportGeneratorEmployee rGE;

    @FXML
    private ComboBox idComboBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox managerComboBox;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void updateButtonPressed(ActionEvent event) {
        if (updateButton.getText().equals("Modificar")) {
            setDisable(false);
            idComboBox.setDisable(true);

            nameTextField.setText(nameTextField.getPromptText());
            addressTextField.setText(addressTextField.getPromptText());
            phoneTextField.setText(phoneTextField.getPromptText());
            managerComboBox.getSelectionModel().select(managerComboBox.getPromptText());

            updateButton.setText("Guardar");
        } else {
            Store store = new Store();
            store.setId(Integer.parseInt(idComboBox.getValue().toString()));
            store.setName(nameTextField.getText());
            store.setAddress(addressTextField.getText());
            store.setPhone(phoneTextField.getText());
            store.setManager(managerComboBox.getValue() != null ?
                    Integer.parseInt(managerComboBox.getValue().toString()) : 0);

            CompositeValidator<Store> validator = new CompositeValidator<>();
            validator.add(new BranchValidator());

            ArrayList<String> errors = validator.validate(store);
            if (errors.size() == 0) {
                if (rGE.updateStore(store)) {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Sucursal actualizada");
                    dialog.setHeaderText(null);
                    dialog.setContentText("La información de la sucursal ha sido actualizada correctamente.");
                    dialog.showAndWait();

                    Stage stage = (Stage) (updateButton.getScene().getWindow());
                    stage.close();
                } else {
                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error al actualizar");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Ocurrió el siguiente problema: " + rGE.getMessage());
                    dialog.showAndWait();
                }
                updateButton.setText("Modificar");
            } else {
                DisplayErrors display = new DisplayErrors("Error al actualizar",
                        "Problemas de actualización", "Ocurrieron los siguientes problemas:");
                display.display(errors);
            }

        }
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        if (updateButton.getText().equals("Modificar")) {
            Stage stage = (Stage) (cancelButton.getScene().getWindow());
            stage.close();
        } else {
            setDisable(true);
            idComboBox.setDisable(false);
            updateButton.setText("Modificar");

            nameTextField.clear();
            phoneTextField.clear();
            addressTextField.clear();
            managerComboBox.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void initialize() {
        setDisable(true);
        updateButton.setDisable(true);

        idComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {
               Store store = rGE.getStore((Integer) newValue);

               nameTextField.setPromptText(store.getName());
               addressTextField.setPromptText(store.getAddress());
               phoneTextField.setPromptText(store.getPhone());
               managerComboBox.setPromptText(Integer.toString(store.getManager()));
               updateButton.setDisable(false);
           }
        });

        phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}")) {
                phoneTextField.setText(oldValue);
            }
        });
    }

    private void setDisable(boolean disable) {
        nameTextField.setDisable(disable);
        addressTextField.setDisable(disable);
        phoneTextField.setDisable(disable);
        managerComboBox.setDisable(disable);
    }

    public void setrGE(ReportGeneratorEmployee rGE) {
        this.rGE = rGE;
        ArrayList<User> users;
        ArrayList<Store> stores;

        if ((users = this.rGE.getUsers()) == null) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error de carga");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido el siguiente error: " + this.rGE.getMessage());
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

        if ((stores = this.rGE.getStores()) == null) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error de carga");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido el siguiente error: " + this.rGE.getMessage());
            dialog.showAndWait();
        } else {
            for (Store store : stores) {
                idComboBox.getItems().add(store.getId());
            }
        }
    }

}
