package org.example.healthcare.service;


import org.example.healthcare.repository.UserRedpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CostumUserDetailsServices implements UserDetailsService {


    @Autowired
    private UserRedpository userRedpository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return  userRedpository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Utilisateur n'exist pas !!"));
    }
}
