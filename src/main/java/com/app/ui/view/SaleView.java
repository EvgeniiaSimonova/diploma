package com.app.ui.view;

import com.app.entity.DrugEnt;
import com.app.entity.PharmacyEnt;
import com.app.entity.PriceEnt;
import com.app.entity.SaleEnt;
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

import java.util.Date;
import java.util.List;

public class SaleView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table saleTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isAdmin() || isWorker()) {
            try {
                contentLayout.addComponent(creationForm);
                initForm();
                contentLayout.addComponent(saleTable);
                initTable();
            } catch (SystemException e) {
                setRedirect(NavigationViews.ERROR);
            }
        }
    }

    private void initForm() throws SystemException {
        final ComboBox pharmacySelect = new ComboBox("Аптека");
        final ComboBox drugSelect = new ComboBox("Лекарство");
        final TextField countField = new TextField("Количество:  ");
        final PopupDateField dateField = new PopupDateField("Дата: ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    pharmacySelect.validate();
                    drugSelect.validate();
                    countField.validate();
                    dateField.validate();
                    PharmacyEnt pharmacyEnt = dashboardService.getPharmacy((String) pharmacySelect.getValue()).get(0);
                    DrugEnt drugEnt = dashboardService.getDrug((String)drugSelect.getValue()).get(0);
                    Integer count = countField.getValue().isEmpty() ? null : Integer.parseInt(countField.getValue());
                    Date date = dateField.getValue();
                    dashboardService.addSale(date, pharmacyEnt, drugEnt, count);
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.SALE);
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (BusinessException e) {
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

        countField.setRequired(true);
        countField.setRequiredError("Количество должно быть указано");
        countField.addValidator(new IntegerValidator("Поле 'Количество' должно содержать число"));
        countField.setValidationVisible(true);

        dateField.setRequired(true);
        dateField.setRequiredError("Дата должна быть указана");

        // add components
        creationForm.addComponent(pharmacySelect);
        creationForm.addComponent(drugSelect);
        creationForm.addComponent(countField);
        creationForm.addComponent(dateField);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        saleTable.addContainerProperty("pharmacy", String.class, null, "Аптека", null, Table.Align.LEFT);
        saleTable.addContainerProperty("drug", String.class, null, "Лекарство", null, Table.Align.LEFT);
        saleTable.addContainerProperty("count", Integer.class, null, "Количество", null, Table.Align.LEFT);
        saleTable.addContainerProperty("price", Double.class, null, "Цена", null, Table.Align.LEFT);
        saleTable.addContainerProperty("data", Date.class, null, "Дата", null, Table.Align.LEFT);

        if (isAdmin()) {
            saleTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }

        saleTable.setSizeFull();
        saleTable.setImmediate(true);

        List<SaleEnt> list = dashboardService.getSaleList();
        saleTable.setPageLength(getTableSize(list));
        for (SaleEnt saleEnt: list) {
            Long id = saleEnt.getId();
            String pharmacy = saleEnt.getPharmacy().getName();
            String drug = saleEnt.getDrug().getTitle();
            Integer count = saleEnt.getCount();
            Double price = saleEnt.getPrice();
            Date date = saleEnt.getDate();


            if (isAdmin()) {
                Button delete = new Button("X");
                delete.setData(id);
                delete.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Long id = (Long) event.getButton().getData();
                        try {
                            dashboardService.deleteSale(id);
                            Notification.show(Constants.DELETED);
                            setRedirect(NavigationViews.SALE);
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                saleTable.addItem(new Object[]{pharmacy, drug, count, price, date, delete}, id);
            } else {
                saleTable.addItem(new Object[]{pharmacy, drug, count, price, date}, id);
            }
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
