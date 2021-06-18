package org.telio.portail_societe.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;
    private String libele;

    public RoleDTO(String libele) {
        this.libele = libele;
    }
}
