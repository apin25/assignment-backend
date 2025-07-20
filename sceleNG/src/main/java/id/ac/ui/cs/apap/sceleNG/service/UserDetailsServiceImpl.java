package id.ac.ui.cs.apap.sceleNG.service;

import id.ac.ui.cs.apap.sceleNG.model.Authentication;
import id.ac.ui.cs.apap.sceleNG.repository.AuthenticationDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthenticationDb authenticationDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication auth = authenticationDb.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                auth.getUsername(),
                auth.getPassword(),
                Collections.emptyList() 
        );
    }
}
