package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.telio.portail_societe.idClass.TypeEntiteID;
import org.telio.portail_societe.model.Societe;
import org.telio.portail_societe.model.TypeEntite;

import java.util.List;

public interface TypeEntiteRepository extends JpaRepository <TypeEntite, TypeEntiteID> {

    List<TypeEntite> findByNom (String nom);
    List<TypeEntite> findByCode (String code);
    List<TypeEntite> findBySociete(Societe societe);
    Boolean existsByNom (String nom);
    Boolean existsByCode (String code);
    @Query(value = "select * from type_entite te where te.id=:id and te.societe=:societe ",
    nativeQuery = true)
    TypeEntite rechercheById (@Param("id") Long id, @Param("societe") Long societe);
    
    
    @Query(value = "select * from type_entite te where  te.societe=:id ",
    	    nativeQuery = true)
    	    TypeEntite rechercheBysociete (@Param("id") Long id);
    
    
    @Query(value = "select * from type_entite te where te.id=:id  ",
    nativeQuery = true)
    TypeEntite rechercheByIds (@Param("id") Long id);
   
    
    @Query(value = "select * from type_entite te where te.type_mere is null",
    nativeQuery = true)
    List<TypeEntite> rechercheTypeMereByID ();
    Boolean existsBySociete (Societe societe);

}
