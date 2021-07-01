package org.telio.portail_societe.metier.interfaces;

import org.springframework.web.multipart.MultipartFile;
import org.telio.portail_societe.dto.entities.SocieteDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;

public interface IModerateur {
	// Ajouter un utilisateur 
	
		ResponseOutput<SocieteDTO> fonctionAutomatique (Long id,String nom);
		ResponseOutput<SocieteDTO> fonctionGlobale(SocieteDTO societeDTO, String nom);
			// appel de l ajout de societe
			//fonction Automatique
		String ctrlSociete(SocieteDTO societeDTO);
}
