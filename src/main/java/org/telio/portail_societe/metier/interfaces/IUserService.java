package org.telio.portail_societe.metier.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.dto.entities.UtilisateurDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.model.Utilisateur;

public interface IUserService extends UserDetailsService {

    ResponseOutput <UtilisateurDTO> persist (UtilisateurDTO utilisateurDTO);
    ResponseOutput <UtilisateurDTO> getAllUtilisateur (String fieldName);
    ResponseOutput <RoleDTO> persist (RoleDTO roleDTO);
    ResponseOutput <RoleDTO> searchRoleByLibele (String libele);
}
