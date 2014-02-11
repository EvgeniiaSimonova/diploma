package com.app.ui.view;

import com.app.entity.Role;
import com.app.service.DashboardService;
import com.app.ui.DashboardUI;
import com.app.ui.form.LoginForm;
import com.app.ui.listener.LogoutListener;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public abstract class AbstractView extends VerticalLayout implements View {

    protected static DashboardService dashboardService;

    protected VerticalLayout headLayout = new VerticalLayout();
    protected HorizontalLayout bodyLayout = new HorizontalLayout();

    protected Layout centralLayout;
    protected VerticalLayout profileLayout = new VerticalLayout();

    protected HorizontalLayout hatLayout = new HorizontalLayout();
    protected HorizontalLayout menuLayout = new HorizontalLayout();

    protected HorizontalLayout topicLayout = new HorizontalLayout();
    protected FormLayout enterLayout = new FormLayout();


    protected List<Link> mainMenuLinks = new ArrayList<Link>();
    protected List<Link> links = new ArrayList<Link>();

    static {
        DashboardUI ui = (DashboardUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        dashboardService = context.getBean(DashboardService.class);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("работает");
        } else {
            System.out.println("отвалилось");
        }
        formEnter();
        formTopic();
        formMenu();
        formHat();
        formProfile();
        formCentral();
        formBody();
        formHead();
        formRoot();
        setStyleName("basic-background");
    }

    protected void setRedirect(NavigationViews view) {
        UI.getCurrent().getNavigator().navigateTo(view.getPath());
    }

    protected void formRoot() {
        removeAllComponents();
        setSizeFull();

        setId("root");
        addComponent(headLayout);
        addComponent(bodyLayout);
        setExpandRatio(headLayout, 3);
        setExpandRatio(bodyLayout, 5);
    }

    protected void formHead() {
        headLayout.removeAllComponents();
        headLayout.setSizeFull();
        headLayout.setStyleName("upper");

        headLayout.addComponent(hatLayout);
        headLayout.addComponent(menuLayout);
        headLayout.setExpandRatio(hatLayout, 2);
        headLayout.setExpandRatio(menuLayout, 1);
    }

    protected void formBody() {
        bodyLayout.removeAllComponents();
        bodyLayout.setSizeFull();
        bodyLayout.addComponent(centralLayout);
        bodyLayout.addComponent(profileLayout);
        bodyLayout.setExpandRatio(centralLayout, 3);
        bodyLayout.setExpandRatio(profileLayout, 1);
    }

    protected void formCentral() {
        centralLayout = getContentLayout();
    }

    protected void formProfile() {
        profileLayout.removeAllComponents();
        setSizeFull();

        links.add(new Link("Страница пользователя", NavigationViews.USER, new Role[] { Role.ADMIN, Role.WORKER, Role.USER }));
        links.add(new Link("Аптеки", NavigationViews.PHARMACY, new Role[] { Role.ADMIN, Role.WORKER, Role.USER }));
        links.add(new Link("Работники", NavigationViews.WORKER, new Role[] { Role.ADMIN }));
        links.add(new Link("История", NavigationViews.HISTORY, new Role[] { Role.ADMIN }));
        links.add(new Link("Цены", NavigationViews.PRICE, new Role[] { Role.ADMIN, Role.WORKER, Role.USER }));
        links.add(new Link("Продажа", NavigationViews.SALE, new Role[] { Role.ADMIN, Role.WORKER }));

        for (final Link link: links) {
            for (Role r: link.getRoles()) {
                if (getUserRole() == r) {
                    Button button = new NativeButton(link.getTitle());
                    button.setSizeFull();
                    button.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            setRedirect(link.getView());
                        }
                    });
                    profileLayout.addComponent(button);
                    break;
                }
            }
        }
    }

    protected void formHat() {
        hatLayout.removeAllComponents();
        hatLayout.setSizeFull();

        hatLayout.addComponent(topicLayout);
        hatLayout.addComponent(enterLayout);
        hatLayout.setExpandRatio(topicLayout, 2);
        hatLayout.setExpandRatio(enterLayout, 1);
    }

    protected void formMenu() {
        menuLayout.removeAllComponents();
        menuLayout.setSizeFull();

        mainMenuLinks.add(new Link("Главная", NavigationViews.MAIN, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("Поиск", NavigationViews.SEARCH, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("МНН", NavigationViews.INN, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("Лекарства", NavigationViews.DRUG, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("Производители", NavigationViews.FIRM, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));

        for (final Link link: mainMenuLinks) {
            Button button = new NativeButton(link.getTitle());
            button.setStyleName("button-style");
            button.setSizeFull();
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    setRedirect(link.getView());
                }
            });
            menuLayout.addComponent(button);
        }
    }

    protected void formTopic() {
        topicLayout.removeAllComponents();
        topicLayout.setSizeFull();
        topicLayout.setStyleName("header");

        Image logo = new Image(null, new ThemeResource("images/8.png"));
        Label title = new Label("Поиск препарата по его МНН");
        topicLayout.addComponent(logo);
        topicLayout.addComponent(title);
    }

    protected void formEnter() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Button logout = new Button("Выйти");
            LogoutListener logoutListener = new LogoutListener();
            logout.addClickListener(logoutListener);
            enterLayout = new FormLayout();
            enterLayout.addComponent(logout);
        } else {
            enterLayout = new LoginForm();
        }

        enterLayout.setSizeFull();
        enterLayout.setStyleName("logger");
    }

    protected void setRedirect(NavigationViews view, Map<String, String> parameters) {
        if (parameters.isEmpty()) {
            setRedirect(view);
        } else {
            StringBuilder builder = new StringBuilder("?");
            for (String key: parameters.keySet()) {
                builder.append(key);
                builder.append("=");
                builder.append(parameters.get(key));
                builder.append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
            UI.getCurrent().getNavigator().navigateTo(view.getPath() + builder.toString());
        }
    }

    protected String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    protected Role getUserRole() {
        if (isAdmin()) return Role.ADMIN;
        if (isUser()) return Role.USER;
        if (isWorker()) return Role.WORKER;
        return Role.GUEST;
    }

    protected boolean isGuest() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return true;
        }
        return false;
    }

    protected boolean isAdmin() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority ga: authorities) {
                String authority = ga.getAuthority();
                if (Role.ADMIN.name().equals(authority)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isWorker() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority ga: authorities) {
                String authority = ga.getAuthority();
                if (Role.WORKER.name().equals(authority)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority ga: authorities) {
                String authority = ga.getAuthority();
                if (Role.USER.name().equals(authority)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int getTableSize(Collection list) {
        return (list.size() < Constants.PAGE_SIZE ? list.size() + 1 : Constants.PAGE_SIZE);
    }

    public abstract Layout getContentLayout();

    private class Link {
        private String title;
        private NavigationViews view;
        private Set<Role> roles;

        private Link(String title, NavigationViews view, Role[] roles) {
            this.title = title;
            this.view = view;
            this.roles = new HashSet<Role>();
            for (Role r: roles) {
                this.roles.add(r);
            }
        }

        public String getTitle() {
            return title;
        }

        public NavigationViews getView() {
            return view;
        }

        public Set<Role> getRoles() {
            return roles;
        }
    }
}
