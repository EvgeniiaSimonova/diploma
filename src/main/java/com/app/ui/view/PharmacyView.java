package com.app.ui.view;

import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.entity.PharmacyEnt;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class PharmacyView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table pharmacyTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isUser() || isWorker() || isAdmin()) {
            if (isAdmin()) {
                contentLayout.addComponent(creationForm);
                initForm();
            }
            contentLayout.addComponent(pharmacyTable);
            try {
                initTable();
            } catch (SystemException e) {
                setRedirect(NavigationViews.ERROR);
            }
        } else {
            setRedirect(NavigationViews.ERROR); // todo redirect to request forbidden page
        }

    }

    private void initForm() {
        final TextField nameField = new TextField("Название: ");
        final TextField addressField = new TextField("Адрес: ");
        final TextField descriptionField = new TextField("Описание:  ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    nameField.validate();
                    addressField.validate();
                    descriptionField.validate();
                    dashboardService.addPharmacy(nameField.getValue(), addressField.getValue(), descriptionField.getValue());
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.PHARMACY);
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (SystemException e) {
                    setRedirect(NavigationViews.ERROR);
                }
            }
        });

        // set properties
        nameField.setRequired(true);
        nameField.setRequiredError("Поле 'Название' должно быть заполнено");
        nameField.setValidationVisible(true);

        // add components
        creationForm.addComponent(nameField);
        creationForm.addComponent(addressField);
        creationForm.addComponent(descriptionField);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        pharmacyTable.addContainerProperty("name", Label.class, null, "Название", null, Table.Align.LEFT);
        pharmacyTable.addContainerProperty("address", TextField.class, null, "Адрес", null, Table.Align.LEFT);
        if (isAdmin()) {
            pharmacyTable.addContainerProperty("save", Button.class, null, "Сохранить", null, Table.Align.CENTER);
            pharmacyTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }
        pharmacyTable.setSizeFull();
        pharmacyTable.setImmediate(true);

        List<PharmacyEnt> list = dashboardService.getPharmacyList();
        pharmacyTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (PharmacyEnt pharmacyEnt: list) {
            Long id = pharmacyEnt.getId();
            Label nameLabel = new Label(pharmacyEnt.getName());

            TextField addressField = new TextField();
            addressField.setValue(pharmacyEnt.getAddress());

            if (isAdmin()) {
                Button save = new Button("ОК");
                save.setData(id);
                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Long id = (Long) event.getButton().getData();
                        try {
                            PharmacyEnt pharmacy = dashboardService.getPharmacy(id);
                            Item item = pharmacyTable.getItem(id);

                            TextField addressField = (TextField)item.getItemProperty("address").getValue();
                            pharmacy.setAddress(addressField.getValue());

                            dashboardService.updatePharmacy(pharmacy);
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
                        Long id = (Long) event.getButton().getData();
                        try {
                            dashboardService.deletePharmacy(id);
                            Notification.show(Constants.DELETED);
                            setRedirect(NavigationViews.PHARMACY);
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                pharmacyTable.addItem(new Object[]{nameLabel, addressField, save, delete}, id);
            } else {
                addressField.setReadOnly(true);
                pharmacyTable.addItem(new Object[]{nameLabel, addressField}, id);
            }
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
