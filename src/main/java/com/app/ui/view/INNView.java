package com.app.ui.view;

import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class INNView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table innTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        if (isAdmin()) {
            contentLayout.addComponent(creationForm);
            initForm();
        }
        contentLayout.addComponent(innTable);
        try {
            initTable();
        } catch (SystemException e) {
            setRedirect(NavigationViews.ERROR);
        }
    }

    private void initForm() {
        final TextField recommendedName = new TextField("Рекомендуемое название: ");
        final TextField synonym = new TextField("Синоним: ");
        final TextField group = new TextField("Группировачное название:  ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    recommendedName.validate();
                    synonym.validate();
                    group.validate();
                    dashboardService.addInternationalNonProprietaryName(recommendedName.getValue(), synonym.getValue(), group.getValue());
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.INN);
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (BusinessException e) {
                    Notification.show(e.getMessage());
                } catch (SystemException e) {
                    setRedirect(NavigationViews.ERROR);
                }
            }
        });

        // set properties
        recommendedName.setRequired(true);
        recommendedName.setRequiredError("Поле 'Рекомендуемое название' должно быть заполнено");
        recommendedName.setValidationVisible(true);

        // add components
        creationForm.addComponent(recommendedName);
        creationForm.addComponent(synonym);
        creationForm.addComponent(group);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        innTable.addContainerProperty("recommendedName", Label.class, null, "Рекомендуемое название", null, Table.Align.LEFT);
        innTable.addContainerProperty("synonym", TextField.class, null, "Синоним", null, Table.Align.LEFT);
        innTable.addContainerProperty("group", TextField.class, null, "Группа", null, Table.Align.LEFT);
        if (isAdmin()) {
            innTable.addContainerProperty("save", Button.class, null, "Сохранить", null, Table.Align.CENTER);
            innTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }
        innTable.setSizeFull();
        innTable.setImmediate(true);

        List<InternationalNonProprietaryNameEnt> list = dashboardService.getInternationalNonProprietaryNameList();
        innTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (InternationalNonProprietaryNameEnt inn: list) {
            String id = inn.getRecommendedName();
            Label recommendedNameLabel = new Label(id);

            TextField synonymField = new TextField();
            synonymField.setValue(inn.getSynonym());

            TextField groupField = new TextField();
            groupField.setValue(inn.getGroup());

            if (isAdmin()) {
                Button save = new Button("ОК");
                save.setData(id);
                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String recommendedName = (String) event.getButton().getData();
                        try {
                            InternationalNonProprietaryNameEnt inn = dashboardService.getInternationalNonProprietaryName(recommendedName);
                            Item item = innTable.getItem(recommendedName);

                            TextField synonymField = (TextField)item.getItemProperty("synonym").getValue();
                            inn.setSynonym(synonymField.getValue());

                            TextField groupField = (TextField)item.getItemProperty("group").getValue();
                            inn.setGroup(groupField.getValue());
                            dashboardService.updateInternationalNonProprietaryName(inn);
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
                        String recommendedName = (String) event.getButton().getData();
                        try {
                            dashboardService.deleteInternationalNonProprietaryName(recommendedName);
                            Notification.show(Constants.DELETED);
                            setRedirect(NavigationViews.INN);
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                innTable.addItem(new Object[] { recommendedNameLabel, synonymField, groupField, save, delete }, id);
            } else {
                synonymField.setReadOnly(true);
                groupField.setReadOnly(true);
                innTable.addItem(new Object[] { recommendedNameLabel, synonymField, groupField }, id);
            }
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
