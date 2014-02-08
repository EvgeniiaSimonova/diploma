package com.app.ui.form;

import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.utils.Constants;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

import java.util.HashMap;
import java.util.Map;

public class FormFactory {

    private FormLayout form;
    private Map<String, AbstractField> fields;

    public FormFactory() {
        form = new FormLayout();
        fields = new HashMap<String, AbstractField>();
    }

    public FormLayout build() {
        return this.form;
    }

    public FormFactory addTextField(String name, String title, boolean isRequired, String requiredError, Validator[] validators) throws SystemException {
        if (name == null || Constants.EMPTY_STRING.equals(name)) {
            throw new SystemException("The field name can not be null or empty.");
        }
        if (fields.containsKey(name)) {
            throw new SystemException("The field with name '" + name + "' already exists.");
        }

        TextField textField = new TextField(title);
        textField.setValidationVisible(true);
        textField.setRequired(isRequired);
        textField.setRequiredError(requiredError);
        for (int i = 0; i < validators.length; i++) {
            textField.addValidator(validators[i]);
        }

        return this;
    }

    public String getFieldValue(String key) throws SystemException {
        if (key == null || Constants.EMPTY_STRING.equals(key)) {
            throw new SystemException("The field name can not be null or empty");
        }
        AbstractField field = fields.get(key);
        if (field instanceof TextField) {
            TextField textField = (TextField) field;
            return textField.getValue().trim();
        } else {
            return field.getValue().toString();
        }
    }

    public String getLowerCaseValue(String key) throws SystemException {
        return getFieldValue(key).trim();
    }

    public void validate() {
        for (String key: fields.keySet()) {
            fields.get(key).validate();
        }
    }
}
