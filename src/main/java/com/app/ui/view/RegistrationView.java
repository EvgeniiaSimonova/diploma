package com.app.ui.view;

import com.app.entity.Role;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.ui.validators.LoginValidator;
import com.app.utils.NavigationViews;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

public class RegistrationView extends AbstractView {

    private FormLayout contentLayout = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        // initialization
        final TextField login = new TextField("Логин: ");
        final PasswordField password = new PasswordField("Пароль: ");
        final PasswordField confirmPassword = new PasswordField("Повторите пароль: ");
        final TextField email = new TextField("Email: ");
        Button save = new Button("Зарегистрироваться!", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    login.validate();
                    password.validate();
                    confirmPassword.validate();
                    email.validate();
                    dashboardService.addPrincipal(login.getValue().trim().toLowerCase(),
                            password.getValue().trim(),
                            email.getValue().trim().toLowerCase(), Role.USER);
                    Notification.show("Регистрация успешна!");
                    setRedirect(NavigationViews.MAIN);
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (BusinessException e) {
                    Notification.show(e.getMessage());
                } catch (SystemException e) {
                    setRedirect(NavigationViews.ERROR);
                }
            }
        });

        // set properties
        login.addValidator(new LoginValidator());
        login.setMaxLength(30);
        password.setMaxLength(30);
        confirmPassword.setMaxLength(30);
        email.setMaxLength(50);


        login.setRequired(true);
        login.setRequiredError("Поле 'Логин' должно быть заполнено");
        login.setValidationVisible(true);

        password.setRequired(true);
        password.setRequiredError("Поле 'Пароль' должно быть заполнено");
        password.setValidationVisible(true);

        confirmPassword.setRequired(true);
        confirmPassword.setRequiredError("Поле 'Подтверждение пароля' должно быть заполнено");
        confirmPassword.setValidationVisible(true);


        confirmPassword.addValidator(new AbstractStringValidator("Пароли не совпадают. ") {
            @Override
            protected boolean isValidValue(String value) {
                if (value.equals(password.getValue())) {
                    return true;
                }
                return false;
            }
        });

        email.addValidator(new EmailValidator("Неверный адрес электронной почты"));

        // set components
        contentLayout.addComponent(login);
        contentLayout.addComponent(password);
        contentLayout.addComponent(confirmPassword);
        contentLayout.addComponent(email);
        contentLayout.addComponent(save);
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }


}