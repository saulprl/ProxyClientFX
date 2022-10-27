package remoteproxy.Validators;

import remoteproxy.User;

import java.util.ArrayList;

public class LoginInfoValidator implements Validator<User> {

    @Override
    public ArrayList<String> validate(User info) {
        ArrayList errors = new ArrayList<>();

        String user = info.getUser();
        String pass = info.getPassword();

        if (user.isEmpty()) {
            errors.add("El nombre de usuario no puede estar vac�o.");
        } else if (user.length() > 15) {
            errors.add("El nombre de usuario no puede contener m�s de 15 caracteres.");
        }

        if (pass.isEmpty()) {
            errors.add("La contrase�a no puede estar vac�a.");
        } else if (pass.length() < 8) {
            errors.add("La contrase�a debe contener al menos 8 caracteres.");
        } else if (!pass.matches("((?=.*[a-z])(?=.*\\d{2})(?=.*^[a-zA-Z0-9]{2})(?=.*[A-Z]).{8,20})")) {
            errors.add("La contrase�a debe contener al menos dos n�meros, dos caracteres especiales y una may�scula.");
        }

        return errors;
    }

}
