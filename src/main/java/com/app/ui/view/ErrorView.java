package com.app.ui.view;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class ErrorView  extends AbstractView {
    @Override
    public Layout getContentLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(new Label("Error Page!!!"));
        return verticalLayout;
    }
}
