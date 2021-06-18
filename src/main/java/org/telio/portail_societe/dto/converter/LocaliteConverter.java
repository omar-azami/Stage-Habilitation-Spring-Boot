package org.telio.portail_societe.dto.converter;

import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.LocaliteDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Localite;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocaliteConverter implements MapperMethods <LocaliteDTO, Localite> {

    @Override
    public LocaliteDTO toVo(Localite data) {
        LocaliteDTO localiteDTO = new LocaliteDTO();
        localiteDTO.setId(data.getId());
        localiteDTO.setNom(data.getNom());
        localiteDTO.setCode(data.getCode());
        localiteDTO.setCreatedBy(data.getCreatedBy());
        localiteDTO.setCreatedDate(data.getCreatedDate());
        localiteDTO.setLastModifiedBy(data.getLastModifiedBy());
        localiteDTO.setLastModifedDate(data.getLastModifedDate());
        localiteDTO.setNomAbrege(data.getNomAbrege());
        return localiteDTO;
    }

    @Override
    public Localite toBo(LocaliteDTO data) {
        Localite localite = new Localite();
        localite.setId(data.getId());
        localite.setNom(data.toUpperNom());
        localite.setCode(data.toUpperCode());
        localite.setCreatedBy(data.getCreatedBy());
        localite.setCreatedDate(data.getCreatedDate());
        localite.setLastModifiedBy(data.getLastModifiedBy());
        localite.setLastModifedDate(data.getLastModifedDate());
        localite.setNomAbrege(data.toUpperNomAbrege());
        return localite;
    }

    @Override
    public List<LocaliteDTO> toVoList(List<Localite> listData) {
        List<LocaliteDTO> localiteDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->localiteDTOS.add(toVo(data)));
        return localiteDTOS;
    }

    @Override
    public List<Localite> toBoList(List<LocaliteDTO> listData) {
        List<Localite> localites = new ArrayList<>();
        if(listData != null) listData.forEach(data ->localites.add(toBo(data)));
        return localites;
    }
}
