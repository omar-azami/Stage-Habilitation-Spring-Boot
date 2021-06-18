package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.telio.portail_societe.idClass.EntiteID;
import org.telio.portail_societe.model.Entite;

import java.util.List;

public interface EntiteRepository extends JpaRepository <Entite, EntiteID> {

	List<Entite> findByNom(String nom);
    List<Entite> findByCode (String code);
    Boolean existsByNom (String nom);
    Boolean existsByCode (String code);
    Boolean existsBySociete (Long id);
    @Query(value = "select * from entite en where en.id=:id and en.societe=:societe",
           nativeQuery = true)
    Entite rechercheByID (@Param("id") Long id, @Param("societe") Long societe);
    @Query(value = "select * from entitie en where en.entite_mere is null",
           nativeQuery = true)
    List<Entite> rechercheEntitesMere ();
    @Query(value = "select * from entitie en where en.entite_mere=:id",
            nativeQuery = true)
     List<Entite> rechercheEntiteesMere (@Param("id") Long id);

}
