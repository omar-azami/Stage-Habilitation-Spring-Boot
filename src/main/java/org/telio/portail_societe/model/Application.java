package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.idClass.ApplicationID;

import javax.persistence.*;

@Table(name="Application")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ApplicationID.class)
@EntityListeners(AuditingEntityListener.class)
public class Application extends Auditable <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name="nom", nullable = false)
    private String nom;
    @Column(name = "description", length = 3000)
    private String description;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="societe")
    private Societe societe;

    public Application(String nom, String description, Societe societe) {
        this.nom = nom;
        this.description = description;
        this.societe = societe;
    }
}
