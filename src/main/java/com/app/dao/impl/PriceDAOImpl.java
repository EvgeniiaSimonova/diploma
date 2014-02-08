package com.app.dao.impl;

import com.app.dao.PriceDAO;
import com.app.entity.DrugEnt;
import com.app.entity.PharmacyEnt;
import com.app.entity.PriceEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("priceDAO")
public class PriceDAOImpl implements PriceDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public PriceEnt addPrice(PriceEnt price) {
        getCurrentSession().save(price);
        return price;
    }

    @Override
    public PriceEnt addPrice(PharmacyEnt pharmacy, DrugEnt drug, Double price) {
        PriceEnt priceEnt = new PriceEnt();
        priceEnt.setPharmacy(pharmacy);
        priceEnt.setDrug(drug);
        priceEnt.setPrice(price);
        return addPrice(priceEnt);
    }

    public PriceEnt getPrice(Long id) {
        return (PriceEnt)getCurrentSession().get(PriceEnt.class, id);
    }

    @Override
    public PriceEnt getPrice(PharmacyEnt pharmacy, DrugEnt drug) {
        return (PriceEnt) getCurrentSession().createQuery("select p from PriceEnt as p where p.drug = :drug and p.pharmacy = :pharmacy")
                .setParameter("drug", drug)
                .setParameter("pharmacy", pharmacy).uniqueResult();
    }

    @Override
    public List<PriceEnt> getPriceList() {
        return getCurrentSession().createQuery("select p from PriceEnt as p").list();
    }

    @Override
    public PriceEnt updatePrice(PriceEnt priceEnt) {
        getCurrentSession().update(priceEnt);
        return priceEnt;
    }

    @Override
    public void deletePrice(Long id) throws BusinessException {
        PriceEnt price = getPrice(id);
        if (price == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        getCurrentSession().delete(price);
    }
}
