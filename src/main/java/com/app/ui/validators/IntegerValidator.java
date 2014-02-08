package com.app.ui.validators;

import com.vaadin.data.Validator;

public class IntegerValidator implements Validator {

    private String errorMessage = "Поле должно содержать положительное число";

    public IntegerValidator() {}

    public IntegerValidator(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void validate(Object value) throws InvalidValueException {
        if (!isValid(value)) {
            throw new InvalidValueException(errorMessage);
        }
    }

    private boolean isValid(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            if (((String)value).length() == 0) {
                return true;
            }
            try {
                Integer number = Integer.parseInt((String)value);
                if (number > 0) {
                    return true;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
