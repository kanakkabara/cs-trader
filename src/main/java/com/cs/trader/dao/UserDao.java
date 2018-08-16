package com.cs.trader.dao;

import com.cs.trader.config.WebSecurityConfig;
import com.cs.trader.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.*;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByUsername(String username) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE USERNAME = ?", new UserRowMapper(), username);
        }catch (Exception e){
            throw new UsernameNotFoundException("Could not find username "+username);
        }
    }

    public List<User.Authority> findUserAuthoritiesByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM USER_ROLES WHERE USERNAME = ?", new UserAuthorityMapper(), username);
    }

    public int addNewUser(User user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES(?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getUsername());
                statement.setString(2, passwordEncoder.encode(user.getPassword()));
                statement.setBoolean(3, true);
                return statement;
            }
        }, holder);

        for(User.Authority authority : user.getAuthorities()){
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO USER_ROLES (USERNAME, USER_ROLE) VALUES(?, ?);");
                    statement.setString(1, user.getUsername());
                    statement.setString(2, authority.toString());
                    return statement;
                }
            });
        }

        return holder.getKey().intValue();
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("username"), rs.getString("password"));
        }
    }

    class UserAuthorityMapper implements RowMapper<User.Authority> {
        @Override
        public User.Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.Authority.valueOf(rs.getString("USER_ROLE"));
        }
    }
}
