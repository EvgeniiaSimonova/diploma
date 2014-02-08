package com.app.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public class MainView extends AbstractView {
    private VerticalLayout contentLayout = new VerticalLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        contentLayout.addComponent(new Label("It is a main page"));
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
