package org.telio.portail_societe.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.dto.entities.UtilisateurDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.idClass.UtilisateurID;
import org.telio.portail_societe.metier.interfaces.IUserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class HabilitationUserRest {
	@Autowired
	private IUserService iUserService;
	
	 @PostMapping("/add/utilisateur")
	    public ResponseEntity <ResponseOutput <UtilisateurDTO> > persist (@RequestBody UtilisateurDTO utilisateurDTO )
	    {
	        return new ResponseEntity<>(iUserService.persist(utilisateurDTO), HttpStatus.OK);
	    }
	 
	  @PatchMapping("/update/utilisateurPourAdmin/{id}/{societe}")
	    public ResponseEntity <ResponseOutput <UtilisateurDTO> > updateUtilisateurPourAdmin ( @PathVariable("id") Long id, @PathVariable("societe") Long societe, @RequestBody UtilisateurDTO utilisateurDTO)
	    {
		    UtilisateurID key = new UtilisateurID();
	        key.setId(id);
	        key.setSociete(societe);
	        return  new ResponseEntity<>(iUserService.updatePourAdmin(utilisateurDTO,key), HttpStatus.OK);
	    }
	  
	  @PatchMapping("/update/utilisateurPourModerateur/{id}/{societe}")
	    public ResponseEntity <ResponseOutput <UtilisateurDTO> > updateUtilisateurPourModerateur ( @PathVariable("id") Long id, @PathVariable("societe") Long societe, @RequestBody UtilisateurDTO utilisateurDTO)
	    {
		    UtilisateurID key = new UtilisateurID();
	        key.setId(id);
	        key.setSociete(societe);
	        return  new ResponseEntity<>(iUserService.updatePourModerateur(utilisateurDTO,key), HttpStatus.OK);
	    }
	    
	  @GetMapping(value = {"/show/utilisateur/{fieldName}","/show/utilisateur"})
	    public ResponseEntity <ResponseOutput <UtilisateurDTO> > getAllSocietes ( @PathVariable(name = "fieldName", required = false) String fieldName)
	    {
	        String field = fieldName == null ? "id": fieldName;
	        return new ResponseEntity<>(iUserService.getAllUtilisateur(field),HttpStatus.OK);
	    }
	  @DeleteMapping("/delete/utilisateur/{id}/{societe}")
	    public ResponseEntity <ResponseOutput <UtilisateurDTO> > deleteProfile (@PathVariable ("id") Long id, @PathVariable ("societe") Long societe)
	    {
		  UtilisateurID utilisateurID = new UtilisateurID();
		  utilisateurID.setId(id);
		  utilisateurID.setSociete(societe);
	        return new ResponseEntity<>(iUserService.delete(utilisateurID), HttpStatus.OK);
	    }
	  
	  @GetMapping("/show/utilisateur/societe/id/{id}")
	    public ResponseEntity <ResponseOutput <UtilisateurDTO> > getUtilisateurBySociete ( @PathVariable ("id") Long id)
	    {
	        return new ResponseEntity<>(iUserService.getUtilisateurBySociete(id), HttpStatus.OK);
	    }
	    
	  
	  @GetMapping(value = {"/show/role/{fieldName}"})
	    public ResponseEntity <ResponseOutput <RoleDTO> > getRole ( @PathVariable(name = "fieldName") String fieldName)
	    {
	        return new ResponseEntity<>(iUserService.searchRoleByLibele(fieldName),HttpStatus.OK);
	    }
}
