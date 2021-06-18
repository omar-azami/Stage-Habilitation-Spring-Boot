package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telio.portail_societe.model.Societe;

import java.util.List;

public interface SocieteRepository extends JpaRepository <Societe, Long> {

    Societe findByNom (String nom);
    Societe findByCode (String code);
    Societe findByNomAbrege (String nomAbrege);
    List<Societe> findByStatut (String statut);
    Boolean existsByNom (String nom);
    Boolean existsByCode (String code);
}
