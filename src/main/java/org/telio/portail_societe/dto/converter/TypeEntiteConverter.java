package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.TypeEntiteDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.TypeEntite;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeEntiteConverter implements MapperMethods <TypeEntiteDTO, TypeEntite> {
    @Autowired
    private SocieteConverter converter;

    @Override
    public TypeEntiteDTO toVo(TypeEntite data) {
        TypeEntiteDTO typeEntiteDTO = new TypeEntiteDTO();
        typeEntiteDTO.setId(data.getId());
        typeEntiteDTO.setNom(data.getNom());
        typeEntiteDTO.setCode(data.getCode());
        typeEntiteDTO.setCreatedBy(data.getCreatedBy());
        typeEntiteDTO.setCreatedDate(data.getCreatedDate());
        typeEntiteDTO.setLastModifiedBy(data.getLastModifiedBy());
        typeEntiteDTO.setLastModifedDate(data.getLastModifedDate());
        typeEntiteDTO.setSocieteDTO(converter.toVo(data.getSociete()));
        if(data.getTypeMere() != null) typeEntiteDTO.setTypeEntiteMereDTO(toVo(data.getTypeMere()));
        else typeEntiteDTO.setTypeEntiteMereDTO(null);
        return typeEntiteDTO;
    }

    @Override
    public TypeEntite toBo(TypeEntiteDTO data) {
        TypeEntite typeEntite = new TypeEntite();
        typeEntite.setId(data.getId());
        typeEntite.setNom(data.toUpperNom());
        typeEntite.setCode(data.toUpperCode());
        typeEntite.setCreatedBy(data.getCreatedBy());
        typeEntite.setCreatedDate(data.getCreatedDate());
        typeEntite.setLastModifiedBy(data.getLastModifiedBy());
        typeEntite.setLastModifedDate(data.getLastModifedDate());
        typeEntite.setSociete(converter.toBo(data.getSocieteDTO ()));
        if(data.getTypeEntiteMereDTO() != null) typeEntite.setTypeMere(toBo(data.getTypeEntiteMereDTO()));
        else typeEntite.setTypeMere(null);
        return typeEntite;
    }

    @Override
    public List<TypeEntiteDTO> toVoList(List<TypeEntite> listData) {
        List<TypeEntiteDTO> typeEntiteDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->typeEntiteDTOS.add(toVo(data)));
        return typeEntiteDTOS;
    }

    @Override
    public List<TypeEntite> toBoList(List<TypeEntiteDTO> listData) {
        List<TypeEntite> typeEntites = new ArrayList<>();
        if(listData != null) listData.forEach(data ->typeEntites.add(toBo(data)));
        return typeEntites;
    }
}
