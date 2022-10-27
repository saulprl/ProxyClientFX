package remoteproxy.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import remoteproxy.DisplayErrors;
import remoteproxy.ReportGeneratorEmployee;
import remoteproxy.Store;
import remoteproxy.User;
import remoteproxy.Validators.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegisterController {

    private ReportGeneratorEmployee rGE;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private ComboBox positionComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox genderComboBox;

    @FXML
    private TextField curpTextField;

    @FXML
    private TextField rfcTextField;

    @FXML
    private ComboBox branchComboBox;

    @FXML
    private ComboBox maritalComboBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private Button registerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void registerButtonPressed(ActionEvent event) {

        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String position = positionComboBox.getValue() != null ? positionComboBox.getValue().toString() : null;
        LocalDate dob = datePicker.getValue();
        char gender = genderComboBox.getValue() != null ? genderComboBox.getValue().toString().equals("Femenino") ? 'F' : 'M' : 0;
        String curp = curpTextField.getText();
        String rfc = rfcTextField.getText();
        int branch = branchComboBox.getValue() != null ? (Integer) branchComboBox.getValue() : 0;
        char marital = maritalComboBox.getValue() != null ? maritalComboBox.getValue().toString().charAt(0) : 0;
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String username = userTextField.getText();
        String pass = passField.getText();
        double salary = !salaryTextField.getText().isEmpty() ? Double.parseDouble(salaryTextField.getText()) : 0000.00;

        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setPosition(position);
        newUser.setDob(dob);
        newUser.setGender(gender);
        newUser.setCurp(curp);
        newUser.setRfc(rfc);
        newUser.setBranch(branch);
        newUser.setMarital(marital);
        newUser.setPhone(phone);
        newUser.setEmail(email);
        newUser.setUser(username);
        newUser.setPassword(pass);
        newUser.setSalary(salary);

        CompositeValidator<User> validator = new CompositeValidator<>();
        validator.add(new PersonalInfoValidator());
        validator.add(new FiscalInfoValidator());
        validator.add(new CorporateInfoValidator());
        validator.add(new LoginInfoValidator());
        validator.add(new ContactInfoValidator());

        ArrayList<String> errors = validator.validate(newUser);

        if (errors.size() == 0) {
            if (rGE.addUser(newUser)) {
                Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setTitle("Usuario registrado");
                dialog.setHeaderText(null);
                dialog.setContentText("El usuario ha sido registrado correctamente. Ahora puedes iniciar sesión.");
                dialog.showAndWait();

                Stage thisStage = (Stage) (registerButton.getScene().getWindow());
                thisStage.close();
            } else {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error al registrar");
                dialog.setHeaderText(null);
                dialog.setContentText("Ha ocurrido el siguiente error: " + rGE.getMessage());
                dialog.showAndWait();
            }
        } else {
            DisplayErrors display = new DisplayErrors("Error de registro", "Problemas de registro",
                    "Ocurrieron los siguientes problemas:");
            display.display(errors);
        }
    }

    @FXML
    private void cancelButtonPressed(ActionEvent event) {
        Stage thisStage = (Stage) (cancelButton.getScene().getWindow());
        thisStage.close();
    }

    @FXML
    private void initialize() {
        positionComboBox.getItems().clear();
        positionComboBox.getItems().add("Empleado");
        positionComboBox.getItems().add("Jefe");

        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20)
                nameTextField.setText(oldValue);
        });

        surnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20)
                surnameTextField.setText(oldValue);
        });

        curpTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 18)
                curpTextField.setText(oldValue);
        });

        rfcTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 13)
                rfcTextField.setText(oldValue);
        });

        phoneTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}"))
                phoneTextField.setText(oldValue);
        });

        emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20)
                emailTextField.setText(oldValue);
        });

        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 15)
                userTextField.setText(oldValue);
        });

        passField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20)
                passField.setText(oldValue);
        });

        datePicker.setConverter(new StringConverter<>() {
            private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) return "";
                return formatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) return null;
                return LocalDate.parse(dateString, formatter);
            }
        });
    }

    public void setrGE(ReportGeneratorEmployee rGE) {
        this.rGE = rGE;
        ArrayList<Store> stores;

        if ((stores = this.rGE.getStores()) == null) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error de carga");
            dialog.setHeaderText(null);
            dialog.setContentText("Ha ocurrido el siguiente error: " + rGE.getMessage());
            dialog.showAndWait();
        } else {
            for (Store store : stores)
                branchComboBox.getItems().add(store.getId());
        }
    }
}
