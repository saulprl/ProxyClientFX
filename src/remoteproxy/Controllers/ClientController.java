package remoteproxy.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import remoteproxy.Report;
import remoteproxy.ReportGeneratorEmployee;
import remoteproxy.Store;
import remoteproxy.User;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController {

    private ReportGeneratorEmployee rGE;

    @FXML
    private TextField branchNoTextField;

    @FXML
    private Button fetchButton;

    @FXML
    private CheckBox salesCheckBox;

    @FXML
    private Button getStoresButton;

    @FXML
    private Button getSalesButton;

    @FXML
    private Button addStoreButton;

    @FXML
    private Button modStoreButton;

    @FXML
    private Button logOutButton;

    @FXML
    private TableView tableView;

    @FXML
    private void fetchButtonPressed(ActionEvent event) {
        if (!branchNoTextField.getText().isEmpty()) {
            int branch = Integer.parseInt(branchNoTextField.getText());

            if (salesCheckBox.isSelected()) {

                Report report = rGE.generateDailyReport(branch);

                if (report != null) {
                    String reportInfo = "ID: 0" + report.getId() + "\nFecha: " + report.getDate() +
                            "\nPizzas vendidas: " + report.getCount() + "\nVenta total: " + report.getSale() +
                            "\nUtilidad: " + report.getProfit();

                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Resumen de ventas");
                    dialog.setHeaderText(null);
                    dialog.setContentText(reportInfo);
                    dialog.showAndWait();
                } else {
                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error de búsqueda");
                    dialog.setHeaderText(null);
                    dialog.setContentText("No se encontró un reporte de ventas para esta sucursal.");
                    dialog.showAndWait();
                }

            } else {
                Store store = rGE.getStore(branch);

                if (store != null) {

                    String info = "ID: " + store.getId() + "\nNombre: " + store.getName() +
                            "\nDirección: " + store.getAddress() + "\nTeléfono: " + store.getPhone() + "\nGerente: " +
                            store.getManager();

                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Sucursal " + store.getId());
                    dialog.setHeaderText(null);
                    dialog.setContentText(info);
                    dialog.showAndWait();

                } else {

                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error al buscar");
                    dialog.setHeaderText(null);
                    dialog.setContentText("No fue posible encontrar la sucursal.");
                    dialog.showAndWait();

                }
            }
        } else {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Campo vacío");
            dialog.setHeaderText(null);
            dialog.setContentText("El campo de texto no puede estar vacío para ejecutar esta función.");
            dialog.showAndWait();
        }
    }

    @FXML
    private void addStoreButtonPressed(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/remoteproxy/Views/AddStoreView.fxml"));
            Parent root = fxmlLoader.load();
            AddStoreController addStore = fxmlLoader.getController();
            addStore.setrGE(rGE);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar sucursal");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(addStoreButton.getScene().getWindow());
            stage.show();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    @FXML
    private void modStoreButtonPressed(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/remoteproxy/Views/UpdateStoreView.fxml"));
            Parent root = fxmlLoader.load();
            UpdateStoreController updateStore = fxmlLoader.getController();
            updateStore.setrGE(this.rGE);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificar sucursal");
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(modStoreButton.getScene().getWindow());
            stage.show();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @FXML
    private void getStoresButtonPressed(ActionEvent event) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn idColumn = new TableColumn("ID");
        TableColumn nameColumn = new TableColumn("Nombre");
        TableColumn addressColumn = new TableColumn("Dirección");
        TableColumn phoneColumn = new TableColumn("Teléfono");
        TableColumn managerColumn = new TableColumn("Gerente");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(addressColumn);
        tableView.getColumns().add(phoneColumn);
        tableView.getColumns().add(managerColumn);

        tableView.setItems(FXCollections.observableArrayList(rGE.getStores()));
    }

    @FXML
    private void getSalesButtonPressed(ActionEvent event) {
        tableView.getItems().clear();
        tableView.getColumns().clear();

        TableColumn idColumn = new TableColumn();
        TableColumn dateColumn = new TableColumn();
        TableColumn totalColumn = new TableColumn();
        TableColumn saleColumn = new TableColumn();
        TableColumn profitColumn = new TableColumn();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<>("sale"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));

        idColumn.setText("ID");
        dateColumn.setText("Fecha");
        totalColumn.setText("Pizzas vendidas");
        saleColumn.setText("Venta total");
        profitColumn.setText("Utilidad");

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(dateColumn);
        tableView.getColumns().add(totalColumn);
        tableView.getColumns().add(saleColumn);
        tableView.getColumns().add(profitColumn);

        ArrayList<Report> reports = rGE.getReports();
        reports.add(rGE.generateDailyReport());

        tableView.setItems(FXCollections.observableArrayList(reports));
    }

    @FXML
    private void logOutButtonPressed(ActionEvent event) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Cerrar sesión");
        confirmation.setHeaderText(null);
        confirmation.setContentText("¿Seguro de que deseas cerrar sesión?");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.OK) {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/remoteproxy/Views/LoginView.fxml"));

                Parent root = fxmlLoader.load();
                LoginController login = fxmlLoader.getController();
                login.setrGE(rGE);

                Stage thisOne = (Stage) (logOutButton.getScene().getWindow());
                thisOne.close();

                stage.setScene(new Scene(root));
                stage.setResizable(false);
                stage.setTitle("Iniciar sesión");
                stage.show();
            } catch (IOException ioEx) {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error");
                dialog.setHeaderText(null);
                dialog.setContentText("Ha ocurrido un error inesperado. Excepción: " + ioEx.getMessage());
                dialog.showAndWait();
            }
        }
    }

    @FXML
    private void initialize() {
        fetchButton.setDisable(true);

        branchNoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                fetchButton.setDisable(true);
            } else {
                fetchButton.setDisable(false);
            }
        });
    }

    public void setrGE(ReportGeneratorEmployee rGE) {
        this.rGE = rGE;

        User logged = rGE.getLogged();

        if (logged.getPosition() != null) {
            if (logged.getPosition().equals("Empleado")) {
                salesCheckBox.setDisable(true);
                getSalesButton.setDisable(true);
                addStoreButton.setDisable(true);
                modStoreButton.setDisable(true);
            }
        } else {
            if (logged.getRole() == 'E') {
                salesCheckBox.setDisable(true);
                getSalesButton.setDisable(true);
                addStoreButton.setDisable(true);
                modStoreButton.setDisable(true);
            }
        }
    }

}
