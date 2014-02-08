package com.app.service;

import com.app.entity.*;
import com.app.exception.BusinessException;
import com.app.exception.SystemException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: Evgenia Simonova
 */
public interface DashboardService {

    // PrincipalEnt
    public PrincipalEnt getPrincipal(final String username) throws SystemException;
    public List<PrincipalEnt> getPrincipalList() throws SystemException;
    public List<PrincipalEnt> getPrincipalList(final Role role) throws SystemException;
    public PrincipalEnt addPrincipal(final PrincipalEnt principal) throws BusinessException, SystemException;
    public PrincipalEnt addPrincipal(final String username, final String password, final String email, final Role role) throws BusinessException, SystemException;
    public void deletePrincipal(final String username) throws BusinessException, SystemException;
    // INN
    public InternationalNonProprietaryNameEnt getInternationalNonProprietaryName(final String recommendedName) throws SystemException;
    public List<InternationalNonProprietaryNameEnt> getInternationalNonProprietaryNameList() throws SystemException;
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(final InternationalNonProprietaryNameEnt inn) throws BusinessException, SystemException;
    public InternationalNonProprietaryNameEnt addInternationalNonProprietaryName(final String recommendedName,
        final String synonym, final String group) throws BusinessException, SystemException;
    public InternationalNonProprietaryNameEnt updateInternationalNonProprietaryName(final InternationalNonProprietaryNameEnt inn) throws SystemException;
    public void deleteInternationalNonProprietaryName(final String recommendedName) throws BusinessException, SystemException;

    // Firm
    public FirmEnt getFirm(final String title) throws SystemException;
    public List<FirmEnt> getFirmList() throws SystemException;
    public FirmEnt addFirm(final FirmEnt firm) throws BusinessException, SystemException;
    public FirmEnt addFirm(final String title, final Integer year, final String county) throws BusinessException, SystemException;
    public FirmEnt updateFirm(final FirmEnt firm) throws SystemException;
    public void deleteFirm(final String title) throws BusinessException, SystemException;

    // Drug
    public DrugEnt getDrug(final Long id) throws SystemException;
    public List<DrugEnt> getDrug(final String title) throws SystemException;
    public List<DrugEnt> getDrugList() throws SystemException;
    public DrugEnt addDrug(final DrugEnt drug) throws SystemException;
    public DrugEnt addDrug(final String title, final InternationalNonProprietaryNameEnt inn, final FirmEnt firm,
                           final Double dosage, final String description) throws SystemException;
    public DrugEnt updateDrug(final DrugEnt drug) throws SystemException;
    public void deleteDrug(final Long id) throws BusinessException, SystemException;

    // Marker
    public MarkerEnt getMarker(final Long id) throws SystemException;
    public MarkerEnt getMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt) throws SystemException;
    public MarkerEnt addMarker(final MarkerEnt marker) throws SystemException;
    public MarkerEnt addMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt) throws SystemException;
    public void deleteMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt) throws BusinessException, SystemException;

    // Rating
    public RatingEnt getRating(final Long id);
    public RatingEnt getRating(final PrincipalEnt principal);
    public RatingEnt getRating(final DrugEnt drug);
    public RatingEnt addRating(final RatingEnt rating);
    public RatingEnt addRating(final PrincipalEnt principal, final DrugEnt drug, final Double rating);

    // Pharmacy
    public PharmacyEnt getPharmacy(final Long id) throws SystemException;
    public List<PharmacyEnt> getPharmacy(final String name) throws SystemException;
    public List<PharmacyEnt> getPharmacyList() throws SystemException;
    public PharmacyEnt addPharmacy(final PharmacyEnt pharmacy) throws SystemException;
    public PharmacyEnt addPharmacy(final String name, final String address, final String description) throws SystemException;
    public PharmacyEnt updatePharmacy(final PharmacyEnt pharmacy) throws SystemException;
    public void deletePharmacy(final Long id) throws BusinessException, SystemException;

    // Price
    public PriceEnt addPrice(final PriceEnt price) throws SystemException;
    public PriceEnt addPrice(final PharmacyEnt pharmacy, final DrugEnt drug, final Double price) throws SystemException;
    public PriceEnt getPrice(Long id) throws SystemException;
    public List<PriceEnt> getPriceList() throws SystemException;
    public PriceEnt updatePrice(final PriceEnt priceEnt) throws SystemException;
    public void deletePrice(final Long id) throws BusinessException, SystemException;

    // History
    public List<HistoryEnt> getHistoryList() throws SystemException;

    // Sale
    public SaleEnt addSale(final SaleEnt sale) throws SystemException;
    public SaleEnt addSale(final Date date, final PharmacyEnt pharmacy, final DrugEnt drug, final Integer count)
            throws SystemException, BusinessException;
    public SaleEnt getSale(final Long id) throws SystemException;
    public List<SaleEnt> getSaleList() throws SystemException;
    public SaleEnt updateSale(final SaleEnt saleEnt) throws SystemException;
    public void deleteSale(Long id) throws BusinessException, SystemException;

    // Search
    public List<DrugEnt> searchDrugEnt(final InternationalNonProprietaryNameEnt inn, final FirmEnt firm,
                                       final Double dosage) throws SystemException;
}
