package com.app.ui.view;

import com.app.entity.PharmacyEnt;
import com.app.entity.PrincipalEnt;
import com.app.entity.Role;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class WorkerView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table workerTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isAdmin()) {
            contentLayout.addComponent(creationForm);
            initForm();
            contentLayout.addComponent(workerTable);
            try {
                initTable();
            } catch (SystemException e) {
                setRedirect(NavigationViews.ERROR);
            }
        }
    }

    private void initForm() {
        final TextField usernameField = new TextField("Логин: ");
        final TextField passwordField = new TextField("Палоль: ");
        final TextField emailField = new TextField("Email:  ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    usernameField.validate();
                    passwordField.validate();
                    emailField.validate();
                    dashboardService.addPrincipal(usernameField.getValue(), passwordField.getValue(), emailField.getValue(), Role.WORKER);
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.WORKER);
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
        usernameField.setMaxLength(30);
        passwordField.setMaxLength(30);
        emailField.setMaxLength(50);


        usernameField.setRequired(true);
        usernameField.setRequiredError("Поле 'Логин' должно быть заполнено");
        usernameField.setValidationVisible(true);

        passwordField.setRequired(true);
        passwordField.setRequiredError("Поле 'Пароль' должно быть заполнено");
        passwordField.setValidationVisible(true);

        emailField.addValidator(new EmailValidator("Неверный адрес электронной почты"));

        // add components
        creationForm.addComponent(usernameField);
        creationForm.addComponent(passwordField);
        creationForm.addComponent(emailField);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        workerTable.addContainerProperty("name", Label.class, null, "Логин", null, Table.Align.LEFT);
        workerTable.addContainerProperty("password", Label.class, null, "Email", null, Table.Align.LEFT);
        if (isAdmin()) {
            workerTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }
        workerTable.setSizeFull();
        workerTable.setImmediate(true);

        List<PrincipalEnt> list = dashboardService.getPrincipalList(Role.WORKER);
        workerTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (PrincipalEnt principalEnt: list) {
            String username = principalEnt.getUsername();
            Label nameLabel = new Label(username);
            Label emailLabel = new Label(principalEnt.getEmail());
            Button delete = new Button("X");
            delete.setData(username);
            delete.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    String id = (String) event.getButton().getData();
                    try {
                        dashboardService.deletePrincipal(id);
                        Notification.show(Constants.DELETED);
                        setRedirect(NavigationViews.WORKER);
                    } catch (BusinessException e) {
                        Notification.show(e.getMessage());
                    } catch (SystemException e) {
                        setRedirect(NavigationViews.ERROR);
                    }
                }
            });
            workerTable.addItem(new Object[]{nameLabel, emailLabel, delete}, username);
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
