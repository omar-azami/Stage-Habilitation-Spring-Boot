package org.telio.portail_societe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telio.portail_societe.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository <Role, Long> {

    List<Role> findAll();
    Role findByLibele (String libele);
    Boolean existsByLibele (String libele);
}
