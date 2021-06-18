package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.telio.portail_societe.idClass.UtilisateurHistoriqueID;

import javax.persistence.*;

@Table(name = "UtilisateurHistorique")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UtilisateurHistoriqueID.class)
public class UtilisateurHistorique {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;
    @Column(name="dateDebut")
    private String dateDebut;
    @Column(name="dateFin")
    private String dateFin;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name="societe")
    private Societe societe;
    @ManyToOne(optional = false)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="entite", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })
    private Entite entite;
    @ManyToOne(optional = false)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="profil", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })
    private Profil profil;

    @ManyToOne(optional = false)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="agent_responsable", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })

    private Utilisateur responsableAgent;
    @ManyToOne(optional = false)
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="agent", referencedColumnName="id")),
            @JoinColumnOrFormula(formula = @JoinFormula(value = "societe", referencedColumnName = "societe"))
    })
    private Utilisateur utilisateur;


    public UtilisateurHistorique(String dateDebut, String dateFin, Societe societe, Entite entite, Profil profil, Utilisateur responsableAgent, Utilisateur utilisateur) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.societe = societe;
        this.entite = entite;
        this.profil = profil;
        this.responsableAgent = responsableAgent;
        this.utilisateur = utilisateur;
    }
}
