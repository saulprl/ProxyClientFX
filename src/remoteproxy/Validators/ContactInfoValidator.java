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
            errors.add("El correo electr�nico no puede estar vac�o.");
        } else if (!email.matches("^(.+)@(.+)[.](.+)$")) {
            errors.add("El formato de correo electr�nico no es correcto.");
        }

        if (phone.trim().isEmpty()) {
            errors.add("El n�mero de tel�fono no puede estar vac�o.");
        } else if (phone.length() != 10) {
            errors.add("El n�mero telef�nico debe tener una longitud de 10 d�gitos.");
        }

        return errors;
    }

}
