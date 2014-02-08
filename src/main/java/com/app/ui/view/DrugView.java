package com.app.ui.view;

import com.app.entity.DrugEnt;
import com.app.entity.FirmEnt;
import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.ui.validators.IntegerValidator;
import com.app.utils.Constants;
import com.app.utils.NavigationViews;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class DrugView extends AbstractView {

    private VerticalLayout contentLayout = new VerticalLayout();
    private Table drugTable = new Table();
    private FormLayout creationForm = new FormLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        super.enter(event);
        try {
            if (isAdmin()) {
                contentLayout.addComponent(creationForm);
                initForm();
            }

            contentLayout.addComponent(drugTable);
            initTable();
        } catch (SystemException e) {
            setRedirect(NavigationViews.ERROR);
        }
    }

    private void initForm() throws SystemException {
        final TextField titleField = new TextField("Название: ");
        final ComboBox innSelect = new ComboBox("МНН");
        final ComboBox firmSelect = new ComboBox("Производитель");
        final TextField dosageField = new TextField("Дозировка:  ");
        final TextField descriptionField = new TextField("Описание: ");
        Button save = new Button("Сохранить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    titleField.validate();
                    innSelect.validate();
                    firmSelect.validate();
                    dosageField.validate();
                    descriptionField.validate();

                    String title = titleField.getValue();
                    InternationalNonProprietaryNameEnt inn = innSelect.getValue()== null ? null : dashboardService
                            .getInternationalNonProprietaryName((String)innSelect.getValue());
                    FirmEnt firm = firmSelect.getValue()== null ? null : dashboardService.getFirm((String)firmSelect.getValue());
                    Double dosage = dosageField.getValue().isEmpty() ? null : Double.parseDouble(dosageField.getValue());
                    String description = descriptionField.getValue();

                    dashboardService.addDrug(title, inn, firm, dosage, description);
                    Notification.show(Constants.SAVED);
                    setRedirect(NavigationViews.DRUG);
                } catch (Validator.InvalidValueException e) {
                    Notification.show(e.getMessage());
                } catch (Exception  e) {//todo
                    System.out.println(e);
                    setRedirect(NavigationViews.ERROR);
                }
            }
        });

        // set properties
        innSelect.setNullSelectionAllowed(false);
        List<InternationalNonProprietaryNameEnt> innList = dashboardService.getInternationalNonProprietaryNameList();
        for (InternationalNonProprietaryNameEnt inn: innList) {
            innSelect.addItem(inn.getRecommendedName());
        }

        firmSelect.setNullSelectionAllowed(false);
        List<FirmEnt> firmList = dashboardService.getFirmList();
        for (FirmEnt firm: firmList) {
            firmSelect.addItem(firm.getTitle());
        }

        titleField.setRequired(true);
        titleField.setRequiredError("Поле 'Название' должно быть заполнено");
        titleField.setValidationVisible(true);

        dosageField.addValidator(new IntegerValidator("Поле 'Дозировка' должно содержать число"));
        dosageField.setValidationVisible(true);

        // add components
        creationForm.addComponent(titleField);
        creationForm.addComponent(innSelect);
        creationForm.addComponent(firmSelect);
        creationForm.addComponent(dosageField);
        creationForm.addComponent(descriptionField);
        creationForm.addComponent(save);
    }

    private void initTable() throws SystemException {
        drugTable.addContainerProperty("title", Label.class, null, "Название", null, Table.Align.LEFT);
        drugTable.addContainerProperty("inn", ComboBox.class, null, "МНН", null, Table.Align.LEFT);
        drugTable.addContainerProperty("firm", ComboBox.class, null, "Производитель", null, Table.Align.LEFT);
        drugTable.addContainerProperty("dosage", TextField.class, null, "Дозировка", null, Table.Align.LEFT);
        if (isAdmin()) {
            drugTable.addContainerProperty("save", Button.class, null, "Сохранить", null, Table.Align.CENTER);
            drugTable.addContainerProperty("delete", Button.class, null, "Удалить", null, Table.Align.CENTER);
        }
        drugTable.setSizeFull();
        drugTable.setImmediate(true);

        List<DrugEnt> list = dashboardService.getDrugList();
        drugTable.setPageLength((list.size() < Constants.PAGE_SIZE ? list.size() : Constants.PAGE_SIZE));
        for (DrugEnt drug: list) {
            Long id = drug.getId();
            Label titleLabel = new Label(drug.getTitle());

            ComboBox innSelect = new ComboBox();
            innSelect.setNullSelectionAllowed(false);
            List<InternationalNonProprietaryNameEnt> innList = dashboardService.getInternationalNonProprietaryNameList();
            for (InternationalNonProprietaryNameEnt inn: innList) {
                innSelect.addItem(inn.getRecommendedName());
            }
            if (drug.getInternationalNonProprietaryName() !=  null)
            innSelect.setValue(drug.getInternationalNonProprietaryName().getRecommendedName());

            ComboBox firmSelect = new ComboBox();
            firmSelect.setNullSelectionAllowed(false);
            List<FirmEnt> firmList = dashboardService.getFirmList();
            for (FirmEnt firm: firmList) {
                firmSelect.addItem(firm.getTitle());
            }
            if (drug.getFirm() !=  null)
            firmSelect.setValue(drug.getFirm().getTitle());

            TextField dosageField = new TextField();
            dosageField.setValue(drug.getDosage() == null ? "" : drug.getDosage().toString());

            if (isAdmin()) {
                Button save = new Button("ОК");
                save.setData(id);
                save.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Long id = (Long) event.getButton().getData();
                        try {
                            DrugEnt drugEnt = dashboardService.getDrug(id);
                            Item item = drugTable.getItem(id);

                            ComboBox innSelect = (ComboBox)item.getItemProperty("inn").getValue();
                            drugEnt.setInternationalNonProprietaryName(innSelect.getValue()== null ? null : dashboardService
                                    .getInternationalNonProprietaryName((String)innSelect.getValue()));

                            ComboBox firmSelect = (ComboBox)item.getItemProperty("firm").getValue();
                            drugEnt.setFirm(firmSelect.getValue()== null ? null : dashboardService
                                    .getFirm((String)firmSelect.getValue()));

                            TextField dosageField = (TextField) item.getItemProperty("dosage").getValue();
                            drugEnt.setDosage(dosageField.getValue().isEmpty() ? null : Double.parseDouble(dosageField.getValue()));
                            dosageField.validate(); // todo good validation

                            dashboardService.updateDrug(drugEnt);
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
                            dashboardService.deleteDrug(id);
                            Notification.show(Constants.DELETED);
                            setRedirect(NavigationViews.DRUG);
                        } catch (BusinessException e) {
                            Notification.show(e.getMessage());
                        } catch (SystemException e) {
                            setRedirect(NavigationViews.ERROR);
                        }
                    }
                });
                drugTable.addItem(new Object[]{titleLabel, innSelect, firmSelect, dosageField, save, delete}, id);
            } else {
                innSelect.setReadOnly(true);
                firmSelect.setReadOnly(true);
                dosageField.setReadOnly(true);
                drugTable.addItem(new Object[]{titleLabel, innSelect, firmSelect, dosageField}, id);
            }
        }
    }

    @Override
    public Layout getContentLayout() {
        return contentLayout;
    }
}
