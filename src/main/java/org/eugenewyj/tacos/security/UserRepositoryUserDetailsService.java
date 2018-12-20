package org.eugenewyj.tacos.security;

import org.eugenewyj.tacos.User;
import org.eugenewyj.tacos.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserRepositoryUserDetailsService
 *
 * @author eugene
 * @date 2018/12/20
 */
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("用户 '" + username + "' 不存在！");
    }
}
