package org.telio.portail_societe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.telio.portail_societe.audit.Auditable;
import org.telio.portail_societe.idClass.ProfilMenuID;

import javax.persistence.*;
import java.sql.Date;

@Table(name="ProfilMenu")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProfilMenuID.class)
@EntityListeners(AuditingEntityListener.class)
public class ProfilMenu extends Auditable<String> {

    @Id
    @Column(name="dateDebut")
    private Date dateDebut;
    @Column(name="dateFint")
    private Date dateFin;

    @Id
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="application", referencedColumnName="application")),
            @JoinColumnOrFormula(column = @JoinColumn(name="societe", referencedColumnName="societe")),
            @JoinColumnOrFormula(column = @JoinColumn(name="menu", referencedColumnName="id")),
    })
    private Menu menu;

    @Id
    @ManyToOne
    @JoinColumnsOrFormulas(value = {
            @JoinColumnOrFormula(column = @JoinColumn(name="profil", referencedColumnName="id")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "societee", referencedColumnName = "societe")),

    })
    private Profil profil;



}

