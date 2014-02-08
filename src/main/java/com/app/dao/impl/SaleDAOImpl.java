package com.app.dao.impl;

import com.app.dao.PriceDAO;
import com.app.dao.SaleDAO;
import com.app.entity.DrugEnt;
import com.app.entity.PharmacyEnt;
import com.app.entity.PriceEnt;
import com.app.entity.SaleEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("saleDAO")
public class SaleDAOImpl implements SaleDAO {

    @Autowired
    public SessionFactory sessionFactory;

    @Autowired
    public PriceDAO priceDAO;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public SaleEnt addSale(SaleEnt sale) {
        getCurrentSession().save(sale);
        return sale;
    }

    @Override
    public SaleEnt addSale(Date date, PharmacyEnt pharmacy, DrugEnt drug, Integer count) throws BusinessException {
        SaleEnt saleEnt = new SaleEnt();
        saleEnt.setDate(date);
        saleEnt.setCount(count);
        saleEnt.setDrug(drug);
        saleEnt.setPharmacy(pharmacy);
        PriceEnt priceEnt = priceDAO.getPrice(pharmacy, drug);
        if (priceEnt != null && priceEnt.getPrice() != null) {
            saleEnt.setPrice(priceEnt.getPrice());
        } else {
            throw new BusinessException(ErrorCode.NOT_PRICE);
        }
        return addSale(saleEnt);
    }

    @Override
    public SaleEnt getSale(Long id) {
        return (SaleEnt)getCurrentSession().get(SaleEnt.class, id);
    }

    @Override
    public List<SaleEnt> getSaleList() {
        return getCurrentSession().createQuery("select s from SaleEnt as s").list();
    }

    @Override
    public SaleEnt updateSale(SaleEnt saleEnt) {
        getCurrentSession().update(saleEnt);
        return saleEnt;
    }

    @Override
    public void deleteSale(Long id) throws BusinessException {
        SaleEnt saleEnt = getSale(id);
        if (saleEnt == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        getCurrentSession().delete(saleEnt);
    }
}
