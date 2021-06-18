package org.telio.portail_societe.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UtilisateurHistoriqueDTO {

    private Long id;
    private String dateDebut;
    private String dateFin;
    private SocieteDTO societeDTO;
    private EntiteDTO entiteDTO;
    private ProfilDTO profilDTO;
    private UtilisateurDTO responsableAgentDTO;
    private UtilisateurDTO utilisateurDTO;

    public UtilisateurHistoriqueDTO(String dateDebut, String dateFin, SocieteDTO societeDTO, EntiteDTO entiteDTO, ProfilDTO profilDTO, UtilisateurDTO responsableAgentDTO, UtilisateurDTO utilisateurDTO) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.societeDTO = societeDTO;
        this.entiteDTO = entiteDTO;
        this.profilDTO = profilDTO;
        this.responsableAgentDTO = responsableAgentDTO;
        this.utilisateurDTO = utilisateurDTO;
    }
}
