package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telio.portail_societe.idClass.UtilisateurID;
import org.telio.portail_societe.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository <Utilisateur, UtilisateurID> {
    Utilisateur findByLogin (String login);
}
