package com.app.dao.impl;

import com.app.dao.InternationalNonProprietaryNameDAO;
import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("internationalNonProprietaryNameDAO")
public class InternationalNonProprietaryNameDAOImpl implements InternationalNonProprietaryNameDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public InternationalNonProprietaryNameEnt getInternationalNonProprietaryName(String recommendedName) {
        return (InternationalNonProprietaryNameEnt)getCurrentSession()
                .get(InternationalNonProprietaryNameEnt.class, recommendedName);
    }

    @Override
    public List<InternationalNonProprietaryNameEnt> getList() {
        return getCurrentSession().createQuery("select inn from InternationalNonProprietaryNameEnt as inn").list();
    }

    @Override
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(InternationalNonProprietaryNameEnt inn)
            throws BusinessException {
        if (getInternationalNonProprietaryName(inn.getRecommendedName()) == null) {
            getCurrentSession().save(inn);
            return inn;
        } else {
            throw new BusinessException(ErrorCode.INN_THIS_NAME_EXISTS);
        }
    }

    @Override
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(String recommendedName, String synonym,
                                                                                 String group) throws BusinessException {
        InternationalNonProprietaryNameEnt inn = new InternationalNonProprietaryNameEnt();
        inn.setRecommendedName(recommendedName);
        inn.setSynonym(synonym);
        inn.setGroup(group);
        return addInternationalNonProprietaryName(inn);
    }

    @Override
    public InternationalNonProprietaryNameEnt updateInternationalNonProprietaryName(InternationalNonProprietaryNameEnt inn) throws BusinessException {
        getCurrentSession().update(inn);
        return inn;
    }

    @Override
    public void deleteInternationalNonProprietaryName(String recommendedName) throws BusinessException {
        InternationalNonProprietaryNameEnt inn = getInternationalNonProprietaryName(recommendedName);
        if (inn == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        if (inn.getDrugs().isEmpty() && inn.getProbablyInternationalNonProprietaryName().isEmpty()) {
            getCurrentSession().delete(inn);
        } else {
            throw new BusinessException(ErrorCode.ENTITY_CONTAINS_LINKS);
        }
    }
}
