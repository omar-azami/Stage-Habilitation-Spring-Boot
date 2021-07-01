package org.telio.portail_societe.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.metier.interfaces.IHabilitation;
import org.telio.portail_societe.metier.interfaces.IModerateur;
import org.telio.portail_societe.model.ProfilMenu;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurRest {
	 @Autowired
	    private IHabilitation iHabilitation;
	    
	    @Autowired
	    private IModerateur iModeratuer;
	    
	    @GetMapping(value = {"/show/profilMenu/BySociete/{id}"})
	    public ResponseEntity <ResponseOutput <ProfilMenu> > getAllProfilMenParSociete (@PathVariable ("id") Long id )
	    {
	        return new ResponseEntity<>(iHabilitation.getAllProfilMenParSociete(id),HttpStatus.OK);
	    }
}
