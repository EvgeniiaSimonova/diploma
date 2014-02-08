package com.app.ui.listener;

import com.app.auth.AuthManager;
import com.app.ui.DashboardUI;
import com.app.ui.form.LoginForm;
import com.app.utils.NavigationViews;
import com.vaadin.data.Validator;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: Evgenia Simonova
 */
public class LoginFormListener implements Button.ClickListener {

    @Autowired
    private AuthManager authManager;

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {

            Button source = event.getButton();
            LoginForm parent = (LoginForm) source.getParent().getParent();
            String username = parent.getTextLogin().getLowerCaseValue();
            String password = parent.getTextPassword().getValue();
            UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = authManager.authenticate(request);

            SecurityContextHolder.setStrategyName("MODE_GLOBAL");
            SecurityContextHolder.getContext().setAuthentication(result);

            DashboardUI current = (DashboardUI) UI.getCurrent();
            Navigator navigator = current.getNavigator();
            navigator.navigateTo(NavigationViews.MAIN.getPath());
        } catch (AuthenticationException e) {
            Notification.show("Authentication failed: " + e.getMessage());
        }
    }
}
