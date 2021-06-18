package org.telio.portail_societe.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilMenuDTO {

    private Date dateDebut;
    private Date dateFin;
    private MenuDTO menu;
    private ProfilDTO profil;




}
