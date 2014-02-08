package com.app.dao;

import com.app.entity.PrincipalEnt;
import com.app.entity.Role;
import com.app.exception.BusinessException;

import java.util.List;

/**
 * User: Evgenia Simonova
 */
public interface PrincipalDAO {
    public PrincipalEnt getPrincipal(final String username);
    public List<PrincipalEnt> getPrincipalList();
    public List<PrincipalEnt> getPrincipalList(final Role role);
    public PrincipalEnt addPrincipal(final PrincipalEnt principal) throws BusinessException;
    public PrincipalEnt addPrincipal(final String username, final String password, final String email, final Role role) throws BusinessException;
    public void deletePrincipal(final String username) throws BusinessException;
}
