package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.UtilisateurHistoriqueDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.UtilisateurHistorique;

import java.util.ArrayList;
import java.util.List;

@Component
public class UtilisateurHistoriqueConverter implements MapperMethods <UtilisateurHistoriqueDTO, UtilisateurHistorique> {

    @Autowired
    private SocieteConverter societeConverter;
    @Autowired
    private EntiteConverter entiteConverter;
    @Autowired
    private ProfilConverter profilConverter;
    @Autowired
    private UtilisateurConverter utilisateurConverter;

    @Override
    public UtilisateurHistoriqueDTO toVo(UtilisateurHistorique data) {
        UtilisateurHistoriqueDTO utilisateurHistoriqueDTO = new UtilisateurHistoriqueDTO();
        utilisateurHistoriqueDTO.setId(data.getId());
        utilisateurHistoriqueDTO.setDateDebut(data.getDateDebut());
        utilisateurHistoriqueDTO.setDateFin(data.getDateFin());
        utilisateurHistoriqueDTO.setSocieteDTO(societeConverter.toVo(data.getSociete()));
        utilisateurHistoriqueDTO.setEntiteDTO(entiteConverter.toVo(data.getEntite()));
        utilisateurHistoriqueDTO.setProfilDTO(profilConverter.toVo(data.getProfil()));
        utilisateurHistoriqueDTO.setResponsableAgentDTO(utilisateurConverter.toVo(data.getResponsableAgent()));
        utilisateurHistoriqueDTO.setUtilisateurDTO(utilisateurConverter.toVo(data.getUtilisateur()));
        return utilisateurHistoriqueDTO;
    }

    @Override
    public UtilisateurHistorique toBo(UtilisateurHistoriqueDTO data) {
        UtilisateurHistorique utilisateurHistorique = new UtilisateurHistorique();
        utilisateurHistorique.setId(data.getId());
        utilisateurHistorique.setDateDebut(data.getDateDebut());
        utilisateurHistorique.setDateFin(data.getDateFin());
        utilisateurHistorique.setSociete(societeConverter.toBo(data.getSocieteDTO()));
        utilisateurHistorique.setEntite(entiteConverter.toBo(data.getEntiteDTO()));
        utilisateurHistorique.setProfil(profilConverter.toBo(data.getProfilDTO()));
        utilisateurHistorique.setResponsableAgent(utilisateurConverter.toBo(data.getResponsableAgentDTO()));
        utilisateurHistorique.setUtilisateur(utilisateurConverter.toBo(data.getUtilisateurDTO()));
        return utilisateurHistorique;
    }

    @Override
    public List<UtilisateurHistoriqueDTO> toVoList(List<UtilisateurHistorique> listData) {
        List<UtilisateurHistoriqueDTO> utilisateurHistoriqueDTOS = new ArrayList<>();
        if(listData != null) listData.forEach(data ->utilisateurHistoriqueDTOS.add(toVo(data)));
        return utilisateurHistoriqueDTOS;
    }

    @Override
    public List<UtilisateurHistorique> toBoList(List<UtilisateurHistoriqueDTO> listData) {
        List<UtilisateurHistorique> utilisateurHistoriques = new ArrayList<>();
        if(listData != null) listData.forEach(data -> utilisateurHistoriques.add(toBo(data)));
        return utilisateurHistoriques;
    }
}
