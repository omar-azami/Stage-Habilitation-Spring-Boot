package org.telio.portail_societe.idClass;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurID implements Serializable {

    private Long id;
    private Long societe;
}
