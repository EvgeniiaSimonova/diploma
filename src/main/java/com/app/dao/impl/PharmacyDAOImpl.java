package com.app.dao.impl;

import com.app.dao.PharmacyDAO;
import com.app.entity.PharmacyEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("pharmacyDAO")
public class PharmacyDAOImpl implements PharmacyDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public PharmacyEnt getPharmacy(Long id) {
        return (PharmacyEnt)getCurrentSession().get(PharmacyEnt.class, id);
    }

    @Override
    public List<PharmacyEnt> getPharmacy(String name) {
        Query query = getCurrentSession().createQuery("select p from PharmacyEnt as p where p.name = :name");
        query.setParameter("name", name);
        return query.list();
    }

    @Override
    public List<PharmacyEnt> getPharmacyList() {
        Query query = getCurrentSession().createQuery("select p from PharmacyEnt as p");
        return query.list();
    }

    @Override
    public PharmacyEnt addPharmacy(PharmacyEnt pharmacy) {
        getCurrentSession().save(pharmacy);
        return pharmacy;
    }

    @Override
    public PharmacyEnt addPharmacy(String name, String address, String description) {
        PharmacyEnt pharmacyEnt = new PharmacyEnt();
        pharmacyEnt.setName(name);
        pharmacyEnt.setAddress(address);
        pharmacyEnt.setDescription(description);
        return addPharmacy(pharmacyEnt);
    }

    @Override
    public PharmacyEnt updatePharmacy(PharmacyEnt pharmacy) {
        getCurrentSession().update(pharmacy);
        return pharmacy;
    }

    @Override
    public void deletePharmacy(Long id) throws BusinessException {
        PharmacyEnt pharmacyEnt = getPharmacy(id);
        if (pharmacyEnt == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        if (pharmacyEnt.getPrices().isEmpty() && pharmacyEnt.getSales().isEmpty()) {
            getCurrentSession().delete(pharmacyEnt);
        } else {
            throw new BusinessException(ErrorCode.ENTITY_CONTAINS_LINKS);
        }
    }
}
