package com.app.ui.listener;

import com.app.utils.NavigationViews;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: Evgenia Simonova
 */
public class LogoutListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent event) {
        SecurityContextHolder.clearContext();
        //UI.getCurrent().close();
        Navigator navigator = UI.getCurrent().getNavigator();
        navigator.navigateTo(NavigationViews.MAIN.getPath());
    }
}
