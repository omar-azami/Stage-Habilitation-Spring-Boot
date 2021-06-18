package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.telio.portail_societe.idClass.ApplicationID;
import org.telio.portail_societe.model.Application;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, ApplicationID> {

    List<Application> findByNom (String nom);
    Boolean existsByNom (String nom);
    @Query(value = "select * from application ap where ap.id=:id and ap.societe=:societe",
            nativeQuery = true)
    Application rechercheApplicationByID(@Param("id") Long id, @Param("societe") Long societe);
}
