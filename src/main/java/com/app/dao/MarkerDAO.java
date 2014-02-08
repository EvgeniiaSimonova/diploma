package com.app.dao;

import com.app.entity.DrugEnt;
import com.app.entity.MarkerEnt;
import com.app.entity.PrincipalEnt;
import com.app.exception.BusinessException;

public interface MarkerDAO {
    public MarkerEnt getMarker(final Long id);
    public MarkerEnt getMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt);
    public MarkerEnt addMarker(final MarkerEnt marker);
    public MarkerEnt addMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt);
    public void deleteMarker(final PrincipalEnt principalEnt, final DrugEnt drugEnt) throws BusinessException;
}
