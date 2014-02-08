package com.app.dao.impl;

import com.app.dao.FirmDAO;
import com.app.entity.FirmEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("firmDAO")
public class FirmDAOImpl implements FirmDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public FirmEnt getFirm(String title) {
        return (FirmEnt)getCurrentSession().get(FirmEnt.class, title);
    }

    @Override
    public List<FirmEnt> getFirmList() {
        return getCurrentSession().createQuery("select f from FirmEnt as f").list();
    }

    @Override
    public FirmEnt addFirm(FirmEnt firm) throws BusinessException {
        if (getFirm(firm.getTitle()) == null) {
            getCurrentSession().save(firm);
            return firm;
        } else {
            throw new BusinessException(ErrorCode.FIRM_THIS_NAME_EXISTS);
        }
    }

    @Override
    public FirmEnt addFirm(String title, Integer year, String county) throws BusinessException {
        FirmEnt firmEnt = new FirmEnt();
        firmEnt.setTitle(title);
        firmEnt.setYear(year);
        firmEnt.setCountry(county);
        return addFirm(firmEnt);
    }

    @Override
    public FirmEnt updateFirm(FirmEnt firm) {
        getCurrentSession().update(firm);
        return firm;
    }

    @Override
    public void deleteFirm(String title) throws BusinessException {
        FirmEnt firm = getFirm(title);
        if (firm == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        if (firm.getDrugs().isEmpty()) {
            getCurrentSession().delete(firm);
        } else {
            throw new BusinessException(ErrorCode.ENTITY_CONTAINS_LINKS);
        }
    }
}
