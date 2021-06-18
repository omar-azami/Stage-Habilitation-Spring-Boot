package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telio.portail_societe.model.Localite;

public interface LocaliteRepository extends JpaRepository <Localite, Long> {

    Localite findByNom (String nom);
    Localite findByCode (String code);
    Localite findByNomAbrege (String nomAbrege);
    Boolean existsByNom (String nom);
    Boolean existsByCode (String code);
    Boolean existsByNomAbrege (String nomAbrege);
}
