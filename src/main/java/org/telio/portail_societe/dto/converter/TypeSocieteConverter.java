package org.telio.portail_societe.dto.converter;

import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.TypeSocieteDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.TypeSociete;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeSocieteConverter implements MapperMethods<TypeSocieteDTO, TypeSociete> {
    @Override
    public  TypeSocieteDTO toVo(TypeSociete data) {
        TypeSocieteDTO typeSocieteDTO = new TypeSocieteDTO();
        if (data == null) return typeSocieteDTO;
        typeSocieteDTO.setId(data.getId());
        typeSocieteDTO.setNom(data.getNom());
        typeSocieteDTO.setCode(data.getCode());
        typeSocieteDTO.setNomAbrege(data.getNomAbrege());
        typeSocieteDTO.setStatut(data.getStatut());
        typeSocieteDTO.setCreatedBy(data.getCreatedBy());
        typeSocieteDTO.setCreatedDate(data.getCreatedDate());
        typeSocieteDTO.setLastModifiedBy(data.getLastModifiedBy());
        typeSocieteDTO.setLastModifedDate(data.getLastModifedDate());
        return typeSocieteDTO;
    }

    @Override
    public TypeSociete toBo(TypeSocieteDTO data) {
        TypeSociete typeSociete = new TypeSociete();
        if (data == null) return typeSociete;
        typeSociete.setId(data.getId());
        typeSociete.setNom(data.toUpperNom());
        typeSociete.setCode(data.toUpperCode());
        typeSociete.setNomAbrege(data.toUpperNomAbrege());
        typeSociete.setStatut(data.getStatut());
        typeSociete.setCreatedBy(data.getCreatedBy());
        typeSociete.setCreatedDate(data.getCreatedDate());
        typeSociete.setLastModifiedBy(data.getLastModifiedBy());
        typeSociete.setLastModifedDate(data.getLastModifedDate());
        return typeSociete;
    }

    @Override
    public List<TypeSocieteDTO> toVoList(List<TypeSociete> listData) {
        List<TypeSocieteDTO> typeSocieteDTOS = new ArrayList<>();
        if(listData !=null)
        {
            listData.forEach( data ->typeSocieteDTOS.add(toVo(data)));
        }
        return typeSocieteDTOS;
    }

    @Override
    public List<TypeSociete> toBoList(List<TypeSocieteDTO> listData) {

        List<TypeSociete> typeSocietes = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->typeSocietes.add(toBo(data)));
        }
        return typeSocietes;
    }
}
