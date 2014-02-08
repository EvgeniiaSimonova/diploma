package com.app.ui;

import com.app.ui.view.ErrorView;
import com.app.utils.NavigationViews;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * User: Evgenia Simonova
 */
@PreserveOnRefresh
@Theme("mytheme")
@Title("Main Page")
public class DashboardUI extends UI {

    private ApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {
        System.out.println("!!!DashboardUI!!!");
        WrappedSession session = request.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
        ServletContext servletContext = httpSession.getServletContext();
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        Navigator navigator = new Navigator(this, this);
        for (NavigationViews view: NavigationViews.views) {
            navigator.addView(view.getPath(), view.getViewClass());
        }
        navigator.navigateTo(NavigationViews.MAIN.getPath());
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
