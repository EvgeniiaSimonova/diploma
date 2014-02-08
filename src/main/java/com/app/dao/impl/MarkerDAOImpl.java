package com.app.dao.impl;

import com.app.dao.MarkerDAO;
import com.app.entity.DrugEnt;
import com.app.entity.MarkerEnt;
import com.app.entity.PrincipalEnt;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("markerDAO")
public class MarkerDAOImpl implements MarkerDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public MarkerEnt getMarker(Long id) {
        return (MarkerEnt)getCurrentSession().get(MarkerEnt.class, id);
    }

    @Override
    public MarkerEnt getMarker(PrincipalEnt principalEnt, DrugEnt drugEnt) {
        List<MarkerEnt> list = getCurrentSession().createQuery("select m from MarkerEnt as m where m.principal = :principal and m.drug = :drug")
                .setParameter("principal", principalEnt)
                .setParameter("drug", drugEnt)
                .list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public MarkerEnt addMarker(MarkerEnt marker) {
        getCurrentSession().save(marker);
        return marker;
    }

    @Override
    public MarkerEnt addMarker(PrincipalEnt principalEnt, DrugEnt drugEnt) {
        MarkerEnt markerEnt = new MarkerEnt();
        markerEnt.setPrincipal(principalEnt);
        markerEnt.setDrug(drugEnt);
        return addMarker(markerEnt);
    }

    @Override
    public void deleteMarker(PrincipalEnt principalEnt, DrugEnt drugEnt) throws BusinessException {
        MarkerEnt markerEnt = getMarker(principalEnt, drugEnt);
        if (markerEnt == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        getCurrentSession().delete(markerEnt);
    }
}
