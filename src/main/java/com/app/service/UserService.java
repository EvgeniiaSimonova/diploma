package com.app.service;

import com.app.entity.PrincipalEnt;
import com.app.exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Evgenia Simonova
 */
public class UserService implements UserDetailsService {

    @Autowired
    DashboardService dashboardService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        PrincipalEnt principal = null;
        try {
            principal = dashboardService.getPrincipal(login);
        } catch (SystemException e) {
            System.out.println(e);
            return null;
        }

        if (principal != null) {
            authorities.add(new SimpleGrantedAuthority(principal.getRole().name()));
            User user = new User(principal.getUsername(), new String(principal.getPassword()), true, true, false, false, authorities);
            return user;
        }
        return null;
    }
}
