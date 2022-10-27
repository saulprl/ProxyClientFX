package remoteproxy.Validators;

import remoteproxy.User;

import java.util.ArrayList;

public class CorporateInfoValidator implements Validator<User> {

    @Override
    public ArrayList<String> validate(User info) {
        ArrayList<String> errors = new ArrayList<>();

        String role = info.getPosition();
        int branch = info.getBranch();

        if (role != null) {
            if (!(role.equals("Empleado")) && !(role.equals("Jefe"))) {
                errors.add("El puesto no puede estar vacío.");
            }
        } else {
            errors.add("El puesto no puede estar vacío.");
        }

        return errors;
    }

}
