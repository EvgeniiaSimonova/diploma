package com.app.ui.field;

import com.vaadin.data.Property;
import com.vaadin.ui.PasswordField;

/**
 * @author Evgenia Simonova
 */
public class InnPasswordField extends PasswordField {
    public InnPasswordField() {
        super();
    }

    public InnPasswordField(Property dataSource) {
        super(dataSource);
    }

    public InnPasswordField(String caption, Property dataSource) {
        super(caption, dataSource);
    }

    public InnPasswordField(String caption, String value) {
        super(caption, value);
    }

    public InnPasswordField(String caption) {
        super(caption);
    }

    @Override
    public String getValue() {
        return super.getValue().trim();
    }
}
