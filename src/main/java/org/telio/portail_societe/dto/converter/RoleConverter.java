package org.telio.portail_societe.dto.converter;

import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Role;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleConverter implements MapperMethods <RoleDTO, Role> {

    @Override
    public RoleDTO toVo(Role data) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(data.getId());
        roleDTO.setLibele(data.getLibele());
        return roleDTO;
    }

    @Override
    public Role toBo(RoleDTO data) {
        Role role = new Role();
        role.setId(data.getId());
        role.setLibele(data.getLibele());
        return role;
    }

    @Override
    public List<RoleDTO> toVoList(List<Role> listData) {
        List <RoleDTO> roleDTOS = new ArrayList<>();
        if (listData != null)
        {
            listData.forEach( el -> roleDTOS.add(toVo(el)));
        }
        return roleDTOS;
    }

    @Override
    public List<Role> toBoList(List<RoleDTO> listData) {
        List <Role> roles = new ArrayList<>();
        if (listData != null)
        {
            listData.forEach( el -> roles.add(toBo(el)));
        }
        return roles;
    }
}
