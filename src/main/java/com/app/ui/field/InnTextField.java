package com.app.ui.field;

import com.vaadin.data.Property;
import com.vaadin.ui.TextField;

/**
 * @author Evgenia Simonova
 */
public class InnTextField extends TextField {

    public InnTextField() {
        super();
    }

    public InnTextField(String caption) {
        super(caption);
    }

    public InnTextField(Property dataSource) {
        super(dataSource);
    }

    public InnTextField(String caption, Property dataSource) {
        super(caption, dataSource);
    }

    public InnTextField(String caption, String value) {
        super(caption, value);
    }

    @Override
    public String getValue() {
        return super.getValue().trim();
    }

    public String getLowerCaseValue() {
        return getValue().toLowerCase();
    }
}
