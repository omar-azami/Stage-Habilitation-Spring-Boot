package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.*;

@Table(name = "Societe")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Societe extends Auditable <String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false, unique = true)
    private Long id;
    @Column(name="nom", nullable = false)
    private String nom;
    @Column(name="code", nullable = false)
    private String code;
    @Column(name="nomAbrege", nullable = false)
    private String nomAbrege;
    @ManyToOne(optional = false)
    @JoinColumn(name = "type")
    private TypeSociete typeSociete;
    @Column(name = "statut")
    private String statut;
    @Embedded
    private PieceJointe imageSociete;
    
    //auditing

    public String toUpperName()
    {
        return this.nom.toUpperCase();
    }


    public Societe(String nom, String code, String nomAbrege, TypeSociete typeSociete, String statut) {
        this.nom = nom;
        this.code = code;
        this.nomAbrege = nomAbrege;
        this.typeSociete = typeSociete;
        this.statut = statut;
    }
   
}
