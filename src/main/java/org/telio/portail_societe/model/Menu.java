package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.idClass.MenuID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Table(name="Menu")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MenuID.class)
@EntityListeners(AuditingEntityListener.class)
public class Menu extends Auditable <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name="type", nullable = false)
    private String type;
    @Column(name="nom", nullable = false)
    private String nom;
    @Column(name="description")
    private String description;
    @Column(name = "lien")
    private String lien;
    @Column(name="parametres", nullable = false)
    private String parametres;
    @Column(name="ordre")
    private int ordre;

    @Id
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="application", referencedColumnName="id")),
            @JoinColumnOrFormula(column = @JoinColumn(name="societe", referencedColumnName="societe")),
    })
    private Application application;

    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="menu_pere", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "application", referencedColumnName = "application"))

    })
    private Menu menuPere;

//    @ManyToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinTable(name = "profilMenu",
//            joinColumns = {
//                    @JoinColumn(name = "Menu", referencedColumnName = "id"),
//                    @JoinColumn(name = "societe", referencedColumnName = "societe"),
//                    @JoinColumn(name = "application", referencedColumnName = "application"),
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "profil", referencedColumnName = "id"),
//                    @JoinColumn(name="societe_p",referencedColumnName = "societe"),
//
//                                }
//            )
//
//
//    private Set<Profil> profils = new HashSet<>();



    public Menu(String type, String nom, String description, String lien, String parametres, int ordre, Application application, Menu menuPere) {
        this.type = type;
        this.nom = nom;
        this.description = description;
        this.lien = lien;
        this.parametres = parametres;
        this.ordre = ordre;
        this.application = application;
        this.menuPere = menuPere;
    }

}
