package remoteproxy.Validators;

import java.util.ArrayList;

public interface Validator<T> {

    public ArrayList<String> validate(T info);

}
