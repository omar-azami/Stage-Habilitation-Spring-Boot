package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.SocieteDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Societe;

import java.util.ArrayList;
import java.util.List;

@Component
public class SocieteConverter implements MapperMethods <SocieteDTO, Societe> {

    @Autowired
    TypeSocieteConverter converter;

    @Override
    public SocieteDTO toVo(Societe data) {
        SocieteDTO societeDTO = new SocieteDTO();
        if(data == null) return societeDTO;
        societeDTO.setId(data.getId());
        societeDTO.setNom(data.getNom());
        societeDTO.setCode(data.getCode());
        societeDTO.setNomAbrege(data.getNomAbrege());
        societeDTO.setStatut(data.getStatut());
        societeDTO.setCreatedBy(data.getCreatedBy());
        societeDTO.setCreatedDate(data.getCreatedDate());
        societeDTO.setLastModifiedBy(data.getLastModifiedBy());
        societeDTO.setLastModifedDate(data.getLastModifedDate());
        societeDTO.setTypeSocieteDTO(converter.toVo(data.getTypeSociete()));
        return societeDTO;
    }

    @Override
    public Societe toBo(SocieteDTO data) {
        Societe societe = new Societe();
        if (data == null) return societe;
        societe.setId(data.getId());
        societe.setNom(data.toUpperNom());
        societe.setCode(data.toUpperCode());
        societe.setNomAbrege(data.toUpperNomAbrege());
        societe.setStatut(data.toUpperStatut());
        societe.setCreatedBy(data.getCreatedBy());
        societe.setCreatedDate(data.getCreatedDate());
        societe.setLastModifiedBy(data.getLastModifiedBy());
        societe.setLastModifedDate(data.getLastModifedDate());
        societe.setTypeSociete(converter.toBo(data.getTypeSocieteDTO()));
        return societe;
    }

    @Override
    public List<SocieteDTO> toVoList(List<Societe> listData) {
        List<SocieteDTO> societeDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->societeDTOS.add(toVo(data)));
        return societeDTOS;
    }

    @Override
    public List<Societe> toBoList(List<SocieteDTO> listData) {
        List<Societe> societes = new ArrayList<>();
        if(listData != null)  listData.forEach(data ->societes.add(toBo(data)));
        return societes;
    }
}
