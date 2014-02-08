package com.app.ui.view;

import com.app.entity.MarkerEnt;
import com.app.entity.PrincipalEnt;
import com.app.entity.RatingEnt;
import com.app.exception.SystemException;
import com.app.ui.listener.LogoutListener;
import com.app.utils.NavigationViews;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * User: Evgenia Simonova
 */
public class UserView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table markerTable = new Table("Ваши закладки: ");
    private Table ratingTable = new Table("Ваши оценки: ");

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();

            Label labelLogin = new Label("Здравствуйте, " + name + "!");
            contentLayout.addComponent(labelLogin);
            contentLayout.addComponent(markerTable);
            contentLayout.addComponent(ratingTable);
            try {
                initMarketTable();
                initRatingTable();
            } catch (SystemException e) {
                setRedirect(NavigationViews.ERROR);
            }
        } else {
            setRedirect(NavigationViews.ERROR); // todo request forbidden
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }

    private void initMarketTable() throws SystemException {
        markerTable.addContainerProperty("title", String.class, null, "Лекарственный препарат", null, Table.Align.LEFT);
        PrincipalEnt principalEnt = dashboardService.getPrincipal(getUsername());
        Set<MarkerEnt> set = principalEnt.getMarkers();
        markerTable.setPageLength(getTableSize(set));
        for (MarkerEnt markerEnt: set) {
            markerTable.addItem(new Object[] { markerEnt.getDrug().getTitle() }, markerEnt.getId());
        }
    }

    private void initRatingTable() throws SystemException {
        ratingTable.addContainerProperty("title", String.class, null, "Лекарственный препарат", null, Table.Align.LEFT);
        ratingTable.addContainerProperty("rating", Double.class, null, "Оценка", null, Table.Align.LEFT);
        PrincipalEnt principalEnt = dashboardService.getPrincipal(getUsername());
        Set<RatingEnt> set = principalEnt.getRatings();
        ratingTable.setPageLength(getTableSize(set));
        for (RatingEnt ratingEnt: set) {
            ratingTable.addItem(new Object[] { ratingEnt.getDrug().getTitle(), ratingEnt.getRating() }, ratingEnt.getId());
        }
    }
}
