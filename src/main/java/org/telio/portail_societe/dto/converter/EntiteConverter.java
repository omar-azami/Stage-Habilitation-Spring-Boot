package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.EntiteDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Entite;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntiteConverter implements MapperMethods <EntiteDTO, Entite> {

    @Autowired
    private LocaliteConverter localiteConverter;
    @Autowired
    private SocieteConverter societeConverter;
    @Autowired
    private TypeEntiteConverter typeEntiteConverter;

    @Override
    public EntiteDTO toVo(Entite data) {
        EntiteDTO entiteDTO = new EntiteDTO();
        entiteDTO.setId(data.getId());
        entiteDTO.setNom(data.getNom());
        entiteDTO.setCode(data.getCode());
        entiteDTO.setCreatedBy(data.getCreatedBy());
        entiteDTO.setCreatedDate(data.getCreatedDate());
        entiteDTO.setLastModifiedBy(data.getLastModifiedBy());
        entiteDTO.setLastModifedDate(data.getLastModifedDate());
        entiteDTO.setLocaliteDTO(localiteConverter.toVo(data.getLocalite()));
        entiteDTO.setSocieteDTO(societeConverter.toVo(data.getSociete()));
        entiteDTO.setTypeEntiteDTO(typeEntiteConverter.toVo(data.getTypeEntite()));
        if(data.getEntiteMere() != null) entiteDTO.setEntiteMereDTO(toVo(data.getEntiteMere()));
        else entiteDTO.setEntiteMereDTO(null);
        return entiteDTO;
    }

    @Override
    public Entite toBo(EntiteDTO data) {
        if (data == null) return null;
        Entite entite = new Entite();
        entite.setId(data.getId());
        entite.setNom(data.toUpperNom());
        entite.setCode(data.toUpperCode());
        entite.setCreatedBy(data.getCreatedBy());
        entite.setCreatedDate(data.getCreatedDate());
        entite.setLastModifiedBy(data.getLastModifiedBy());
        entite.setLastModifedDate(data.getLastModifedDate());
        entite.setLocalite(localiteConverter.toBo(data.getLocaliteDTO()));
        entite.setSociete(societeConverter.toBo(data.getSocieteDTO()));
        entite.setTypeEntite(typeEntiteConverter.toBo(data.getTypeEntiteDTO()));
         if(data.getEntiteMereDTO() != null) entite.setEntiteMere(toBo(data.getEntiteMereDTO()));
         else entite.setEntiteMere(null);
        return entite;
    }

    @Override
    public List<EntiteDTO> toVoList(List<Entite> listData) {
        List<EntiteDTO> entiteDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->entiteDTOS.add(toVo(data)));
        return entiteDTOS;
    }

    @Override
    public List<Entite> toBoList(List<EntiteDTO> listData) {
        List<Entite> entites = new ArrayList<>();
        if(listData != null) listData.forEach(data ->entites.add(toBo(data)));
        return entites;
    }
}
