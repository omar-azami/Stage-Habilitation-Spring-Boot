package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.ProfilDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Profil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProfilConverter implements MapperMethods <ProfilDTO, Profil> {

    @Autowired
    private SocieteConverter converter;

    @Override
    public ProfilDTO toVo(Profil data) {
        ProfilDTO profilDTO = new ProfilDTO();
        profilDTO.setId(data.getId());
        profilDTO.setNom(data.getNom());
        profilDTO.setCreatedBy(data.getCreatedBy());
        profilDTO.setCreatedDate(data.getCreatedDate());
        profilDTO.setLastModifiedBy(data.getLastModifiedBy());
        profilDTO.setLastModifedDate(data.getLastModifedDate());
        profilDTO.setSocieteDTO(converter.toVo(data.getSociete()));
        return profilDTO;
    }

    @Override
    public Profil toBo(ProfilDTO data) {
        if (data == null) return null;
        Profil profil = new Profil();
        profil.setId(data.getId());
        profil.setNom(data.toUpperNom());
        profil.setCreatedBy(data.getCreatedBy());
        profil.setCreatedDate(data.getCreatedDate());
        profil.setLastModifiedBy(data.getLastModifiedBy());
        profil.setLastModifedDate(data.getLastModifedDate());
        profil.setSociete(converter.toBo(data.getSocieteDTO()));
        return profil;
    }

    @Override
    public List<ProfilDTO> toVoList(List<Profil> listData) {
        List<ProfilDTO> profilDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->profilDTOS.add(toVo(data)));
        return profilDTOS;
    }

    @Override
    public List<Profil> toBoList(List<ProfilDTO> listData) {
        List<Profil> profils = new ArrayList<>();
        if(listData != null) listData.forEach(data ->profils.add(toBo(data)));
        return profils;
    }

    public Set<ProfilDTO> toVoList (Set <Profil> listData)
    {
        Set <ProfilDTO> profilDTOS = new HashSet<>();
        if(listData != null)
        {
            listData.forEach(data ->profilDTOS.add(toVo(data)));
        }
        return profilDTOS;
    }

    public Set <Profil> toBoList (Set <ProfilDTO> listData)
    {
        Set <Profil> profils = new HashSet<>();
        if(listData != null)
        {
            listData.forEach(data ->profils.add(toBo(data)));
        }
        return profils;
    }
}
