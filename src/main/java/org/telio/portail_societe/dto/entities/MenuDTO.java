package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.EntityListeners;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenuDTO extends Auditable <String> {

    private Long id;
    private String type;
    private String nom;
    private String description;
    private String lien;
    private String parametres;
    private int ordre;
    private ApplicationDTO applicationDTO;
    private MenuDTO menuPereDTO;
    private Set <ProfilDTO> profilDTOS = new HashSet<>();


    public MenuDTO(String type, String nom, String description, String lien, String parametres, int ordre, ApplicationDTO applicationDTO, MenuDTO menuPereDTO) {
        this.type = type;
        this.nom = nom;
        this.description = description;
        this.lien = lien;
        this.parametres = parametres;
        this.ordre = ordre;
        this.applicationDTO = applicationDTO;
        this.menuPereDTO = menuPereDTO;
    }

    public String toUpperNom()
    {
        return this.nom.toUpperCase();
    }

    public String toUpperLien()
    {

        return this.lien.toUpperCase();
    }

    public String toUpperParametres()
    {
        return this.parametres.toUpperCase();
    }

    public String toUpperType()
    {
        return this.type.toUpperCase();
    }

    public String toUpperDescription()
    {
        return this.description.toUpperCase();
    }
}
