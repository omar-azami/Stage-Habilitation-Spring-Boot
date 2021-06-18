package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.idClass.EntiteID;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EntiteID.class)
@EntityListeners(AuditingEntityListener.class)
public class Entite extends Auditable <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name= "nom", length = 255, nullable = false)
    private String nom;
    @Column(name= "code", length = 255, nullable = false)
    private String code;
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="entite_mere", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })
    private Entite entiteMere;
    @ManyToOne(optional = false)
    @JoinColumn(name="localite")
    private Localite localite;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="societe")
    private Societe societe;
    @ManyToOne(optional = false)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="type", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })
    private TypeEntite typeEntite;

    public Entite(String nom, String code, Entite entiteMere, Localite localite, Societe societe, TypeEntite typeEntite) {
        this.nom = nom;
        this.code = code;
        this.entiteMere = entiteMere;
        this.localite = localite;
        this.societe = societe;
        this.typeEntite = typeEntite;
    }
}
