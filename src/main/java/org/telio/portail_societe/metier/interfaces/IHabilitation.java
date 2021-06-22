package org.telio.portail_societe.metier.interfaces;

import org.telio.portail_societe.dto.entities.*;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.idClass.*;

public interface IHabilitation {

    /*============================ Partie Type Societe ===================== */
    String ctrlTypeSociete (TypeSocieteDTO typeSocieteDTO);
    ResponseOutput <TypeSocieteDTO> persist (TypeSocieteDTO typeSocieteDTO);
    ResponseOutput <TypeSocieteDTO> update (Long id, TypeSocieteDTO typeSocieteDTO);
    ResponseOutput <TypeSocieteDTO> deleteTypeSociete (Long id);
    ResponseOutput <TypeSocieteDTO> searchTypeSocieteByNom (String nom);
    ResponseOutput <TypeSocieteDTO> searchTypeSocieteByCode (String code);
    ResponseOutput <TypeSocieteDTO> searchTypeSocieteByStatut (String statut);
    ResponseOutput <TypeSocieteDTO> searchTypeSocieteByID (Long id);
    ResponseOutput <TypeSocieteDTO> getAllTypeSocieteSortBy (String fieldName);

    /*=========================================================================*/

    /*============================ Partie Societe =============================*/
    String ctrlSociete (SocieteDTO societeDTO);
    ResponseOutput <SocieteDTO> persist (SocieteDTO societeDTO);
    ResponseOutput <SocieteDTO> update (Long id, SocieteDTO societeDTO);
    ResponseOutput <SocieteDTO> deleteSociete (Long id);
    ResponseOutput <SocieteDTO> searchSocieteByNom (String nom);
    ResponseOutput <SocieteDTO> searchSocieteByCode (String code);
    ResponseOutput <SocieteDTO> searchSocieteByNomAbrege (String nomAbrege);
    ResponseOutput <SocieteDTO> searchSocieteByStatut (String statut);
    ResponseOutput <SocieteDTO> getAllSocietesSortBy (String fieldName);
    ResponseOutput <SocieteDTO> searchSocieteByID (Long id);
    /*=========================================================================*/

    /*============================ Partie Application =========================*/

    ResponseOutput <ApplicationDTO> persist (ApplicationDTO applicationDTO);
    ResponseOutput <ApplicationDTO> update (ApplicationID applicationID, ApplicationDTO applicationDTO);
    ResponseOutput <ApplicationDTO> deleteApplication (ApplicationID applicationID);
    ResponseOutput <ApplicationDTO> searchApplicationByNom (String nom);
    ResponseOutput <ApplicationDTO> searchApplicationByID (Long id, Long societe);
    ResponseOutput <ApplicationDTO> getAllApplicationsSortBy (String fieldName);
    ResponseOutput <ApplicationDTO> getApplicationBySociete(Long id) ;

    /*=========================================================================*/

    /*============================ Partie Profil ==============================*/

    ResponseOutput <ProfilDTO> persist (ProfilDTO profilDTO);
    ResponseOutput <ProfilDTO> update (ProfilID profilID, ProfilDTO profilDTO);
    ResponseOutput <ProfilDTO> deleteProfil (ProfilID profilID);
    ResponseOutput <ProfilDTO> searchProfilByNom (String nom);
    ResponseOutput <ProfilDTO> searchProfilByID (Long id, Long societe);
    ResponseOutput <ProfilDTO> getAllProfilsSortBy (String fieldName);
    ResponseOutput <ProfilDTO> getProfilBySociete(Long id) ;


    /*=========================================================================*/

    /*============================ Partie Localite ============================*/

    ResponseOutput <LocaliteDTO> persist (LocaliteDTO localiteDTO);
    ResponseOutput <LocaliteDTO> update (Long id, LocaliteDTO localiteDTO);
    ResponseOutput <LocaliteDTO> deleteLocalite (Long id);
    ResponseOutput <LocaliteDTO> searchLocaliteByNom (String nom);
    ResponseOutput <LocaliteDTO> searchLocaliteByCode (String code);
    ResponseOutput <LocaliteDTO> searchLocaliteByNomAbrege (String nomAbrege);
    ResponseOutput <LocaliteDTO> getAllLocaliteSortBy (String fieldName);
    ResponseOutput <LocaliteDTO> searchLocaliteByID (Long id);


    /*=========================================================================*/

    /*========================== Partie Type Entite ===========================*/

    ResponseOutput <TypeEntiteDTO> persist (TypeEntiteDTO typeEntiteDTO);
    ResponseOutput <TypeEntiteDTO> update (TypeEntiteID typeEntiteID, TypeEntiteDTO typeEntiteDTO);
    ResponseOutput <TypeEntiteDTO> deleteTypeEntite (TypeEntiteID typeEntiteID);
    ResponseOutput <TypeEntiteDTO> searchTypeEntiteByNom (String nom);
    ResponseOutput <TypeEntiteDTO> searchTypeEntiteByCode (String code);
    ResponseOutput <TypeEntiteDTO> searchTypeEntiteByID (Long id, Long societe);
    ResponseOutput <TypeEntiteDTO> searchTypeEntiteMere ();
    ResponseOutput <TypeEntiteDTO> getAllTypeEntitiesSortBy (String fieldName);
    ResponseOutput <TypeEntiteDTO> getAllTypeEntities ();
	String ctrlEntite(EntiteDTO entiteDTO);
    ResponseOutput <TypeEntiteDTO> getAllTypeEntitiesBySociete (SocieteDTO SocieteDTO);
    ResponseOutput <TypeEntiteDTO> getTypeEntitiesBySociete (Long id);
    ResponseOutput <TypeEntiteDTO> getTypeEntitiesById (Long id);

    /*=========================================================================*/

    /*=========================== Partie Entite ================================*/

    ResponseOutput <EntiteDTO> persist (EntiteDTO entiteDTO);
    ResponseOutput <EntiteDTO> update (EntiteID entiteID, EntiteDTO entiteDTO);
    ResponseOutput <EntiteDTO> deleteEntite (EntiteID entiteID);
    ResponseOutput <EntiteDTO> searchEntiteByNom (String nom);
    ResponseOutput <EntiteDTO> searchEntiteByCode (String code);
    ResponseOutput <EntiteDTO> searchEntiteByID (Long id, Long societe);
    ResponseOutput <EntiteDTO> searchEntiteMere ();
    ResponseOutput <EntiteDTO> getAllEntitiesSortBy (String fieldName);
    ResponseOutput <EntiteDTO> getEntitiesBySociete (Long id);
    ResponseOutput <EntiteDTO> getAllEntities ();
    ResponseOutput <EntiteDTO> getEntitiesById (Long id);

    /*==========================================================================*/

    /*=========================== Partie Menu ==================================*/

    ResponseOutput <MenuDTO> persist (MenuDTO menuDTO);
    ResponseOutput <MenuDTO> update (MenuID menuID , MenuDTO menuDTO);
    ResponseOutput <MenuDTO> deleteMenu (MenuID menuID);
    ResponseOutput <MenuDTO> searchMenuByNom (String nom);
    ResponseOutput <MenuDTO> searchMenyByType (String type);
    ResponseOutput <MenuDTO> searchMenuByLien (String lien);
    ResponseOutput <MenuDTO> searchMenuByParametres (String parametres);
    ResponseOutput <MenuDTO> searchMenuByID (Long id, Long societe, Long application);
    ResponseOutput <MenuDTO> searchMenuMere ();
    ResponseOutput <MenuDTO> getAllMenusSortBy (String fieldName);

    /*==========================================================================*/

}
