package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telio.portail_societe.model.TypeSociete;

import java.util.List;

public interface TypeSocieteRepository extends JpaRepository <TypeSociete, Long> {
    TypeSociete findByNom (String nom);
    TypeSociete findByCode (String code);
    List<TypeSociete> findByStatut (String statut);
    Boolean existsByNom (String nom );
    Boolean existsByCode (String code);

}
