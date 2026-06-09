package org.example.healthcare.service;

import org.example.healthcare.dto.auth.UserResquestDTO;
import org.example.healthcare.enums.UserRoles;
import org.example.healthcare.model.Admin;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.Patient;
import org.example.healthcare.model.User;
import org.example.healthcare.repository.UserRedpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRedpository userRedpository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private JwtService jwtService;


     public User regisrer(UserResquestDTO user){
         User user_result;
         if (user.getRole().equals(UserRoles.PATIENT)){
             if (user.getPatientDTO() == null) {
                 throw new IllegalArgumentException("Les informations du patient sont obligatoires.");
             }
             Patient patient = new Patient();
             patient.setUserName(user.getUserName());
             patient.setEmail(user.getEmail());
             patient.setPassword(passwordEncoder.encode(user.getPassword()));
             patient.setNom(user.getPatientDTO().getNom());
             patient.setPrenom(user.getPatientDTO().getPrenom());
             patient.setTelephone( user.getPatientDTO().getTelephone());
             patient.setDateNaissance(user.getPatientDTO().getDateNaissance());
             user_result = userRedpository.save(patient);
         }
          else if (user.getRole().equals(UserRoles.MEDECIN)){
             if (user.getMedecinDTO() == null) {
                 throw new IllegalArgumentException("Les informations du médecin sont obligatoires.");
             }
             Medecin medecin = new Medecin();
             medecin.setUserName(user.getUserName());
             medecin.setEmail(user.getEmail());
             medecin.setPassword(passwordEncoder.encode(user.getPassword()));
             medecin.setNom(user.getMedecinDTO().getNom());
             medecin.setPrenom(user.getMedecinDTO().getPrenom());
             medecin.setSpecialite(user.getMedecinDTO().getSpecialite());
             user_result = userRedpository.save(medecin);
         }
          else {
             Admin admin = new Admin();
             admin.setUserName(user.getUserName());
             admin.setEmail(user.getEmail());
             admin.setPassword(passwordEncoder.encode(user.getPassword()));
             user_result = userRedpository.save(admin);
         }
          return user_result;
     }


    public String login(String email,String password) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        UserDetails user = (UserDetails) auth.getPrincipal();

        return jwtService.generateToken(user);
    }
}
