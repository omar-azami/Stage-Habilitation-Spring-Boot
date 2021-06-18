package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.model.TypeSociete;

import javax.persistence.EntityListeners;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SocieteDTO extends Auditable <String> {

    private Long id;
    private String nom;
    private String code;
    private String nomAbrege;
    private String statut;
    private TypeSocieteDTO typeSocieteDTO;

    public SocieteDTO(String nom, String code, String nomAbrege, String statut, TypeSocieteDTO typeSocieteDTO) {
        this.nom = nom;
        this.code = code;
        this.nomAbrege = nomAbrege;
        this.statut = statut;
        this.typeSocieteDTO = typeSocieteDTO;
    }

    public String toUpperNom()
    {
        return this.nom.toUpperCase();
    }
    public String toUpperCode()
    {
        return this.code.toUpperCase();
    }
    public String toUpperNomAbrege()
    {
        return this.nomAbrege.toUpperCase();
    }

    public String toUpperStatut(){return this.statut.toUpperCase();}
}
