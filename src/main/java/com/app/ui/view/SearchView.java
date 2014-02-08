package com.app.ui.view;

import com.app.entity.*;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.ui.validators.IntegerValidator;
import com.app.utils.NavigationViews;
import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class SearchView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table drugTable = new Table();
    private FormLayout searchForm = new FormLayout();
    private List<DrugEnt> drugList = new ArrayList<DrugEnt>();
    private Label searchResult = new Label("");

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        try {
            contentLayout.addComponent(searchForm);
            initForm();
            contentLayout.addComponent(searchResult);
            contentLayout.addComponent(drugTable);
            initTable();
        } catch (SystemException e) {
            setRedirect(NavigationViews.ERROR);
        }
    }

    private void initForm() throws SystemException {
        final ComboBox innSelect = new ComboBox("МНН");
        final ComboBox firmSelect = new ComboBox("Производитель");
        final TextField dosageField = new TextField("Дозировка:  ");
        Button searchButton = new Button("Искать", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    innSelect.validate();
                    firmSelect.validate();
                    dosageField.validate();

                    InternationalNonProprietaryNameEnt inn = innSelect.getValue()== null ? null : dashboardService
                            .getInternationalNonProprietaryName((String)innSelect.getValue());
                    FirmEnt firm = firmSelect.getValue()== null ? null : dashboardService.getFirm((String)firmSelect.getValue());
                    Double dosage = dosageField.getValue().isEmpty() ? null : Double.parseDouble(dosageField.getValue());

                    drugList = dashboardService.searchDrugEnt(inn, firm, dosage);
                    searchResult.setValue(drugList.size() + "");
                    initTable();
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (Exception  e) {//todo
                    System.out.println(e);
                    setRedirect(NavigationViews.ERROR);
                }
            }
        });

        // set properties
        List<InternationalNonProprietaryNameEnt> innList = dashboardService.getInternationalNonProprietaryNameList();
        for (InternationalNonProprietaryNameEnt inn: innList) {
            innSelect.addItem(inn.getRecommendedName());
        }

        List<FirmEnt> firmList = dashboardService.getFirmList();
        for (FirmEnt firm: firmList) {
            firmSelect.addItem(firm.getTitle());
        }

        dosageField.addValidator(new IntegerValidator("Поле 'Дозировка' должно содержать число"));
        dosageField.setValidationVisible(true);

        // add components
        searchForm.addComponent(innSelect);
        searchForm.addComponent(firmSelect);
        searchForm.addComponent(dosageField);
        searchForm.addComponent(searchButton);
    }

    private void initTable() throws SystemException {
        drugTable.removeAllItems();
        drugTable.addContainerProperty("title", Label.class, null, "Наименование", null, Table.Align.LEFT);
        drugTable.addContainerProperty("rating", Double.class, null, "Оценка", null, Table.Align.LEFT);

        if (!isGuest()) {
            drugTable.addContainerProperty("price", Double.class, null, "Средняя цена", null, Table.Align.LEFT);
            //drugTable.addContainerProperty("ratingSelect", Button.class, null, "Оценка", null, Table.Align.CENTER);
            //drugTable.addContainerProperty("pharmacyButton", Button.class, null, "Сравнить цены", null, Table.Align.CENTER);
            drugTable.addContainerProperty("markerButton", Button.class, null, "Закладки", null, Table.Align.CENTER);
        }
        drugTable.setSizeFull();
        drugTable.setImmediate(true);

        drugTable.setPageLength(getTableSize(drugList));
        for (DrugEnt drugEnt: drugList) {
            Long id = drugEnt.getId();
            Label titleLabel = new Label(drugEnt.getTitle());

            Double rating = new Double(0);
            for (RatingEnt ratingEnt: drugEnt.getRatings()) {
                rating += ratingEnt.getRating();
            }
            rating = rating / drugEnt.getRatings().size();

            Double price = new Double(0);
            for (PriceEnt priceEnt: drugEnt.getPrices()) {
                price += priceEnt.getPrice();
            }
            price = price / drugEnt.getPrices().size();

            if (!isGuest()) {
                final PrincipalEnt principalEnt = dashboardService.getPrincipal(getUsername());
                MarkerEnt markerEnt = dashboardService.getMarker(principalEnt, drugEnt);
                String buttonValue = "Удалить";
                if (markerEnt == null) {
                    buttonValue = "Добавить";
                }
                final Button markerButton = new Button(buttonValue);
                markerButton.setData(id);
                markerButton.setImmediate(true);
                markerButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Long id = (Long) event.getButton().getData();
                        try {
                            DrugEnt drug = dashboardService.getDrug(id);
                            MarkerEnt markerEnt = dashboardService.getMarker(principalEnt, drug);
                            if (markerEnt == null) {
                                dashboardService.addMarker(principalEnt, drug);
                                markerButton.setCaption("Удалить");
                            } else {
                                dashboardService.deleteMarker(principalEnt, drug);
                                markerButton.setCaption("Добавить");
                            }
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                drugTable.addItem(new Object[]{titleLabel, rating, price, markerButton}, id);

            } else {
                drugTable.addItem(new Object[]{titleLabel, rating}, id);
            }

        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
