package org.telio.portail_societe.metier.interfaces;

import org.telio.portail_societe.dto.entities.SocieteDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;

public interface IModerateur {
	// Ajouter un utilisateur 
	
		ResponseOutput<SocieteDTO> fonctionAutomatique (Long id);
		ResponseOutput<SocieteDTO> fonctionGlobale(SocieteDTO societe);
			// appel de l ajout de societe
			//fonction Automatique
		String ctrlSociete(SocieteDTO societeDTO);
}
