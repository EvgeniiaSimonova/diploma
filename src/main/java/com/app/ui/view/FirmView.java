package com.app.ui.view;

import com.app.entity.FirmEnt;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.ui.validators.IntegerValidator;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class FirmView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table firmTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isAdmin()) {
            contentLayout.addComponent(creationForm);
            initForm();
        }
        contentLayout.addComponent(firmTable);
        try {
            initTable();
        } catch (SystemException e) {
            setRedirect(NavigationViews.ERROR);
        }
    }

    private void initForm() {
        final TextField firmName = new TextField("Название: ");
        final TextField year = new TextField("Год: ");
        final TextField country = new TextField("Страна:  ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    firmName.validate();
                    year.validate();
                    country.validate();
                    dashboardService.addFirm(firmName.getValue(), (year.getValue().isEmpty() ? null : Integer.parseInt(year.getValue())), country.getValue());
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.FIRM);
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
        firmName.setRequired(true);
        firmName.setRequiredError("Поле 'Название' должно быть заполнено");
        firmName.setValidationVisible(true);

        year.addValidator(new IntegerValidator("Поле 'год' должно содержать число"));
        //year.setValidationVisible(true);

        // add components
        creationForm.addComponent(firmName);
        creationForm.addComponent(year);
        creationForm.addComponent(country);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        firmTable.addContainerProperty("name", Label.class, null, "Название", null, Table.Align.LEFT);
        firmTable.addContainerProperty("year", TextField.class, null, "Год", null, Table.Align.LEFT);
        firmTable.addContainerProperty("country", TextField.class, null, "Страна", null, Table.Align.LEFT);
        if (isAdmin()) {
            firmTable.addContainerProperty("save", Button.class, null, "Сохранить", null, Table.Align.CENTER);
            firmTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }
        firmTable.setSizeFull();
        firmTable.setImmediate(true);

        List<FirmEnt> list = dashboardService.getFirmList();
        firmTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (FirmEnt firm: list) {
            String id = firm.getTitle();
            Label titleLabel = new Label(id);

            TextField yearField = new TextField();
            yearField.addValidator(new IntegerValidator());
            yearField.setValue(firm.getYear() == null ? "" : firm.getYear().toString());

            TextField countryField = new TextField();
            countryField.setValue(firm.getCountry());

            if (isAdmin()) {
                Button save = new Button("ОК");
                save.setData(id);
                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String title = (String) event.getButton().getData();
                        try {
                            FirmEnt firm = dashboardService.getFirm(title);
                            Item item = firmTable.getItem(title);

                            TextField yearField = (TextField)item.getItemProperty("year").getValue();
                            firm.setYear((yearField.getValue().isEmpty() ? null : Integer.parseInt(yearField.getValue())));

                            TextField countryField = (TextField)item.getItemProperty("country").getValue();
                            firm.setCountry(countryField.getValue());

                            yearField.validate(); // todo good validation

                            dashboardService.updateFirm(firm);
                        } catch (Validator.InvalidValueException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                        Notification.show(Constants.EDITED);
                    }
                });

                Button delete = new Button("X");
                delete.setData(id);
                delete.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String title = (String) event.getButton().getData();
                        try {
                            dashboardService.deleteFirm(title);
                            Notification.show(Constants.DELETED);
                            setRedirect(NavigationViews.FIRM);
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                firmTable.addItem(new Object[]{titleLabel, yearField, countryField, save, delete}, id);
            } else {
                yearField.setReadOnly(true);
                countryField.setReadOnly(true);
                firmTable.addItem(new Object[]{titleLabel, yearField, countryField}, id);
            }
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
