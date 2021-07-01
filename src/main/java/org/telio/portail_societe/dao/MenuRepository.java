package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.telio.portail_societe.dto.entities.ApplicationDTO;
import org.telio.portail_societe.idClass.MenuID;
import org.telio.portail_societe.model.Application;
import org.telio.portail_societe.model.Menu;
import org.telio.portail_societe.model.Societe;

import java.util.List;

public interface MenuRepository extends JpaRepository <Menu, MenuID> {

    List <Menu> findByNom (String nom);
    List <Menu> findByType (String type);
    List <Menu> findByLien (String lien);
//    List<Menu> findBySociete(Societe societe);
    Menu findById(Long id);

    Boolean existsByNom (String nom);
//    Boolean existsBySociete (Long id);
    List <Menu> findByParametres (String parametres);
    @Query(value = "select * from menu m where m.id=:id and m.societe=:societe and m.application=:application",
           nativeQuery = true)
    Menu rechercheMenuByID (@Param("id") Long id, @Param("societe") Long societe, @Param("application") Long application);
    @Query(value = "select * from menu m where m.menu_pere is null",
           nativeQuery = true)
    List<Menu> rechercheMenuMere ();
	List<Menu> findByApplication(Application list);

}
