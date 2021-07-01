package org.telio.portail_societe.idClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuID implements Serializable {

    private Long id;
    private ApplicationID application;

}
