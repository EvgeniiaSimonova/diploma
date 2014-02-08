package com.app.dao.impl;

import com.app.dao.SearchDAO;
import com.app.entity.DrugEnt;
import com.app.entity.FirmEnt;
import com.app.entity.InternationalNonProprietaryNameEnt;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("searchDAO")
public class SearchDAOImpl implements SearchDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public List<DrugEnt> searchDrugs(InternationalNonProprietaryNameEnt inn, FirmEnt firm, Double dosage) {
        StringBuilder queryBuilder = new StringBuilder("select d from DrugEnt as d ");

        Map<String, Object> parameters = new HashMap<String, Object>();
        if (inn != null || firm != null || dosage != null) {
            queryBuilder.append("where ");
            if (inn != null) {
                queryBuilder.append("d.internationalNonProprietaryName = :inn and ");
                parameters.put("inn", inn);
            }

            if (firm != null) {
                queryBuilder.append("d.firm = :firm and ");
                parameters.put("firm", firm);
            }

            if (dosage != null) {
                queryBuilder.append("d.dosage = :dosage and ");
                parameters.put("dosage", dosage);
            }

            queryBuilder.delete(queryBuilder.length() - 4, queryBuilder.length() - 1);
        }
        Query query = getCurrentSession().createQuery(queryBuilder.toString());
        for (String key: parameters.keySet()) {
            query.setParameter(key, parameters.get(key));
        }
        return query.list();
    }
}
