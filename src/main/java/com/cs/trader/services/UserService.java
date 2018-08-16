package com.cs.trader.services;

import com.cs.trader.dao.UserDao;
import com.cs.trader.domain.User;
import com.cs.trader.exceptions.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    public int addNewUser(User user){
        try{
            loadUserByUsername(user.getUsername());
            throw new DuplicateUsernameException("This username already exists");
        }catch (UsernameNotFoundException e){
            return userDao.addNewUser(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    public List<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> auths = new ArrayList<>();
        for(User.Authority auth: userDao.findUserAuthoritiesByUsername(user.getUsername()))
            auths.add(new SimpleGrantedAuthority(auth.toString()));
        return auths;
    }
}
