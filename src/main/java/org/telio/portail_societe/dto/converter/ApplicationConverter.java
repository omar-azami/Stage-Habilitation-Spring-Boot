package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.ApplicationDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Application;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationConverter implements MapperMethods <ApplicationDTO, Application> {
    @Autowired
    SocieteConverter converter;

    @Override
    public ApplicationDTO toVo(Application data) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        if (data == null) return applicationDTO;
        applicationDTO.setId(data.getId());
        applicationDTO.setNom(data.getNom());
        applicationDTO.setDescription(data.getDescription());
        applicationDTO.setSocieteDTO(converter.toVo(data.getSociete()));
        applicationDTO.setCreatedBy(data.getCreatedBy());
        applicationDTO.setCreatedDate(data.getCreatedDate());
        applicationDTO.setLastModifiedBy(data.getLastModifiedBy());
        applicationDTO.setLastModifedDate(data.getLastModifedDate());
        return applicationDTO;
    }

    @Override
    public Application toBo(ApplicationDTO data) {
        Application application = new Application();
        if (data == null) return application;
        application.setId(data.getId());
        application.setNom(data.toUpperNom());
        application.setDescription(data.toUpperDescription());
        application.setSociete(converter.toBo(data.getSocieteDTO()));
        application.setCreatedBy(data.getCreatedBy());
        application.setCreatedDate(data.getCreatedDate());
        application.setLastModifiedBy(data.getLastModifiedBy());
        application.setLastModifedDate(data.getLastModifedDate());
        return application;
    }

    @Override
    public List<ApplicationDTO> toVoList(List<Application> listData) {
        List<ApplicationDTO> applicationDTOS = new ArrayList<>();
        if(listData !=null) listData.forEach(data ->applicationDTOS.add(toVo(data)));
        return applicationDTOS;
    }

    @Override
    public List<Application> toBoList(List<ApplicationDTO> listData) {
        List<Application> applications = new ArrayList<>();
        if(listData != null) listData.forEach(data ->applications.add(toBo(data)));
        return null;
    }
}
