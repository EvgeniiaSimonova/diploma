package com.app.dao;

import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.exception.BusinessException;

import java.util.List;

public interface InternationalNonProprietaryNameDAO {
    public InternationalNonProprietaryNameEnt getInternationalNonProprietaryName(final String recommendedName);
    public List<InternationalNonProprietaryNameEnt> getList();
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(final InternationalNonProprietaryNameEnt inn) throws BusinessException;
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(final String recommendedName,
                                                                                 final String synonym, final String group) throws BusinessException;
    public InternationalNonProprietaryNameEnt updateInternationalNonProprietaryName(final InternationalNonProprietaryNameEnt inn) throws BusinessException;
    public void deleteInternationalNonProprietaryName(String recommendedName) throws BusinessException;
}
