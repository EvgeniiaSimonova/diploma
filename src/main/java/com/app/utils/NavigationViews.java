package com.app.utils;

import com.app.ui.view.*;
import com.vaadin.navigator.View;

import java.util.HashSet;
import java.util.Set;

public enum NavigationViews {

    MAIN("main", MainView.class),
    USER("user", UserView.class),
    REGISTRATION("registration", RegistrationView.class),
    INN("inn", INNView.class),
    FIRM("firm", FirmView.class),
    DRUG("drug", DrugView.class),
    PHARMACY("pharmacy", PharmacyView.class),
    WORKER("worker", WorkerView.class),
    HISTORY("history", HistoryView.class),
    PRICE("price", PriceView.class),
    SALE("sale", SaleView.class),
    SEARCH("search", SearchView.class),
    ERROR("error", ErrorView.class);

    private String path;
    private Class<? extends View> viewClass;
    public static Set<NavigationViews> views = new HashSet<NavigationViews>();

    static {
        views.add(MAIN);
        views.add(USER);
        views.add(REGISTRATION);
        views.add(INN);
        views.add(FIRM);
        views.add(DRUG);
        views.add(PHARMACY);
        views.add(WORKER);
        views.add(HISTORY);
        views.add(PRICE);
        views.add(SALE);
        views.add(SEARCH);
        views.add(ERROR);
    }

    NavigationViews(String path, Class<? extends View> viewClass) {
        this.path = path;
        this.viewClass = viewClass;
    }

    public String getPath() {
        return path;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }
}
