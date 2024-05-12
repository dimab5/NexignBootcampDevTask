package com.brt.services.clients;

import com.brt.models.clients.Client;
import com.brt.repositories.clients.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ClientDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            return new org.springframework.security.core.userdetails.User(
                    username,
                    passwordEncoder.encode("admin"),
                    Arrays.asList(new SimpleGrantedAuthority("MANAGER")));
        }

        Client client = clientRepository.findClientById(username);

        if (client == null) {
            throw new UsernameNotFoundException(
                    String.format("Абонент '%s' не найден", username));
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                passwordEncoder.encode(username),
                Arrays.asList(new SimpleGrantedAuthority("ABONENT")));
    }
}
