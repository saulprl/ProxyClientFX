package remoteproxy.Validators;

import java.util.ArrayList;

public class CompositeValidator<T> implements Validator<T> {

    private ArrayList<Validator<T>> validators;

    public CompositeValidator() {
        this.validators = new ArrayList<>();
    }

    public CompositeValidator(ArrayList<Validator<T>> validators) {
        this.validators = validators;
    }

    @Override
    public ArrayList<String> validate(T info) {
        ArrayList<String> errors = new ArrayList<>();

        for (Validator validator : validators) {
            errors.addAll(validator.validate(info));
        }

        return errors;
    }

    public void add(Validator<T> validator) {
        this.validators.add(validator);
    }

    public void remove(Validator<T> validator) {
        this.validators.remove(validator);
    }

}
