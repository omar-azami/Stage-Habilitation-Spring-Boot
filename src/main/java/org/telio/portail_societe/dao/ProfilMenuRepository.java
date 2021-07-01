package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.telio.portail_societe.idClass.ProfilMenuID;
import org.telio.portail_societe.model.ProfilMenu;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

public interface ProfilMenuRepository extends JpaRepository <ProfilMenu, ProfilMenuID> {

    List <ProfilMenu> findAll();
    
    
    
//    @Query(value = " ",
//    	    nativeQuery = true)
//       Void rechercheById ();
//    
    //INSERT INTO `profil_menu`(`date_debut`, `date_fint`, `profil`, `societee`, `application`, `societe`, `menu`) VALUES (2021-06-02,2021-06-02,9,5,230,5,245)
    @Query(
    		  value = 
    		    "insert into profil_menu (date_debut, date_fint, profil, societee, application, societe, menu )"
    		    + " values (:date_debut, :date_fin, :profilid, :societeeid, :applicationid, :societeid, :menuid)",
    		  nativeQuery = true)
    @Modifying
    @Transactional
    		void insertUser( @Param("date_debut") Date date_debut, @Param("date_fin") Date date_fin, @Param("profilid") Long profilid , @Param("societeeid") Long societeeid , @Param("applicationid") Long applicationid , @Param("societeid") Long societeid , @Param("menuid") Long menuid );
//    @Param("name") String name, @Param("age") Integer age, 
//    		  @Param("status") Integer status, @Param("email") String email);
    
//    @Query(
//  		  value = 
//  		    "update  profil_menu set date_debut, date_fint, profil, societee, application, societe, menu  "
//  		    + " values (:date_debut, :date_fin, :profilid, :societeeid, :applicationid, :societeid, :menuid)",
//  		  nativeQuery = true)
    
}
