package com.app.ui.validators;

import com.vaadin.data.Validator;

/**
 * @author Evgenia Simonova
 */
public class LoginValidator implements Validator {

    private String errorMessage = "Логин должен содержать только символы латинского алфавита и нижнее подчеркивание";

    @Override
    public void validate(Object value) throws InvalidValueException {
        if (value instanceof String) {
            String s = (String) value;
            for (int i = 0; i < s.length(); i++) {
                int code = s.codePointAt(i);
                if (!(code == 95 || (code >= 65 && code <= 90) || (code >= 97 && code <= 122) || (code >= 48 && code <= 57))) {
                    throw new InvalidValueException(errorMessage);
                }
            }
        } else {
            throw new InvalidValueException(errorMessage);
        }
    }
}
