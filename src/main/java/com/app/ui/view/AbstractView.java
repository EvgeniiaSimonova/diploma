package com.app.ui.view;

import com.app.entity.Role;
import com.app.service.DashboardService;
import com.app.ui.DashboardUI;
import com.app.ui.form.*;
import com.app.ui.form.LoginForm;
import com.app.ui.listener.LogoutListener;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public abstract class AbstractView extends VerticalLayout implements View {

    protected static DashboardService dashboardService;

    protected HorizontalLayout headerLayout = new HorizontalLayout();
    protected HorizontalLayout mainMenuLayout = new HorizontalLayout();
    protected HorizontalLayout centerLayout = new HorizontalLayout();
    protected VerticalLayout profileLayout = new VerticalLayout();

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
        root();
        header();
        mainMenu();
        profile();
        center();
        setStyleName("basic-background");
    }

    protected void setRedirect(NavigationViews view) {
        UI.getCurrent().getNavigator().navigateTo(view.getPath());
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

    private void root() {
        removeAllComponents();
        setSizeFull();

        setId("root");
        addComponent(headerLayout);
        addComponent(mainMenuLayout);
        addComponent(centerLayout);
        setExpandRatio(headerLayout, 2);
        setExpandRatio(mainMenuLayout, 1);
        setExpandRatio(centerLayout, 7);
    }

    private void mainMenu() {
        mainMenuLayout.removeAllComponents();
        mainMenuLayout.setSizeFull();

        mainMenuLinks.add(new Link("Главная", NavigationViews.MAIN, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("Поиск", NavigationViews.SEARCH, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("МНН", NavigationViews.INN, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("Лекарства", NavigationViews.DRUG, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));
        mainMenuLinks.add(new Link("Производители", NavigationViews.FIRM, new Role[] { Role.ADMIN, Role.WORKER, Role.USER, Role.GUEST }));

        for (final Link link: mainMenuLinks) {
            Button button = new NativeButton(link.getTitle());
            button.setSizeFull();
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    setRedirect(link.getView());
                }
            });
            mainMenuLayout.addComponent(button);
        }
    }

    private void center() {
        centerLayout.removeAllComponents();
        centerLayout.setSizeFull();

        Layout content = getContentLayout();
        centerLayout.addComponent(content);
        centerLayout.addComponent(profileLayout);
        centerLayout.setExpandRatio(content, 6);
        centerLayout.setExpandRatio(profileLayout, 2);
    }

    private void profile() {
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

    private void header() {
        headerLayout.removeAllComponents();
        headerLayout.setSizeFull();

        VerticalLayout titleLayout = new VerticalLayout();
        titleLayout.addComponent(new Label("Поиск препарата по его МНН"));
        titleLayout.setSizeFull();
        titleLayout.setStyleName("header");

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Button logout = new Button("Выйти");
            LogoutListener logoutListener = new LogoutListener();
            logout.addClickListener(logoutListener);
            headerLayout.addComponent(titleLayout);
            headerLayout.addComponent(logout);
            headerLayout.setExpandRatio(titleLayout, 2);
            headerLayout.setExpandRatio(logout, 1);
        } else {
            com.app.ui.form.LoginForm loginForm = new com.app.ui.form.LoginForm();
            loginForm.setStyleName("logger");
            headerLayout.addComponent(titleLayout);
            headerLayout.addComponent(loginForm);
            headerLayout.setExpandRatio(titleLayout, 2);
            headerLayout.setExpandRatio(loginForm, 1);
        }
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
