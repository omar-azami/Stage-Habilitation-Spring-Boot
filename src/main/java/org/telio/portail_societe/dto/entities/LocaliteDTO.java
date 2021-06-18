package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.EntityListeners;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LocaliteDTO extends Auditable <String> {

    private Long id;
    private String nom;
    private String code;
    private String nomAbrege;

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

    public LocaliteDTO(String nom, String code, String nomAbrege) {
        this.nom = nom;
        this.code = code;
        this.nomAbrege = nomAbrege;
    }
}
