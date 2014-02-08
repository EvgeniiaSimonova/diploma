package com.app.dao.impl;

import com.app.dao.DrugDAO;
import com.app.entity.DrugEnt;
import com.app.entity.FirmEnt;
import com.app.entity.InternationalNonProprietaryNameEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("drugDAO")
public class DrugDAOImpl implements DrugDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public DrugEnt getDrug(Long id) {
        return (DrugEnt) getCurrentSession().get(DrugEnt.class, id);
    }

    @Override
    public List<DrugEnt> getDrug(String title) {
        Query query = getCurrentSession().createQuery("select d from DrugEnt as d where d.title = :title");
        query.setParameter("title", title);
        return query.list();
    }

    @Override
    public List<DrugEnt> getDrugList() {
        Query query = getCurrentSession().createQuery("select d from DrugEnt as d");
        return query.list();
    }

    @Override
    public DrugEnt addDrug(DrugEnt drug) {
        getCurrentSession().save(drug);
        return drug;
    }

    @Override
    public DrugEnt addDrug(String title, InternationalNonProprietaryNameEnt inn, FirmEnt firm, Double dosage, String description) {
        DrugEnt drugEnt = new DrugEnt();
        drugEnt.setTitle(title);
        drugEnt.setInternationalNonProprietaryName(inn);
        drugEnt.setFirm(firm);
        drugEnt.setDosage(dosage);
        drugEnt.setDescription(description);
        return addDrug(drugEnt) ;
    }

    @Override
    public DrugEnt updateDrug(DrugEnt drug) {
        getCurrentSession().update(drug);
        return drug;
    }

    @Override
    public void deleteDrug(Long id) throws BusinessException {
        DrugEnt drug = getDrug(id);
        if (drug == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        if (drug.getMarkers().isEmpty() && drug.getPrices().isEmpty()
                && drug.getRatings().isEmpty() && drug.getSales().isEmpty()) {
            getCurrentSession().delete(drug);
        } else {
            throw new BusinessException(ErrorCode.ENTITY_CONTAINS_LINKS);
        }
    }
}
