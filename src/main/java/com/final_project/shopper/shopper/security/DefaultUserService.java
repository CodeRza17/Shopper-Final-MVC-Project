package com.final_project.shopper.shopper.security;

import com.final_project.shopper.shopper.models.User;
import com.final_project.shopper.shopper.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findUser = userRepository.findByEmail(username);
        if (findUser != null){
            org.springframework.security.core.userdetails.User loggedUser = new org.springframework.security.core.userdetails.User(
                    findUser.getEmail(),
                    findUser.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    findUser.getAuthorities()
            );
            return loggedUser;
        }
        throw new UsernameNotFoundException("User not found");
    }
}
