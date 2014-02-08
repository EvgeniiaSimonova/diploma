package com.app.service.impl;

import com.app.dao.*;
import com.app.entity.*;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import com.app.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: Evgenia Simonova
 */
@Service("dashboardService")
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private PrincipalDAO principalDAO;

    @Autowired
    private InternationalNonProprietaryNameDAO internationalNonProprietaryNameDAO;

    @Autowired
    private FirmDAO firmDAO;

    @Autowired
    private DrugDAO drugDAO;

    @Autowired
    private PharmacyDAO pharmacyDAO;

    @Autowired
    private HistoryDAO historyDAO;

    @Autowired
    private PriceDAO priceDAO;

    @Autowired
    private SaleDAO saleDAO;

    @Autowired
    private MarkerDAO markerDAO;

    @Autowired
    private SearchDAO searchDAO;

    public PrincipalEnt getPrincipal(String username) throws SystemException {
        try {
            return principalDAO.getPrincipal(username);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<PrincipalEnt> getPrincipalList() throws SystemException {
        try {
            return principalDAO.getPrincipalList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<PrincipalEnt> getPrincipalList(Role role) throws SystemException {
        try {
            return principalDAO.getPrincipalList(role);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PrincipalEnt addPrincipal(PrincipalEnt principal) throws BusinessException, SystemException {
        try {
            return principalDAO.addPrincipal(principal);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PrincipalEnt addPrincipal(String username, String password, String email, Role role)
            throws BusinessException, SystemException {
        try {
            return principalDAO.addPrincipal(username, password, email, role);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deletePrincipal(String username) throws BusinessException, SystemException {
        try {
            principalDAO.deletePrincipal(username);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public InternationalNonProprietaryNameEnt getInternationalNonProprietaryName(String recommendedName) throws SystemException {
        try {
            return internationalNonProprietaryNameDAO.getInternationalNonProprietaryName(recommendedName);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<InternationalNonProprietaryNameEnt> getInternationalNonProprietaryNameList() throws SystemException {
        try {
            return internationalNonProprietaryNameDAO.getList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(InternationalNonProprietaryNameEnt inn) throws BusinessException, SystemException {
        try {
            return internationalNonProprietaryNameDAO.addInternationalNonProprietaryName(inn);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(String recommendedName, String synonym, String group) throws BusinessException, SystemException {
        try {
            return internationalNonProprietaryNameDAO.addInternationalNonProprietaryName(recommendedName, synonym, group);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public InternationalNonProprietaryNameEnt updateInternationalNonProprietaryName(InternationalNonProprietaryNameEnt inn) throws SystemException {
        try {
            return internationalNonProprietaryNameDAO.updateInternationalNonProprietaryName(inn);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deleteInternationalNonProprietaryName(String recommendedName) throws BusinessException, SystemException {
        try {
            internationalNonProprietaryNameDAO.deleteInternationalNonProprietaryName(recommendedName);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public FirmEnt getFirm(String title) throws SystemException {
        try {
            return firmDAO.getFirm(title);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<FirmEnt> getFirmList() throws SystemException {
        try {
            return firmDAO.getFirmList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public FirmEnt addFirm(FirmEnt firm) throws BusinessException, SystemException {
        try {
            return firmDAO.addFirm(firm);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public FirmEnt addFirm(String title, Integer year, String county) throws BusinessException, SystemException {
        try {
            return firmDAO.addFirm(title, year, county);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public FirmEnt updateFirm(FirmEnt firm) throws SystemException {
        try {
            return firmDAO.updateFirm(firm);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deleteFirm(String title) throws BusinessException, SystemException {
        try {
            firmDAO.deleteFirm(title);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public DrugEnt getDrug(Long id) throws SystemException {
        try {
            return drugDAO.getDrug(id);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<DrugEnt> getDrug(String title) throws SystemException {
        try {
            return drugDAO.getDrug(title);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<DrugEnt> getDrugList() throws SystemException {
        try {
            return drugDAO.getDrugList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public DrugEnt addDrug(DrugEnt drug) throws SystemException {
        try {
            return drugDAO.addDrug(drug);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public DrugEnt addDrug(String title, InternationalNonProprietaryNameEnt inn, FirmEnt firm, Double dosage, String description) throws SystemException {
        try {
            return drugDAO.addDrug(title, inn, firm, dosage, description);
        } catch (Exception e) {
            System.out.println(e);
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public DrugEnt updateDrug(DrugEnt drug) throws SystemException {
        try {
            return drugDAO.updateDrug(drug);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deleteDrug(Long id) throws BusinessException, SystemException {
        try {
            drugDAO.deleteDrug(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public MarkerEnt getMarker(Long id) throws SystemException {
        try {
            return markerDAO.getMarker(id);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public MarkerEnt getMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt) throws SystemException {
        try {
            return markerDAO.getMarker(principalEnt, drugEnt);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public MarkerEnt addMarker(MarkerEnt marker) throws SystemException {
        try {
            return markerDAO.addMarker(marker);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public MarkerEnt addMarker(PrincipalEnt principal, DrugEnt drug) throws SystemException {
        try {
            return markerDAO.addMarker(principal, drug);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deleteMarker(PrincipalEnt principalEnt, DrugEnt drugEnt) throws BusinessException, SystemException {
        try {
            markerDAO.deleteMarker(principalEnt, drugEnt);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Override
    public RatingEnt getRating(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RatingEnt getRating(PrincipalEnt principal) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RatingEnt getRating(DrugEnt drug) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RatingEnt addRating(RatingEnt rating) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RatingEnt addRating(PrincipalEnt principal, DrugEnt drug, Double rating) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PharmacyEnt getPharmacy(Long id) throws SystemException {
        try {
            return pharmacyDAO.getPharmacy(id);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<PharmacyEnt> getPharmacy(String name) throws SystemException {
        try {
            return pharmacyDAO.getPharmacy(name);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<PharmacyEnt> getPharmacyList() throws SystemException {
        try {
            return pharmacyDAO.getPharmacyList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PharmacyEnt addPharmacy(PharmacyEnt pharmacy) throws SystemException {
        try {
            return pharmacyDAO.addPharmacy(pharmacy);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PharmacyEnt addPharmacy(String name, String address, String description) throws SystemException {
        try {
            return pharmacyDAO.addPharmacy(name, address, description);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PharmacyEnt updatePharmacy(PharmacyEnt pharmacy) throws SystemException {
        try {
            return pharmacyDAO.updatePharmacy(pharmacy);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deletePharmacy(Long id) throws BusinessException, SystemException {
        try {
            pharmacyDAO.deletePharmacy(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PriceEnt addPrice(PriceEnt price) throws SystemException {
        try {
            return priceDAO.addPrice(price);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PriceEnt addPrice(PharmacyEnt pharmacy, DrugEnt drug, Double price) throws SystemException {
        try {
            return priceDAO.addPrice(pharmacy, drug, price);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Override
    public PriceEnt getPrice(Long id) throws SystemException {
        try {
            return priceDAO.getPrice(id);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<PriceEnt> getPriceList() throws SystemException {
        try {
            return priceDAO.getPriceList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public PriceEnt updatePrice(PriceEnt priceEnt) throws SystemException {
        try {
            return priceDAO.updatePrice(priceEnt);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deletePrice(Long id) throws BusinessException, SystemException {
        try {
            priceDAO.deletePrice(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<HistoryEnt> getHistoryList() throws SystemException {
        try {
            return historyDAO.getHistoryList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }


    public SaleEnt addSale(SaleEnt sale) throws SystemException {
        try {
            return saleDAO.addSale(sale);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public SaleEnt addSale(Date date, PharmacyEnt pharmacy, DrugEnt drug, Integer count)
            throws SystemException, BusinessException {
        try {
            return saleDAO.addSale(date, pharmacy, drug, count);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public SaleEnt getSale(Long id) throws SystemException {
        try {
            return saleDAO.getSale(id);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    public List<SaleEnt> getSaleList() throws SystemException {
        try {
            return saleDAO.getSaleList();
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public SaleEnt updateSale(SaleEnt saleEnt) throws SystemException {
        try {
            return saleDAO.updateSale(saleEnt);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public void deleteSale(Long id) throws BusinessException, SystemException {
        try {
            saleDAO.deleteSale(id);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }

    @Override
    public List<DrugEnt> searchDrugEnt(InternationalNonProprietaryNameEnt inn, FirmEnt firm, Double dosage) throws SystemException {
        try {
            return searchDAO.searchDrugs(inn, firm, dosage);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
    }
}
