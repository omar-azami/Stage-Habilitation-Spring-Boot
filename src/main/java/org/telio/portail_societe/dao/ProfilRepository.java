package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.telio.portail_societe.idClass.ProfilID;
import org.telio.portail_societe.model.Profil;
import org.telio.portail_societe.model.Societe;

import java.util.List;

public interface ProfilRepository extends JpaRepository <Profil, ProfilID> {

    List <Profil> findByNom (String nom);
    List<Profil> findBySociete(Societe societe);
    Boolean existsByNom (String nom);
    @Query(value = "select * from profil pro where pro.id=:id and pro.societe=:societe",
            nativeQuery = true)
    Profil rechercheProfilByID (@Param("id") Long id, @Param("societe") Long societe);


}
