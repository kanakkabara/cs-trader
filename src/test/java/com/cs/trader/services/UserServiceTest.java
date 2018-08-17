package com.cs.trader.services;

import com.cs.trader.domain.User;
import com.cs.trader.exceptions.DuplicateUsernameException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test(expected = DuplicateUsernameException.class)
    public void addNewUserFailsForDuplicateUser(){
        userService.addNewUser(new User("admin", "admin"));
    }

    @Test
    public void addNewUserSuccess(){
        User u = new User("admin2", "admin2");
        List<User.Authority> auths = new ArrayList<>();
        auths.add(User.Authority.ADMIN);
        auths.add(User.Authority.COMPLIANCE_OFFICER);

        u.setAuthorities(auths);
        int uID = userService.addNewUser(u);

        assertEquals("User ID returned is incorrect", uID, 6);
    }

    @Test
    public void getUsernameFetchesValidObjectWithAuthorities(){
        User u = new User("admin3", "admin3");
        List<User.Authority> auths = new ArrayList<>();
        auths.add(User.Authority.ADMIN);
        auths.add(User.Authority.COMPLIANCE_OFFICER);

        u.setAuthorities(auths);
        int uID = userService.addNewUser(u);

        UserDetails user = userService.loadUserByUsername("admin3");
        assertEquals(user.getAuthorities().size(), 2);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(User.Authority.ADMIN.toString())));
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(User.Authority.COMPLIANCE_OFFICER.toString())));
    }
}
