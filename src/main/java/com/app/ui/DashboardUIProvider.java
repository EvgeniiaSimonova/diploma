package com.app.ui;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

/**
 * User: Evgenia Simonova
 */
public class DashboardUIProvider extends UIProvider {
    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        return DashboardUI.class;
    }
}
