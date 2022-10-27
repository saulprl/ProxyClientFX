package remoteproxy.Validators;

import remoteproxy.Store;

import java.util.ArrayList;

public class BranchValidator implements Validator<Store> {

    @Override
    public ArrayList<String> validate(Store info) {
        ArrayList<String> errors = new ArrayList<>();

        String name = info.getName();
        String address = info.getAddress();
        int manager = info.getManager();
        String phone = info.getPhone();

        if (name.trim().isEmpty())
            errors.add("El nombre no puede estar vacío.");

        if (address.trim().isEmpty())
            errors.add("La dirección no puede estar vacía.");

        if (manager == 0)
            errors.add("El gerente no puede estar vacío.");

        if (phone.trim().isEmpty())
            errors.add("El número de teléfono no puede estar vacío.");

        if (!phone.trim().matches("\\d{10}"))
            errors.add("El formato del número telefónico es incorrecto.");

        return errors;
    }

}
