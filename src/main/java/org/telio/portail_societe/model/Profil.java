package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.idClass.ProfilID;

import javax.persistence.*;

@Table(name="Profil")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProfilID.class)
@EntityListeners(AuditingEntityListener.class)
public class Profil extends Auditable <String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name= "nom", nullable = false, length = 255)
    private String nom;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="societe")
    private Societe societe;

    public Profil(String nom, Societe societe) {
        this.nom = nom;
        this.societe = societe;
    }
}
