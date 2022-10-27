package remoteproxy.Validators;

import remoteproxy.User;

import java.util.ArrayList;

public class ContactInfoValidator implements Validator<User> {

    @Override
    public ArrayList<String> validate(User info) {
        ArrayList<String> errors = new ArrayList<>();

        String email = info.getEmail();
        String phone = info.getPhone();

        if (email.trim().isEmpty()) {
            errors.add("El correo electrónico no puede estar vacío.");
        } else if (!email.matches("^(.+)@(.+)[.](.+)$")) {
            errors.add("El formato de correo electrónico no es correcto.");
        }

        if (phone.trim().isEmpty()) {
            errors.add("El número de teléfono no puede estar vacío.");
        } else if (phone.length() != 10) {
            errors.add("El número telefónico debe tener una longitud de 10 dígitos.");
        }

        return errors;
    }

}
