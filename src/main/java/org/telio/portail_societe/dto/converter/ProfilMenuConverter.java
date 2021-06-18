package org.telio.portail_societe.dto.converter;

import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.ProfilMenuDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.ProfilMenu;

import java.util.List;

@Component
public class ProfilMenuConverter implements MapperMethods <ProfilMenuDTO, ProfilMenu> {
    @Override
    public ProfilMenuDTO toVo(ProfilMenu data) {
        return null;
    }

    @Override
    public ProfilMenu toBo(ProfilMenuDTO data) {
        return null;
    }

    @Override
    public List<ProfilMenuDTO> toVoList(List<ProfilMenu> listData) {
        return null;
    }

    @Override
    public List<ProfilMenu> toBoList(List<ProfilMenuDTO> listData) {
        return null;
    }
}
