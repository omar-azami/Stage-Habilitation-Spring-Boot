package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.idClass.TypeEntiteID;

import javax.persistence.*;

@Table(name = "type_entite")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TypeEntiteID.class)
@EntityListeners(AuditingEntityListener.class)
public class TypeEntite extends Auditable <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name="nom", length = 255, nullable = false)
    private String nom;
    @Column(name= "code", length = 255, nullable = false)
    private String code;
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="type_mere", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })
    private TypeEntite typeMere;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="societe")
    private Societe societe;

    public TypeEntite(String nom, String code, TypeEntite typeMere, Societe societe) {
        this.nom = nom;
        this.code = code;
        this.typeMere = typeMere;
        this.societe = societe;
    }
}
