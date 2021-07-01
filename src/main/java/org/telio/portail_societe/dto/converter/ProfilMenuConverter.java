package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.ProfilMenuDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.ProfilMenu;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfilMenuConverter implements MapperMethods <ProfilMenuDTO, ProfilMenu> {
	
	 @Autowired
	    private MenuConverter menuConverter;
	  @Autowired
	    private ProfilConverter proConverter;
    @Override
    public ProfilMenuDTO toVo(ProfilMenu data) {
    	ProfilMenuDTO profilMenuDTO = new ProfilMenuDTO();
    	profilMenuDTO.setDateDebut(data.getDateDebut());
    	profilMenuDTO.setProfilDTO(proConverter.toVo(data.getProfil()));
    	profilMenuDTO.setDateFin(data.getDateFin());
    	profilMenuDTO.setCreatedBy(data.getCreatedBy());
    	profilMenuDTO.setLastModifedDate(data.getLastModifedDate());
    	profilMenuDTO.setCreatedDate(data.getCreatedDate());
    	profilMenuDTO.setLastModifiedBy(data.getLastModifiedBy());

    	profilMenuDTO.setMenuDTO(menuConverter.toVo(data.getMenu()));
        return profilMenuDTO;
    }

    @Override
    public ProfilMenu toBo(ProfilMenuDTO data) {
    	
    	ProfilMenu profilMenu = new ProfilMenu();
        if (data == null) { 
        	System.out.println("hona **************       *******       "+data);
        	return profilMenu;}

    	profilMenu.setDateDebut(data.getDateDebut());
    	profilMenu.setProfil(proConverter.toBo(data.getProfilDTO()));
    	profilMenu.setDateFin(data.getDateFin());
    	profilMenu.setMenu(menuConverter.toBo(data.getMenuDTO()));
    	profilMenu.setCreatedBy(data.getCreatedBy());
    	profilMenu.setLastModifedDate(data.getLastModifedDate());
    	profilMenu.setCreatedDate(data.getCreatedDate());
    	profilMenu.setLastModifiedBy(data.getLastModifiedBy());
    	System.out.println("hona 2**************       *******       "+data);
    	System.out.println("hona **************       *******       "+profilMenu		);

        return profilMenu;

    }

    @Override
    public List<ProfilMenuDTO> toVoList(List<ProfilMenu> listData) {
    	List<ProfilMenuDTO> profilMenuDTOS = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->profilMenuDTOS.add(toVo(data)));
        }
        return profilMenuDTOS;
    }

    @Override
    public List<ProfilMenu> toBoList(List<ProfilMenuDTO> listData) {
    	List<ProfilMenu> profilMenuS = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->profilMenuS.add(toBo(data)));
        }
        return profilMenuS;
    }
}
