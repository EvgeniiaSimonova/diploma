package com.app.dao.impl;

import com.app.dao.HistoryDAO;
import com.app.entity.HistoryEnt;
import com.app.exception.SystemException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("historyDAO")
public class HistoryDAOImpl implements HistoryDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<HistoryEnt> getHistoryList() throws SystemException {
        return getCurrentSession().createQuery("select h from HistoryEnt as h").list();
    }
}
