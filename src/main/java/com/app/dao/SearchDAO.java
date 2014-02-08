package com.app.dao;

import com.app.entity.DrugEnt;
import com.app.entity.FirmEnt;
import com.app.entity.InternationalNonProprietaryNameEnt;

import java.util.List;

public interface SearchDAO {
    public List<DrugEnt> searchDrugs(final InternationalNonProprietaryNameEnt inn, final FirmEnt firm, final Double dosage);
}
