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
            errors.add("El nombre no puede estar vac�o.");

        if (address.trim().isEmpty())
            errors.add("La direcci�n no puede estar vac�a.");

        if (manager == 0)
            errors.add("El gerente no puede estar vac�o.");

        if (phone.trim().isEmpty())
            errors.add("El n�mero de tel�fono no puede estar vac�o.");

        if (!phone.trim().matches("\\d{10}"))
            errors.add("El formato del n�mero telef�nico es incorrecto.");

        return errors;
    }

}
