package org.telio.portail_societe.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.telio.portail_societe.common.Connexion;
import org.telio.portail_societe.common.UserAuthenticated;
import org.telio.portail_societe.config.jwt.JwtTokenUtil;
import org.telio.portail_societe.dao.UtilisateurRepository;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.dto.entities.UtilisateurDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.metier.interfaces.IUserService;
import org.telio.portail_societe.model.Entite;
import org.telio.portail_societe.model.Role;
import org.telio.portail_societe.model.TypeEntite;
import org.telio.portail_societe.model.Utilisateur;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AuthentificationRest {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<ResponseOutput <UserAuthenticated> > createAuthenticationToken(@RequestBody Connexion connexion) throws Exception {
        ResponseOutput <UserAuthenticated> userAuthenticatedResponseOutput = new ResponseOutput<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("name user before login  : " + authentication.getName());
        UserAuthenticated  currentUser = new UserAuthenticated();
        authenticate(connexion.getLogin(), connexion.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(connexion.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);
        currentUser.setToken(token);
        currentUser.setLogin(userDetails.getUsername());
//        currentUser.setRoles();
        userAuthenticatedResponseOutput.setData(currentUser);
        System.out.println("Authorities " + userDetails.getAuthorities());
        Collection<? extends GrantedAuthority> admin = userDetails.getAuthorities() ;
        for (GrantedAuthority rd : admin)
        {
            RoleDTO roleDTO = new RoleDTO(rd.getAuthority());
            currentUser.setRoles(Arrays.asList(roleDTO.getLibele()));
        }

        Utilisateur utilisateur =  utilisateurRepository.findByLogin(connexion.getLogin());
        currentUser.setTypeSociete(utilisateur.getSociete().getTypeSociete().getNom());
        currentUser.setSociete(utilisateur.getSociete().getNom());
        currentUser.setTypeEntite(utilisateur.getEntite().getTypeEntite().getNom());
        TypeEntite typeentitemere= utilisateur.getEntite().getTypeEntite().getTypeMere();
        if(typeentitemere!=null) {
         currentUser.setTypeEntiteMere(utilisateur.getEntite().getTypeEntite().getTypeMere().getNom());
        }
        currentUser.setEntite(utilisateur.getEntite().getNom());
        Entite entitemere= utilisateur.getEntite().getEntiteMere();

        if(entitemere!=null) {
            currentUser.setEntiteMere(utilisateur.getEntite().getEntiteMere().getNom());
           }


        currentUser.setEmail(utilisateur.getLogin());
        currentUser.setProfilName(utilisateur.getProfil().getNom());


        return new ResponseEntity<>(userAuthenticatedResponseOutput, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/admin/he")
    public String hello ()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("connected : " + authentication.getName());
        return authentication.getName() + " -- > " + authentication.getPrincipal();
    }
}

