package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telio.portail_societe.idClass.UtilisateurHistoriqueID;
import org.telio.portail_societe.model.UtilisateurHistorique;

public interface UtilisateurHistoriqueRepository extends JpaRepository <UtilisateurHistorique, UtilisateurHistoriqueID> {
}