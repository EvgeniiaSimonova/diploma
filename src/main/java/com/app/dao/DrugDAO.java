package com.app.dao;

import com.app.entity.DrugEnt;
import com.app.entity.FirmEnt;
import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.exception.BusinessException;

import java.util.List;

public interface DrugDAO {
    public DrugEnt getDrug(final Long id);
    public List<DrugEnt> getDrug(final String title);
    public List<DrugEnt> getDrugList();
    public DrugEnt addDrug(final DrugEnt drug);
    public DrugEnt addDrug(final String title, final InternationalNonProprietaryNameEnt inn, final FirmEnt firm,
                           final Double dosage, final String description);
    public DrugEnt updateDrug(final DrugEnt drug);
    public void deleteDrug(final Long id) throws BusinessException;
}
