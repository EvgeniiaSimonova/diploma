package com.app.dao;

import com.app.entity.DrugEnt;
import com.app.entity.PharmacyEnt;
import com.app.entity.PriceEnt;
import com.app.exception.BusinessException;

import java.util.List;

public interface PriceDAO {
    public PriceEnt addPrice(final PriceEnt price);
    public PriceEnt addPrice(final PharmacyEnt pharmacy, final DrugEnt drug, final Double price);
    public PriceEnt getPrice(final Long id);
    public PriceEnt getPrice(final PharmacyEnt pharmacy, final DrugEnt drug);
    public List<PriceEnt> getPriceList();
    public PriceEnt updatePrice(final PriceEnt priceEnt);
    public void deletePrice(final Long id) throws BusinessException;
}
