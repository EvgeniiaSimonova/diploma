package com.app.ui.form;

import com.app.ui.DashboardUI;
import com.app.ui.field.InnPasswordField;
import com.app.ui.field.InnTextField;
import com.app.ui.listener.LoginFormListener;
import com.app.ui.validators.LoginValidator;
import com.app.utils.NavigationViews;
import com.vaadin.ui.*;
import org.springframework.context.ApplicationContext;

/**
 * User: Evgenia Simonova
 */
public class LoginForm extends FormLayout {
    private InnTextField textLogin = new InnTextField("Логин: ");
    private InnPasswordField textPassword = new InnPasswordField("Пароль: ");
    private Button buttonLogin = new Button("Войти");
    private Button registrationButton = new Button("Зарегистрироваться");

    public LoginForm() {
        addComponent(textLogin);
        addComponent(textPassword);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(buttonLogin);
        buttonLayout.addComponent(registrationButton);
        addComponent(buttonLayout);


        LoginFormListener loginFormListener = getLoginFormListener();
        buttonLogin.addClickListener(loginFormListener);

        registrationButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo(NavigationViews.REGISTRATION.getPath());
            }
        });
    }

    public LoginFormListener getLoginFormListener() {
        DashboardUI ui = (DashboardUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(LoginFormListener.class);
    }

    public InnTextField getTextLogin() {
        return textLogin;
    }

    public InnPasswordField getTextPassword() {
        return textPassword;
    }
}
