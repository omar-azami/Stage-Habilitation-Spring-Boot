package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.UtilisateurDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

@Component
public class UtilisateurConverter implements MapperMethods <UtilisateurDTO, Utilisateur> {

    @Autowired
    private SocieteConverter societeConverter;
    @Autowired
    private EntiteConverter entiteConverter;
    @Autowired
    private ProfilConverter profilConverter;
    @Autowired
    private RoleConverter roleConverter;

    @Override
    public UtilisateurDTO toVo(Utilisateur data) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setId(data.getId());
        utilisateurDTO.setNom(data.getNom());
        utilisateurDTO.setPrenom(data.getPrenom());
        utilisateurDTO.setLogin(data.getLogin());
        utilisateurDTO.setPassword(data.getPassword());
        utilisateurDTO.setStatut(data.getStatut());
        utilisateurDTO.setTel(data.getTel());
        utilisateurDTO.setCreatedBy(data.getCreatedBy());
        utilisateurDTO.setCreatedDate(data.getCreatedDate());
        utilisateurDTO.setLastModifiedBy(data.getLastModifiedBy());
        utilisateurDTO.setLastModifedDate(data.getLastModifedDate());
        utilisateurDTO.setSocieteDTO(societeConverter.toVo(data.getSociete()));
        utilisateurDTO.setEntiteDTO(entiteConverter.toVo(data.getEntite()));
        utilisateurDTO.setProfilDTO(profilConverter.toVo(data.getProfil()));
        utilisateurDTO.setRoleDTOList(roleConverter.toVoList(data.getRoles()));
        return utilisateurDTO;
    }

    @Override
    public Utilisateur toBo(UtilisateurDTO data) {
        Utilisateur utilisateur  = new Utilisateur();
        utilisateur.setId(data.getId());
        utilisateur.setNom(data.toUpperNom());
        utilisateur.setPrenom(data.toUpperPrenom());
        utilisateur.setLogin(data.getLogin());
        utilisateur.setPassword(data.getPassword());
        utilisateur.setStatut(data.getStatut());
        utilisateur.setTel(data.getTel());
        utilisateur.setCreatedBy(data.getCreatedBy());
        utilisateur.setCreatedDate(data.getCreatedDate());
        utilisateur.setLastModifiedBy(data.getLastModifiedBy());
        utilisateur.setLastModifedDate(data.getLastModifedDate());
        utilisateur.setSociete(societeConverter.toBo(data.getSocieteDTO()));
        utilisateur.setEntite(entiteConverter.toBo(data.getEntiteDTO()));
        utilisateur.setProfil(profilConverter.toBo(data.getProfilDTO()));
        utilisateur.setRoles(roleConverter.toBoList(data.getRoleDTOList()));
        return utilisateur;
    }

    @Override
    public List<UtilisateurDTO> toVoList(List<Utilisateur> listData) {
        List<UtilisateurDTO> utilisateurDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->utilisateurDTOS.add(toVo(data)));
        return utilisateurDTOS;
    }

    @Override
    public List<Utilisateur> toBoList(List<UtilisateurDTO> listData) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        if(listData != null) listData.forEach(data ->utilisateurs.add(toBo(data)));
        return utilisateurs;
    }
}
