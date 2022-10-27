package remoteproxy.Validators;

import remoteproxy.User;

import java.util.ArrayList;

public class FiscalInfoValidator implements Validator<User> {

    @Override
    public ArrayList<String> validate(User info) {
        ArrayList<String> errors = new ArrayList<>();

        String curp = info.getCurp();
        String rfc = info.getRfc();
        char marital = info.getMarital();
        double salary = info.getSalary();

        if (curp.isEmpty()) {
            errors.add("La CURP no puede estar vacía.");
        } else if (curp.length() != 18) {
            errors.add("La CURP debe contener 18 caracteres.");
        }

        if (rfc.isEmpty()) {
            errors.add("El RFC no puede estar vacío.");
        } else if (rfc.length() < 12 || rfc.length() > 13) {
            errors.add("El RFC debe contener entre 12 y 13 caracteres.");
        }

        if (marital != 'S' && marital != 'C') {
            errors.add("Debes proporcionar un estado civil.");
        }

        if (salary != 0000.00) {
            if (!(Double.toString(salary).matches("\\d{0,13}[.]\\d{0,2}"))) {
                errors.add("El formato del salario no es correcto.");
            }
        } else {
            errors.add("Se debe ingresar un salario.");
        }

        return errors;
    }

}
