package org.telio.portail_societe.metier.impl;

import org.springframework.beans.factory.annotation.Autowired;

import java.awt.desktop.PrintFilesEvent;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.telio.portail_societe.dao.EntiteRepository;
import org.telio.portail_societe.dao.ProfilRepository;
import org.telio.portail_societe.dao.SocieteRepository;
import org.telio.portail_societe.dto.converter.EntiteConverter;
import org.telio.portail_societe.dto.converter.ProfilConverter;
import org.telio.portail_societe.dto.converter.SocieteConverter;
import org.telio.portail_societe.dto.entities.ApplicationDTO;
import org.telio.portail_societe.dto.entities.EntiteDTO;
import org.telio.portail_societe.dto.entities.LocaliteDTO;
import org.telio.portail_societe.dto.entities.ProfilDTO;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.dto.entities.SocieteDTO;
import org.telio.portail_societe.dto.entities.TypeEntiteDTO;
import org.telio.portail_societe.dto.entities.UtilisateurDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.metier.interfaces.IHabilitation;
import org.telio.portail_societe.metier.interfaces.IModerateur;
import org.telio.portail_societe.metier.interfaces.IUserService;

@Service
public class IModerateurImpl implements IModerateur{
	@Autowired
	private IHabilitation iHabilitation;
	@Autowired
	private SocieteConverter societeConverter;
	@Autowired
	private SocieteRepository societeRepository;
	@Autowired
	private ProfilConverter profilConverter;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private EntiteRepository entiteRepository;
	@Autowired
	private EntiteConverter entiteConverter;
	@Autowired
	private IUserService iUserService;
	@Override
	public ResponseOutput<SocieteDTO> fonctionAutomatique(Long id) {
		// TODO Auto-generated method stub
		ResponseOutput<SocieteDTO> fctAuto= new ResponseOutput<>();
		
		SocieteDTO wanted = iHabilitation.searchSocieteByID(id).getData();
		
		String nickname = "admin-" +wanted.getNom();
		String nicknam = wanted.getNom();
		String nickcode = "admin-" +wanted.getCode();
		
		TypeEntiteDTO typeEntiteDTO = new TypeEntiteDTO(nickname, nickcode,null, wanted);
		typeEntiteDTO.setCreatedBy("SYSTEM");
		typeEntiteDTO.setCreatedDate(new Date());
		iHabilitation.persist(typeEntiteDTO);
		
		TypeEntiteDTO wantedType = iHabilitation.searchTypeEntiteByNom(nickname).getData();
		
		LocaliteDTO localiteDTO =new LocaliteDTO(nickname, "", "");
		localiteDTO.setCreatedBy("SYSTEM");
		localiteDTO.setCreatedDate(new Date());
		iHabilitation.persist(localiteDTO);
		
		LocaliteDTO wantedLocalite = iHabilitation.searchLocaliteByNom(nickname).getData();	
		
		EntiteDTO entiteDTOo =new EntiteDTO(nickname, nickcode,null, wantedLocalite, wanted, wantedType);
		entiteDTOo.setCreatedBy("SYSTEM");
		entiteDTOo.setCreatedDate(new Date());
		iHabilitation.persist(entiteDTOo);
		
		EntiteDTO entiteDTO = entiteConverter.toVo(entiteRepository.findByNom(nickname).get(0));
		
		ProfilDTO profilDTOo=new ProfilDTO(nickname,wanted);
		profilDTOo.setCreatedBy("SYSTEM");
		profilDTOo.setCreatedDate(new Date());
		iHabilitation.persist(profilDTOo);
		
		 ProfilDTO profilDTO = profilConverter.toVo(profilRepository.findByNom(nickname).get(0));
		 
		ApplicationDTO aplicationDTO =new ApplicationDTO(nickname,"",wanted);
		aplicationDTO.setCreatedBy("SYSTEM");
		aplicationDTO.setCreatedDate(new Date());
		iHabilitation.persist(aplicationDTO);
		
		RoleDTO admin = iUserService.searchRoleByLibele("ADMIN").getData();
        nickname = nicknam.replace(" ", "-");
        nickname =  nickname + "@admin.com"  ;
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO("admin", "admin", nickname, "admin123456", "public", "",
				wanted, entiteDTO, profilDTO, Arrays.asList(admin));
        utilisateurDTO.setCreatedBy("SYSTEM");
        utilisateurDTO.setCreatedDate(new Date());
		iUserService.persist(utilisateurDTO);

//		ApplicationDTO wantedApp = iHabilitation.searchApplicationByNom(nickname).getData();

//		iHabilitation.persist(new MenuDTO(nickname, nickname, "", "", "", -1, wantedApp, null));
	    

		//
		fctAuto.setTypeOperation("POST");

		fctAuto.setCode("500");
		fctAuto.setStatut("SUCCESS");
		fctAuto.setMessage("Lasociete a ete bien ajoute");
        return fctAuto;
        }

	@Override
	public ResponseOutput<SocieteDTO> fonctionGlobale(SocieteDTO societe) {
	     String message = ctrlSociete(societe);
		 ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
	 		iHabilitation.persist(societe);

	     if(message != null)
	     {     

	    	 societeDTOResponseOutput.setTypeOperation("POST");
	         societeDTOResponseOutput.setCode("404");
	         societeDTOResponseOutput.setStatut("ERROR");
	         societeDTOResponseOutput.setMessage(message);
	         return societeDTOResponseOutput;
	     }
		SocieteDTO societeDto = iHabilitation.searchSocieteByNom(societe.getNom()).getData();
		ResponseOutput<SocieteDTO> automatique = fonctionAutomatique(societeDto.getId());
		return automatique;
		
	}
	 @Override
	    public String ctrlSociete(SocieteDTO societeDTO) {
	        if (societeRepository.existsByNom(societeDTO.getNom())) return " Le nom de la sociéte saisie est déjà utilisé !";
	        if (societeRepository.existsByCode(societeDTO.getCode())) return "Le code de la société saisie est déjà utilisé !";
	        return null;
	    }
	 

     

}
