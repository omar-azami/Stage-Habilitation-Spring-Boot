package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telio.portail_societe.idClass.ProfilMenuID;
import org.telio.portail_societe.model.ProfilMenu;

import java.util.List;

public interface ProfilMenuRepository extends JpaRepository <ProfilMenu, ProfilMenuID> {

    List <ProfilMenu> findAll();
}
