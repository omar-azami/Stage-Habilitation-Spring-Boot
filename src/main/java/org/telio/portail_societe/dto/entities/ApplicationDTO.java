package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.EntityListeners;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApplicationDTO extends Auditable <String> {

    private Long id;
    private String nom;
    private String description;
    private SocieteDTO societeDTO;

    public ApplicationDTO(String nom, String description, SocieteDTO societeDTO) {
        this.nom = nom;
        this.description = description;
        this.societeDTO = societeDTO;
    }

    public String toUpperNom()
    {
        return this.nom.toUpperCase();
    }

    public String toUpperDescription()
    {
        return this.description.toUpperCase();
    }


}
