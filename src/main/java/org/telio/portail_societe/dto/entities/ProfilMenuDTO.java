package org.telio.portail_societe.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class ProfilMenuDTO extends Auditable<String>{

    private Date dateDebut;
    private Date dateFin;
    private MenuDTO menuDTO;
    private ProfilDTO profilDTO;
    
    




}
