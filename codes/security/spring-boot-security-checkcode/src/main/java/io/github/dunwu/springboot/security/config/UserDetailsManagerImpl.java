package io.github.dunwu.springboot.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.springboot.security.domain.User;
import io.github.dunwu.springboot.security.mapper.UserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsManagerImpl implements UserDetailsManager {

    private final UserMapper userMapper;

    public UserDetailsManagerImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        QueryWrapper<User> query = new QueryWrapper<>();
        User user = userMapper.selectOne(query.eq("userName", userName));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public void createUser(UserDetails userDetails) {

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return false;
    }

}
