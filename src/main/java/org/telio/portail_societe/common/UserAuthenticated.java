package org.telio.portail_societe.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.model.Societe;

import java.util.List;

@Data
@NoArgsConstructor
public class UserAuthenticated {

    private String login;
    private List<String> roles;
    private String profilName;
    private String email;
    private String token;
    private String entite;
    private Long idEntite;
    private String entiteMere;
    private String societe;
    private Long idSociete;
    private String typeSociete;
    private String typeEntite;
    private Long idTypeEntite;
    private String typeEntiteMere;
    private Societe societee;
}
