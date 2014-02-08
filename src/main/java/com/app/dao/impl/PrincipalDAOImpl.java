package com.app.dao.impl;

import com.app.dao.PrincipalDAO;
import com.app.entity.PrincipalEnt;
import com.app.entity.Role;
import com.app.exception.BusinessException;
import com.app.utils.ErrorCode;
import com.app.utils.HashUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: Evgenia Simonova
 */
@Repository("principalDAO")
public class PrincipalDAOImpl implements PrincipalDAO {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public PrincipalEnt getPrincipal(String username) {
        return (PrincipalEnt)getCurrentSession().get(PrincipalEnt.class, username);
    }

    @Override
    public List<PrincipalEnt> getPrincipalList() {
        return getCurrentSession().createQuery("select p from PrincipalEnt as p").list();
    }

    @Override
    public List<PrincipalEnt> getPrincipalList(Role role) {
        return getCurrentSession().createQuery("select p from PrincipalEnt as p where p.role = :role")
                .setParameter("role", role).list();
    }

    @Override
    public PrincipalEnt addPrincipal(PrincipalEnt principal) throws BusinessException {
        if (getPrincipal(principal.getUsername()) == null) {
            getCurrentSession().save(principal);
            return principal;
        } else {
            throw new BusinessException(ErrorCode.USER_THIS_NAME_EXISTS);
        }
    }

    @Override
    public PrincipalEnt addPrincipal(final String username, final String password, final String email, final Role role)
            throws BusinessException {
        PrincipalEnt principalEnt = new PrincipalEnt();
        principalEnt.setUsername(username);
        principalEnt.setPassword(HashUtil.sha512(password));
        principalEnt.setEmail(email);
        principalEnt.setRole(role);
        addPrincipal(principalEnt);
        return principalEnt;
    }

    @Override
    public void deletePrincipal(String username) throws BusinessException {
        PrincipalEnt principalEnt = getPrincipal(username);
        if (principalEnt == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_ENTITY);
        }
        getCurrentSession().delete(principalEnt);
    }
}
