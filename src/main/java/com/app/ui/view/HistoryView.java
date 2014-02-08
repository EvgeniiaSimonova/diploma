package com.app.ui.view;

import com.app.entity.DrugEnt;
import com.app.entity.HistoryEnt;
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

public class HistoryView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table historyTable = new Table();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isAdmin()) {
            contentLayout.addComponent(historyTable);
            try {
                initTable();
            } catch (SystemException e) {
                setRedirect(NavigationViews.ERROR);
            }
        } else {
            setRedirect(NavigationViews.ERROR); // todo redirect to request forbidden
        }
    }

    private void initTable() throws SystemException {
        historyTable.addContainerProperty("priceId", Long.class, null, "ID", null, Table.Align.LEFT);
        historyTable.addContainerProperty("pharmacy", String.class, null, "Аптека", null, Table.Align.LEFT);
        historyTable.addContainerProperty("drug", String.class, null, "Лекарство", null, Table.Align.LEFT);
        historyTable.addContainerProperty("price", Double.class, null, "Цена", null, Table.Align.LEFT);
        historyTable.addContainerProperty("type", String.class, null, "Тип операции", null, Table.Align.LEFT);

        historyTable.setSizeFull();
        historyTable.setImmediate(true);

        List<HistoryEnt> list = dashboardService.getHistoryList();
        historyTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (HistoryEnt history: list) {
            Long id = history.getId();

            PharmacyEnt pharmacyEnt = history.getPharmacyId() == null ? null : dashboardService.getPharmacy(history.getPharmacyId());
            DrugEnt drugEnt = history.getDrugId() == null ? null : dashboardService.getDrug(history.getDrugId());

            Long priceId = history.getPriceId();
            String pharmacy = pharmacyEnt == null ? "" : pharmacyEnt.getName();
            String drug = drugEnt == null ? "" : drugEnt.getTitle();
            Double price = history.getPrice();
            String type = history.getOperationType();

            historyTable.addItem(new Object[]{ priceId, pharmacy, drug, price, type }, id);
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
