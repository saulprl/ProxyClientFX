package remoteproxy.Validators;

import remoteproxy.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class PersonalInfoValidator implements Validator<User> {

    @Override
    public ArrayList<String> validate(User info) {
        ArrayList<String> errors = new ArrayList<>();

        String name = info.getName();
        String surname = info.getSurname();
        char gender = info.getGender();
        LocalDate dob = info.getDob();

        if (name.isEmpty()) {
            errors.add("El nombre no puede estar vacío.");
        }

        if (surname.isEmpty()) {
            errors.add("El apellido no puede estar vacío.");
        }

        if (gender != 'F' && gender != 'M') {
            errors.add("Debes ingresar un género.");
        }

        if (dob != null) {
            if (Period.between(dob, LocalDate.now()).getYears() < 18) {
                errors.add("La edad es menor a 18 años.");
            }
        } else {
            errors.add("Debe ingresar una fecha de nacimiento.");
        }

        return errors;
    }

}
