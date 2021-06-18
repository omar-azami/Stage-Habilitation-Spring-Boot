package org.telio.portail_societe.idClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeEntiteID implements Serializable {

    private Long id;
    private Long societe;
}
