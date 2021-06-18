package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.model.Localite;

import javax.persistence.EntityListeners;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EntiteDTO extends Auditable <String> {

    private Long id;
    private String nom;
    private String code;
    private EntiteDTO entiteMereDTO;
    private LocaliteDTO localiteDTO;
    private SocieteDTO societeDTO;
    private TypeEntiteDTO typeEntiteDTO;


    public EntiteDTO(String nom, String code, EntiteDTO entiteMereDTO, LocaliteDTO localiteDTO, SocieteDTO societeDTO, TypeEntiteDTO typeEntiteDTO) {
        this.nom = nom;
        this.code = code;
        this.entiteMereDTO = entiteMereDTO;
        this.localiteDTO = localiteDTO;
        this.societeDTO = societeDTO;
        this.typeEntiteDTO = typeEntiteDTO;
    }



    public String toUpperNom()
    {
        return this.nom.toUpperCase();
    }

    public String toUpperCode()
    {
        return this.code.toUpperCase();
    }

}

