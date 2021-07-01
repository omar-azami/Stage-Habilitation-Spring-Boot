package org.telio.portail_societe.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telio.portail_societe.dto.entities.MenuDTO;
import org.telio.portail_societe.generic.interfaces.MapperMethods;
import org.telio.portail_societe.model.Menu;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuConverter implements MapperMethods <MenuDTO, Menu> {

    @Autowired
    private ApplicationConverter converter;
    @Autowired
    private ProfilConverter proConverter;

    @Override
    public MenuDTO toVo(Menu data) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(data.getId());
        menuDTO.setNom(data.getNom());
        menuDTO.setDescription(data.getDescription());
        menuDTO.setLien(data.getLien());
        menuDTO.setType(data.getType());
        menuDTO.setParametres(data.getParametres());
        menuDTO.setOrdre(data.getOrdre());
        menuDTO.setCreatedBy(data.getCreatedBy());
        menuDTO.setCreatedDate(data.getCreatedDate());
        menuDTO.setLastModifiedBy(data.getLastModifiedBy());
        menuDTO.setLastModifedDate(data.getLastModifedDate());
        if(data.getMenuPere() != null) menuDTO.setMenuPereDTO(toVo(data.getMenuPere()));
        else menuDTO.setMenuPereDTO(null);
        menuDTO.setApplicationDTO(converter.toVo(data.getApplication()));
//        menuDTO.setProfilDTOS(proConverter.toVoList(data.getProfils()));
        return menuDTO;
    }

    @Override
    public Menu toBo(MenuDTO data) {

        Menu menu = new Menu();
        menu.setId(data.getId());
        menu.setNom(data.toUpperNom());
        menu.setLien(data.getLien());
        menu.setDescription(data.getDescription());
        menu.setParametres(data.getParametres());
        menu.setType(data.toUpperType());
        menu.setOrdre(data.getOrdre());
        menu.setCreatedBy(data.getCreatedBy());
        menu.setCreatedDate(data.getCreatedDate());
        menu.setLastModifiedBy(data.getLastModifiedBy());
        menu.setLastModifedDate(data.getLastModifedDate());
        menu.setApplication(converter.toBo(data.getApplicationDTO()));
        if(data.getMenuPereDTO() != null)
        {
            menu.setMenuPere(toBo(data.getMenuPereDTO()));
        }
        else{
            menu.setMenuPere(null);
        }
//        menu.setProfils(proConverter.toBoList(data.getProfilDTOS()));
        return menu;
    }

    @Override
    public List<MenuDTO> toVoList(List<Menu> listData) {
        List<MenuDTO> menuDTOS = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->menuDTOS.add(toVo(data)));
        }
        return menuDTOS;
    }

    @Override
    public List<Menu> toBoList(List<MenuDTO> listData) {
        List<Menu> menus = new ArrayList<>();
        if(listData != null)
        {
            listData.forEach(data ->menus.add(toBo(data)));
        }
        return menus;
    }
}
