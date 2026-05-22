package org.example.healthcare.service;


import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CostumUserDetailsServices implements UserDetailsService {


    @Autowired
    private UserRedpository userRedpository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRedpository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Utilisateur n'exist pas !!"));

        return user;
    }
}
