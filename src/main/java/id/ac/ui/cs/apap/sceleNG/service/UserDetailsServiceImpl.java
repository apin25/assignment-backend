package id.ac.ui.cs.apap.sceleNG.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tidak digunakan lagi karena semua sudah di-handle di JwtFilter
        throw new UnsupportedOperationException("loadUserByUsername should not be called");
    }
}
