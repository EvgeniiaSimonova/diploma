package com.app.dao;

import com.app.entity.DrugEnt;
import com.app.entity.PharmacyEnt;
import com.app.entity.SaleEnt;
import com.app.exception.BusinessException;

import java.util.Date;
import java.util.List;

public interface SaleDAO {
    public SaleEnt addSale(final SaleEnt sale);
    public SaleEnt addSale(final Date date, final PharmacyEnt pharmacy, final DrugEnt drug, final Integer count)
            throws BusinessException;
    public SaleEnt getSale(final Long id);
    public List<SaleEnt> getSaleList();
    public SaleEnt updateSale(final SaleEnt saleEnt);
    public void deleteSale(Long id) throws BusinessException;
}
