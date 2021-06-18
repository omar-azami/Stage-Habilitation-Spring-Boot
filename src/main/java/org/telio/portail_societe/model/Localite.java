package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.*;

@Table(name = "Localite")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Localite extends Auditable <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="nom", nullable = false)
    private String nom;
    @Column(name="code", nullable = false)
    private String code;
    @Column(name="nomAbrege", nullable = false)
    private String nomAbrege;

    public Localite(String nom, String code, String nomAbrege) {
        this.nom = nom;
        this.code = code;
        this.nomAbrege = nomAbrege;
    }

    public String toUpperNom()
    {
        return this.nom.toUpperCase();
    }
}
