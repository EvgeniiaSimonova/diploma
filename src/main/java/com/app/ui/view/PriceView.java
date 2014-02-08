package com.app.ui.view;

import com.app.entity.*;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.ui.validators.DoubleValidator;
import com.app.ui.validators.IntegerValidator;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class PriceView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table priceTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isAdmin() || isWorker() || isUser()) {
            try {
                if (isAdmin() || isWorker()) {
                    contentLayout.addComponent(creationForm);
                    initForm();
                }
                contentLayout.addComponent(priceTable);
                initTable();
            } catch (SystemException e) {
                setRedirect(NavigationViews.ERROR);
            }
        }
    }

    private void initForm() throws SystemException {
        final ComboBox pharmacySelect = new ComboBox("Аптека");
        final ComboBox drugSelect = new ComboBox("Лекарство");
        final TextField priceField = new TextField("Стоимость:  ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    pharmacySelect.validate();
                    drugSelect.validate();
                    priceField.validate();

                    PharmacyEnt pharmacyEnt = dashboardService.getPharmacy((String) pharmacySelect.getValue()).get(0);
                    DrugEnt drugEnt = dashboardService.getDrug((String)drugSelect.getValue()).get(0);
                    Double dosage = priceField.getValue().isEmpty() ? null : Double.parseDouble(priceField.getValue());

                    dashboardService.addPrice(pharmacyEnt, drugEnt, dosage);
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.PRICE);
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (Exception  e) {//todo
                    System.out.println(e);
                    setRedirect(NavigationViews.ERROR);
                }
            }
        });

        // set properties
        pharmacySelect.setRequired(true);
        pharmacySelect.setRequiredError("Аптека должна быть выбрана");
        pharmacySelect.setNullSelectionAllowed(false);
        List<PharmacyEnt> pharmacyList = dashboardService.getPharmacyList();
        for (PharmacyEnt pharmacy: pharmacyList) {
            pharmacySelect.addItem(pharmacy.getName());
        }

        drugSelect.setRequired(true);
        drugSelect.setRequiredError("Лекарство должно быть выбрано");
        drugSelect.setNullSelectionAllowed(false);
        List<DrugEnt> drugList = dashboardService.getDrugList();
        for (DrugEnt drug: drugList) {
            drugSelect.addItem(drug.getTitle());
        }

        priceField.setRequired(true);
        priceField.setRequiredError("Цена должна быть указана");
        priceField.addValidator(new DoubleValidator("Поле 'Стоимость' должно содержать число"));
        priceField.setValidationVisible(true);

        // add components
        creationForm.addComponent(pharmacySelect);
        creationForm.addComponent(drugSelect);
        creationForm.addComponent(priceField);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        priceTable.addContainerProperty("pharmacy", Label.class, null, "Аптека", null, Table.Align.LEFT);
        priceTable.addContainerProperty("drug", Label.class, null, "Лекарство", null, Table.Align.LEFT);
        priceTable.addContainerProperty("price", TextField.class, null, "Стоимость", null, Table.Align.LEFT);
        if (isAdmin() || isWorker()) {
            priceTable.addContainerProperty("save", Button.class, null, "Сохранить", null, Table.Align.CENTER);
            priceTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }
        priceTable.setSizeFull();
        priceTable.setImmediate(true);

        List<PriceEnt> list = dashboardService.getPriceList();
        priceTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (PriceEnt priceEnt: list) {
            Long id = priceEnt.getId();
            Label pharmacyLabel = new Label(priceEnt.getPharmacy().getName());
            Label drugLabel = new Label(priceEnt.getDrug().getTitle());

            TextField priceField = new TextField();
            priceField.setValue(priceEnt.getPrice() == null ? "" : priceEnt.getPrice().toString());

            if (isAdmin() || isWorker()) {
                Button save = new Button("ОК");
                save.setData(id);
                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Long id = (Long) event.getButton().getData();
                        try {
                            PriceEnt priceEnt = dashboardService.getPrice(id);
                            Item item = priceTable.getItem(id);

                            TextField priceField = (TextField) item.getItemProperty("price").getValue();
                            priceEnt.setPrice(priceField.getValue().isEmpty() ? null : Double.parseDouble(priceField.getValue()));
                            priceField.validate(); // todo good validation

                            dashboardService.updatePrice(priceEnt);
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
                        Long id = (Long) event.getButton().getData();
                        try {
                            dashboardService.deletePrice(id);
                            Notification.show(Constants.DELETED);
                            setRedirect(NavigationViews.PRICE);
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                priceTable.addItem(new Object[]{pharmacyLabel, drugLabel, priceField, save, delete}, id);
            } else {
                priceField.setReadOnly(true);
                priceTable.addItem(new Object[]{pharmacyLabel, drugLabel, priceField}, id);
            }
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
