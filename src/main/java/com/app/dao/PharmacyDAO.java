package com.app.dao;

import com.app.entity.PharmacyEnt;
import com.app.exception.BusinessException;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PharmacyDAO {
    public PharmacyEnt getPharmacy(final Long id);
    public List<PharmacyEnt> getPharmacy(final String name);
    public List<PharmacyEnt> getPharmacyList();
    public PharmacyEnt addPharmacy(final PharmacyEnt pharmacy);
    public PharmacyEnt addPharmacy(final String name, final String address, final String description);
    public PharmacyEnt updatePharmacy(final PharmacyEnt pharmacy);
    public void deletePharmacy(final Long id) throws BusinessException;
}
