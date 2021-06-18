package org.telio.portail_societe.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;

import javax.persistence.*;

@Table(name = "Type_Societe")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TypeSociete extends Auditable <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name="nom", nullable = false, length = 255)
    private String nom;
    @Column(name="code", nullable = false, length = 255)
    private String code;
    @Column(name = "nomAbrege", length = 255)
    private String nomAbrege;
    @Column(name="statut")
    private String statut;
    //Auditing




    public TypeSociete(String nom, String code, String nomAbrege, String statut) {
        this.nom = nom;
        this.code = code;
        this.nomAbrege = nomAbrege;
        this.statut = statut;
    }

}
