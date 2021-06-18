package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.EntityListeners;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UtilisateurDTO extends Auditable <String> {

    private Long id;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    private String statut;
    private String tel;
    private SocieteDTO societeDTO;
    private EntiteDTO entiteDTO;
    private ProfilDTO profilDTO;
    private List<RoleDTO> roleDTOList = new ArrayList<>();


    public UtilisateurDTO(String nom, String prenom, String login, String password, String statut, String tel, SocieteDTO societeDTO, EntiteDTO entiteDTO, ProfilDTO profilDTO, List<RoleDTO> roleDTOList) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
        this.statut = statut;
        this.tel = tel;
        this.societeDTO = societeDTO;
        this.entiteDTO = entiteDTO;
        this.profilDTO = profilDTO;
        this.roleDTOList = roleDTOList;
    }

    public String toUpperNom()
    {
        return this.nom.toUpperCase();
    }
    public String toUpperPrenom()
    {
        return this.prenom.toUpperCase();
    }
    public String toUpperLogin()
    {
        return this.login.toUpperCase();
    }
    public String toUpperStatut()
    {
        return this.statut.toUpperCase();
    }
}
