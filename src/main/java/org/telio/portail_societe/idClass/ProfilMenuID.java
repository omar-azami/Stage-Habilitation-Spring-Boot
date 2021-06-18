package org.telio.portail_societe.idClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilMenuID implements Serializable {

    private ProfilID profil;
    private MenuID menu;
    private Date dateDebut;
}
