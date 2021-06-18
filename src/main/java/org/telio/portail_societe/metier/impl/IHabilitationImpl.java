package org.telio.portail_societe.metier.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telio.portail_societe.dao.*;
import org.telio.portail_societe.dto.converter.*;
import org.telio.portail_societe.dto.entities.*;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.idClass.*;
import org.telio.portail_societe.metier.interfaces.IHabilitation;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IHabilitationImpl implements IHabilitation {
    @Autowired
    private TypeSocieteRepository typeSocieteRepository;
    @Autowired
    private TypeEntiteRepository typeEntiteRepository;
    @Autowired
    private LocaliteRepository localiteRepository;
    @Autowired
    private ProfilRepository profilRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private EntiteRepository entiteRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private TypeSocieteConverter typeSocieteConverter;
    @Autowired
    private TypeEntiteConverter typeEntiteConverter;
    @Autowired
    private LocaliteConverter localiteConverter;
    @Autowired
    private ProfilConverter profilConverter;
    @Autowired
    private ApplicationConverter applicationConverter;
    @Autowired
    private SocieteConverter societeConverter;
    @Autowired
    private EntiteConverter entiteConverter;
    @Autowired
    private MenuConverter menuConverter;
    @Autowired
    private UtilisateurConverter utilisateurConverter;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    
    @Override
     public String ctrlTypeSociete (TypeSocieteDTO typeSocieteDTO) {
        if(typeSocieteRepository.existsByNom(typeSocieteDTO.getNom())) return "Le nom du type de sociéte est déjà saisie dans la base de données";
        if(typeSocieteRepository.existsByCode(typeSocieteDTO.getCode())) return "Le code du type de sociéte est déjà saisie dans la base de données";
        return null;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> persist(TypeSocieteDTO typeSocieteDTO) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	 typeSocieteDTO.setCreatedBy(authentication.getName());
    	 typeSocieteDTO.setCreatedDate(new Date());
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("POST");
        String message = ctrlTypeSociete(typeSocieteDTO);
        if(message != null)
        {
            societeDTOResponseOutput.setCode("404");
            societeDTOResponseOutput.setStatut("ERROR");
            societeDTOResponseOutput.setMessage(message);
            return societeDTOResponseOutput;
        }
        typeSocieteRepository.save(typeSocieteConverter.toBo(typeSocieteDTO));
        societeDTOResponseOutput.setCode("200");
        societeDTOResponseOutput.setStatut("SUCCESS");
        societeDTOResponseOutput.setMessage("Enregistrement Effectué ! ");
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> update(Long id, TypeSocieteDTO typeSocieteDTO) {
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("PUT");
        TypeSocieteDTO typeSocieteDTO1 = typeSocieteConverter.toVo(typeSocieteRepository.findById(id).get());
        int cpt =0;
        int idc =0;
       
        if(typeSocieteDTO1 != null)
        {
            for (TypeSocieteDTO record : typeSocieteConverter.toVoList(typeSocieteRepository.findAll()))
            {
                cpt += typeSocieteDTO.getNom().equals(record.getNom()) ? 1: 0;
                if(cpt > 0) {
                	idc += id==record.getId() ? 1: 0;
                	cpt=0;

                }
            }
            System.out.println("Compteur : "+cpt);
            if(cpt > 0)
            {
                societeDTOResponseOutput.setCode("404");
                societeDTOResponseOutput.setStatut("ERROR");
                societeDTOResponseOutput.setMessage("Enregistrement Non  Modifié ! ");
            }
            else{
                typeSocieteDTO.setId(id);
                typeSocieteRepository.save(typeSocieteConverter.toBo(typeSocieteDTO));
                societeDTOResponseOutput.setCode("200");
                societeDTOResponseOutput.setStatut("SUCCESS");
                societeDTOResponseOutput.setMessage("Enregistrement   Modifié ! ");
                societeDTOResponseOutput.setData(typeSocieteDTO);
            }

            return societeDTOResponseOutput;
        }
        societeDTOResponseOutput.setCode("500");
        societeDTOResponseOutput.setStatut("NOT FOUND");
        societeDTOResponseOutput.setMessage("Le type de societe Selectionner ne figure pas dans notre base de données");
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> deleteTypeSociete(Long id) {
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("DELETE");
        // 
         if(typeSocieteRepository.existsById(id))
         {
        	 for (SocieteDTO societeDTO : societeConverter.toVoList(societeRepository.findAll()))
        	 {
        		 if (societeDTO.getTypeSocieteDTO().getId() == id)
        		 {
        			 societeDTOResponseOutput.setCode("300");
                     societeDTOResponseOutput.setStatut("WARNING");
                     societeDTOResponseOutput.setMessage("Le type de societe n'a pas été supprimé ,Veuillez suprrimer les societes ayant ce type  ");
                     return societeDTOResponseOutput;
        		 }
        		 
        	 }
        	 typeSocieteRepository.deleteById(id);
        	 societeDTOResponseOutput.setCode("500");
             societeDTOResponseOutput.setStatut("SUCCESS");
             societeDTOResponseOutput.setMessage("Le type de societe a ete bien supprime");
             return societeDTOResponseOutput;
        	 
        	 
         }
         else {
        	 // element not found 
        	 societeDTOResponseOutput.setCode("500");
             societeDTOResponseOutput.setStatut("NOT FOUND");
             societeDTOResponseOutput.setMessage("Le type de societe chercher est introuvable");
             return societeDTOResponseOutput;
         }
        
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> searchTypeSocieteByNom(String nom) {
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        TypeSocieteDTO typeSocieteDTO = typeSocieteConverter.toVo(typeSocieteRepository.findByNom(nom));
        if(typeSocieteDTO != null)
        {
            societeDTOResponseOutput.setStatut("SUCCESS");
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setMessage(" Operation Effectué avec succés ");
            societeDTOResponseOutput.setData(typeSocieteDTO);
        }
        else
        {
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setMessage(" Aucun enregistrement comportant ce nom :  "+nom);
        }
        return societeDTOResponseOutput;
    }
    
    @Override
    public ResponseOutput <TypeSocieteDTO> searchTypeSocieteByID (Long id){
    	
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
       TypeSocieteDTO typeSocieteDTO = typeSocieteConverter.toVo(typeSocieteRepository.findById(id).get());
        if(typeSocieteDTO != null)
        {
            societeDTOResponseOutput.setStatut("SUCCESS");
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setMessage(" Operation Effectué avec succés ");
            societeDTOResponseOutput.setData(typeSocieteDTO);
        }
        else
        {
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setMessage(" Aucun enregistrement comportant ce nom :  "+id);
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> searchTypeSocieteByCode(String code) {

        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        TypeSocieteDTO typeSocieteDTO = typeSocieteConverter.toVo(typeSocieteRepository.findByCode(code));
        if(typeSocieteDTO != null)
        {
            societeDTOResponseOutput.setStatut("SUCCESS");
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setMessage(" Operation Effectué avec succés ");
            societeDTOResponseOutput.setData(typeSocieteDTO);
        }
        else
        {
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setMessage(" Aucun enregistrement comportant ce nom :  "+code);
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> searchTypeSocieteByStatut(String statut) {
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List<TypeSocieteDTO> typeSocieteDTO = typeSocieteConverter.toVoList(typeSocieteRepository.findByStatut(statut));
        if(typeSocieteDTO != null)
        {
            societeDTOResponseOutput.setStatut("SUCCESS");
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setMessage(" Operation Effectué avec succés ");
            societeDTOResponseOutput.setCollection(typeSocieteDTO);
        }
        else
        {
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setMessage(" Aucun enregistrement comportant ce nom :  "+statut);
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> getAllTypeSocieteSortBy(String fieldName) {
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List <TypeSocieteDTO> typeSocieteDTO = typeSocieteConverter.toVoList(typeSocieteRepository.findAll(Sort.by(fieldName)));
        if(typeSocieteDTO != null)
        {
            societeDTOResponseOutput.setStatut("SUCCESS");
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setMessage(" Operation Effectué avec succés ");
            societeDTOResponseOutput.setCollection(typeSocieteDTO);
        }
        else
        {
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setMessage(" Aucun enregistrement trouver ! ");
        }
        return societeDTOResponseOutput;
    }


    @Override
    public String ctrlSociete(SocieteDTO societeDTO) {
        if (societeRepository.existsByNom(societeDTO.getNom())) return " Le nom de la sociéte saisie est déjà utilisé !";
        if (societeRepository.existsByCode(societeDTO.getCode())) return "Le code de la société saisie est déjà utilisé !";
        return null;
    }

    @Override
    public ResponseOutput<SocieteDTO> persist(SocieteDTO societeDTO) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("POST");
        String message = ctrlSociete(societeDTO);

        if(message != null)
        {
            societeDTOResponseOutput.setCode("404");
            societeDTOResponseOutput.setStatut("ERROR");
            societeDTOResponseOutput.setMessage(message);
            return societeDTOResponseOutput;
        }
        societeRepository.save(societeConverter.toBo(societeDTO));
        societeDTOResponseOutput.setCode("200");
        societeDTOResponseOutput.setStatut("SUCCESS");
        societeDTOResponseOutput.setMessage("Société Inséré");
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<SocieteDTO> update(Long id, SocieteDTO societeDTO) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("PATCH");
        if (societeRepository.existsById(id))
        {
            SocieteDTO societeWantedDTO = societeConverter.toVo(societeRepository.getOne(id));
            // identical records
            if(societeWantedDTO.getNom().equals(societeDTO.getNom()) && societeWantedDTO.getCode().equals(societeDTO.getCode())
               && societeWantedDTO.getNomAbrege().equals(societeDTO.getNomAbrege()) && societeWantedDTO.getTypeSocieteDTO().getId() == societeDTO.getTypeSocieteDTO().getId())
            {
                System.out.println("IDENTICAL ...");
                societeDTOResponseOutput.setCode("300");
                societeDTOResponseOutput.setStatut("WARNING");
                societeDTOResponseOutput.setMessage("L'enregistrement n'a subit aucun changement !");
                societeDTOResponseOutput.setData(societeWantedDTO);
                return societeDTOResponseOutput;
            }
            // same name
            if(societeWantedDTO.getNom().equals(societeDTO.getNom()) && societeWantedDTO.getTypeSocieteDTO().getId() != societeDTO.getTypeSocieteDTO().getId())
            {
                System.out.println("SAME NAME BUT DIFFERENT TYPE....");
                societeDTOResponseOutput.setCode("200");
                societeDTOResponseOutput.setStatut("SUCCESS");
                societeDTOResponseOutput.setMessage(" Modification effectué");
                societeDTO.setId(id);
                societeRepository.save(societeConverter.toBo(societeDTO));
                societeDTOResponseOutput.setData(societeDTO);
                return societeDTOResponseOutput;
            }
            /**/
            int cpt = 0;
//                for (SocieteDTO record : societeConverter.toVoList(societeRepository.findAll())) {
//                cpt += societeDTO.getNom().equals(record.getNom()) ? 1 : 0;
//                System.out.println("==============================");
//                System.out.println("record name :"+ record.getNom());
//                System.out.println("societe DTO :"+societeDTO.getNom());
//                System.out.println("==============================");
//            }
            System.out.println("Compteur : " + cpt);
            if (cpt > 0) {
                societeDTOResponseOutput.setCode("404");
                societeDTOResponseOutput.setStatut("ERROR");
                societeDTOResponseOutput.setMessage("Enregistrement Non  Modifié ! ");
            } else {
                societeDTO.setId(id);
                societeRepository.save(societeConverter.toBo(societeDTO));
                societeDTOResponseOutput.setCode("200");
                societeDTOResponseOutput.setStatut("SUCCESS");
                societeDTOResponseOutput.setMessage("Enregistrement   Modifié ! ");
                societeDTOResponseOutput.setData(societeDTO);
            }
            return societeDTOResponseOutput;

        }
        societeDTOResponseOutput.setStatut("NOT FOUND");
        societeDTOResponseOutput.setCode("500");
        societeDTOResponseOutput.setMessage("La societe Selectionné ne figure pas dans notre base ! ");
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<SocieteDTO> deleteSociete(Long id) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("DELETE");
        if(societeRepository.existsById(id))
        {
	        for (TypeEntiteDTO typeEntiteDTO : typeEntiteConverter.toVoList(typeEntiteRepository.findAll()))
	   	 		{
			   		 if (typeEntiteDTO.getSocieteDTO().getId() != null)
			   		 {
				   		 if (typeEntiteDTO.getSocieteDTO().getId() == id) {

			   			 	societeDTOResponseOutput.setCode("300");
			                societeDTOResponseOutput.setStatut("WARNING");
			                societeDTOResponseOutput.setMessage("La societe n'a pas été supprimé ,Veuillez suprrimer les types entites ayant ce type  ");
			                return societeDTOResponseOutput;
				   		 }
			   		 }
	   		 
	   	 		}
        
        
	        for (EntiteDTO entiteDTO : entiteConverter.toVoList(entiteRepository.findAll()))
	     	 	{
		     		 if (entiteDTO.getSocieteDTO().getId() != null)
		     		 {
		     			if (entiteDTO.getSocieteDTO().getId() == id)
			     		 {
		     			   societeDTOResponseOutput.setCode("300");
		                  societeDTOResponseOutput.setStatut("WARNING");
		                  societeDTOResponseOutput.setMessage("La societe n'a pas été supprimé ,Veuillez suprrimer les entites ayant ce type  ");
		                  return societeDTOResponseOutput;}
		     		 }
		     		 
		     	 } 
//        	
//	        
//	       	for (UtilisateurDTO utilisateurDTO : utilisateurConverter.toVoList(utilisateurRepository.findAll()))
//	      	 	{
//		      		 if (utilisateurDTO.getSocieteDTO().getId() != null)
//		      		 {
//		      			if (utilisateurDTO.getSocieteDTO().getId() == id)
//			      		 {
//		      			   societeDTOResponseOutput.setCode("300");
//		                   societeDTOResponseOutput.setStatut("WARNING");
//		                   societeDTOResponseOutput.setMessage("La societe n'a pas été supprimé ,Veuillez suprrimer les entites ayant ce type  ");
//		                   return societeDTOResponseOutput;}
//		      		 }
//		      		 
//		      	 }
//	        
//	         
	        for (ProfilDTO profilDTO : profilConverter.toVoList(profilRepository.findAll()))
	        	 {
	        		 if (profilDTO.getSocieteDTO().getId() != null)
	        		 {
	        			 if (profilDTO.getSocieteDTO().getId() == id)
		        		 {
	        			   societeDTOResponseOutput.setCode("300");
	                     societeDTOResponseOutput.setStatut("WARNING");
	                     societeDTOResponseOutput.setMessage("La societe n'a pas été supprimé ,Veuillez suprrimer les profils ayant ce type  ");
	                     return societeDTOResponseOutput;}
	        		 }
	        		 
	        	 }
	         
	        for (ApplicationDTO applicationDTO : applicationConverter.toVoList(applicationRepository.findAll()))
	         	 {
	         		 if (applicationDTO.getSocieteDTO().getId() != null)
	         		 {
	         			if (applicationDTO.getSocieteDTO().getId() == id)
		         		 {
	         			   societeDTOResponseOutput.setCode("300");
	                      societeDTOResponseOutput.setStatut("WARNING");
	                      societeDTOResponseOutput.setMessage("La societe n'a pas été supprimé ,Veuillez suprrimer les applications ayant ce type  ");
	                      return societeDTOResponseOutput;}
	         		 }
	         		 
	         	 }
	        societeRepository.deleteById(id);
	        societeDTOResponseOutput.setStatut("SUCCES");
	        societeDTOResponseOutput.setCode("200");
	        societeDTOResponseOutput.setMessage("La societe a été supprimé ! ");
	        return societeDTOResponseOutput;
	         }
        societeDTOResponseOutput.setCode("500");
        societeDTOResponseOutput.setStatut("NOT FOUND");
        societeDTOResponseOutput.setMessage("Lae de societe chercher est introuvable");
        return societeDTOResponseOutput;
        
    }

    @Override
    public ResponseOutput<SocieteDTO> searchSocieteByID(Long  id){

    	 ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
         societeDTOResponseOutput.setTypeOperation("GET");
         SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
         if(societeDTO != null)
         {
             societeDTOResponseOutput.setCode("200");
             societeDTOResponseOutput.setStatut("SUCCES");
             societeDTOResponseOutput.setMessage("Operation Effectué !");
             societeDTOResponseOutput.setData(societeDTO);

         }
         else
         {
             societeDTOResponseOutput.setCode("500");
             societeDTOResponseOutput.setStatut("NOT FOUND");
             societeDTOResponseOutput.setMessage("Operation Effectué !");
         }
         return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<SocieteDTO> searchSocieteByNom(String nom) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findByNom(nom));
        if(societeDTO != null)
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
            societeDTOResponseOutput.setData(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<SocieteDTO> searchSocieteByCode(String code) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findByCode(code));
        if(societeDTO != null)
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
            societeDTOResponseOutput.setData(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<SocieteDTO> searchSocieteByNomAbrege(String nomAbrege) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findByNomAbrege(nomAbrege));
        if(societeDTO != null)
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
            societeDTOResponseOutput.setData(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
        }
        return societeDTOResponseOutput;

    }

    @Override
    public ResponseOutput<SocieteDTO> searchSocieteByStatut(String statut) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List<SocieteDTO> societeDTO = societeConverter.toVoList(societeRepository.findByStatut(statut));
        if( societeDTO.size() !=0 )
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
            societeDTOResponseOutput.setCollection(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
        }
        return societeDTOResponseOutput;

    }

    @Override
    public ResponseOutput<SocieteDTO> getAllSocietesSortBy(String fieldName) {
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List<SocieteDTO> societeDTO = societeConverter.toVoList(societeRepository.findAll(Sort.by(fieldName)));
        if( societeDTO.size() !=0 )
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
            societeDTOResponseOutput.setCollection(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectué !");
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> persist(ApplicationDTO applicationDTO) {
        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("POST");
        applicationRepository.save(applicationConverter.toBo(applicationDTO));
        applicationDTOResponseOutput.setStatut("SUCCESS");
        applicationDTOResponseOutput.setCode("200");
        applicationDTOResponseOutput.setMessage("Application Ajouté !");
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> update(ApplicationID applicationID, ApplicationDTO applicationDTO) {
        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("PATCH | PUT");
        if (applicationRepository.existsById(applicationID))
        {
            applicationDTOResponseOutput.setCode("200");
            applicationDTOResponseOutput.setStatut("SUCCES");
            applicationDTOResponseOutput.setMessage("Element Modifier ! ");
            applicationDTO.setId(applicationID.getId());
            SocieteDTO wanted  = societeConverter.toVo(societeRepository.findById(applicationID.getSociete()).get());
            applicationDTO.setSocieteDTO(wanted);
            applicationRepository.save(applicationConverter.toBo(applicationDTO));
            applicationDTOResponseOutput.setData(applicationDTO);
        }
        else
        {
            applicationDTOResponseOutput.setCode("500");
            applicationDTOResponseOutput.setStatut("NOT FOUND");
            applicationDTOResponseOutput.setMessage("Element introuvable ! ");
        }
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> deleteApplication(ApplicationID applicationID) {
        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("DELETE");
        if (applicationRepository.existsById(applicationID))
        {
            applicationRepository.deleteById(applicationID);
            applicationDTOResponseOutput.setCode("200");
            applicationDTOResponseOutput.setStatut("SUCCES");
            applicationDTOResponseOutput.setMessage("Application Supprimer");

        }
        else
        {
            applicationDTOResponseOutput.setCode("500");
            applicationDTOResponseOutput.setStatut("NOT FOUND");
            applicationDTOResponseOutput.setMessage("Application non  Supprimé");
        }
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> searchApplicationByNom(String nom) {
        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("GET");
        List <ApplicationDTO> applicationDTO = applicationConverter.toVoList(applicationRepository.findByNom(nom));
        if (applicationDTO.size() == 0)
        {
            applicationDTOResponseOutput.setCode("500");
            applicationDTOResponseOutput.setStatut("NOT FOUND");
            applicationDTOResponseOutput.setMessage(" Application "+nom+" est introuvable dans notre base ");
        }
        else{
            applicationDTOResponseOutput.setCode("200");
            applicationDTOResponseOutput.setStatut("SUCCES");
            applicationDTOResponseOutput.setMessage(" Operation effectuer ");
            applicationDTOResponseOutput.setCollection(applicationDTO);
        }
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> searchApplicationByID(Long id, Long societe) {

        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("GET");
        ApplicationID applicationID = new ApplicationID();
        applicationID.setId(id);
        applicationID.setSociete(societe);
        ApplicationDTO applicationDTO = applicationConverter.toVo(applicationRepository.findById(applicationID).get());
        if (applicationDTO == null)
        {
            applicationDTOResponseOutput.setCode("500");
            applicationDTOResponseOutput.setStatut("NOT FOUND");
            applicationDTOResponseOutput.setMessage(" Application est introuvable dans notre base ");
        }
        else{
            applicationDTOResponseOutput.setCode("200");
            applicationDTOResponseOutput.setStatut("SUCCES");
            applicationDTOResponseOutput.setMessage(" Operation effectuer ");
            applicationDTOResponseOutput.setData(applicationDTO);
        }
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> getAllApplicationsSortBy(String fieldName) {

        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("GET");

        List <ApplicationDTO> applicationDTO = applicationConverter.toVoList(applicationRepository.findAll(Sort.by(fieldName)));
        if (applicationDTO == null)
        {
            applicationDTOResponseOutput.setCode("500");
            applicationDTOResponseOutput.setStatut("NOT FOUND");
            applicationDTOResponseOutput.setMessage(" Application est introuvable dans notre base ");
        }
        else{
            applicationDTOResponseOutput.setCode("200");
            applicationDTOResponseOutput.setStatut("SUCCES");
            applicationDTOResponseOutput.setMessage(" Operation effectuer ");
            applicationDTOResponseOutput.setCollection(applicationDTO);
        }
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> persist(ProfilDTO profilDTO) {
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("POST");

        if (profilRepository.existsByNom(profilDTO.getNom()))
        {
            List <ProfilDTO> wanted = profilConverter.toVoList(profilRepository.findByNom(profilDTO.getNom()));
            wanted.forEach( data ->{
                if (data.getSocieteDTO().getId() == data.getSocieteDTO().getId())
                {
                    profilDTOResponseOutput.setMessage(" Ce profil est déja inséré dans notre base ");
                }
            });
            profilDTOResponseOutput.setStatut("ERROR");
            profilDTOResponseOutput.setCode("404");
            profilDTOResponseOutput.setMessage(profilDTOResponseOutput.getMessage() == null ? "Le nom du profil  est déja utilisé dans notre base" : profilDTOResponseOutput.getMessage());
        }
        else
        {
            profilDTOResponseOutput.setStatut("SUCCES");
            profilDTOResponseOutput.setCode("200");
            profilRepository.save(profilConverter.toBo(profilDTO));
            profilDTOResponseOutput.setMessage(" Profil Ajouté !");
        }
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> update(ProfilID profilID, ProfilDTO profilDTO) {
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("PATCH");
        if(profilRepository.existsById(profilID))
        {
            ProfilDTO wanted  = profilConverter.toVo(profilRepository.findById(profilID).get());
            System.out.println("ID : "+ wanted.getNom());
            if (wanted.getNom().equalsIgnoreCase(profilDTO.getNom()) && profilID.getSociete() == wanted.getSocieteDTO().getId())
            {
                profilDTOResponseOutput.setCode("300");
                profilDTOResponseOutput.setStatut("WARNING");
                profilDTOResponseOutput.setMessage("Vous n'avez rien modifier au niveau du profil ! ");
                profilDTOResponseOutput.setData(wanted);
                return profilDTOResponseOutput;
            }
            profilDTO.setId(profilID.getId());
            profilDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(profilID.getSociete()).get()));
            profilRepository.save(profilConverter.toBo(profilDTO));
            profilDTOResponseOutput.setCode("200");
            profilDTOResponseOutput.setStatut("SUCCESS");
            profilDTOResponseOutput.setMessage("Profil Modifié");
            profilDTOResponseOutput.setData(profilDTO);
            return profilDTOResponseOutput;
        }
        profilDTOResponseOutput.setCode("500");
        profilDTOResponseOutput.setStatut("NOT FOUND");
        profilDTOResponseOutput.setMessage("Le profil que vous cherchez est introuvable " );
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> deleteProfil(ProfilID profilID) {
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("DELETE");
        if (profilRepository.existsById(profilID))
        {
            profilDTOResponseOutput.setCode("200");
            profilDTOResponseOutput.setStatut("SUCCES");
            profilDTOResponseOutput.setMessage(" Le profil a été supprimé ! ");
            profilRepository.deleteById(profilID);
        }
        else
        {
            profilDTOResponseOutput.setCode("500");
            profilDTOResponseOutput.setStatut("NOT FOUND");
            profilDTOResponseOutput.setMessage("Le profil que vous voulez supprimé ne figure pas dans notre liste des profils ");

        }
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> searchProfilByNom(String nom) {
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("GET");
        List <ProfilDTO> profilDTOS = profilConverter.toVoList(profilRepository.findByNom(nom));
        if(profilDTOS.size() == 0)
        {
            profilDTOResponseOutput.setCode("300");
            profilDTOResponseOutput.setStatut("NO DATA FOUND");
            profilDTOResponseOutput.setMessage("Aucun Profil comportant ce nom !");

        }
        else {
            profilDTOResponseOutput.setCode("200");
            profilDTOResponseOutput.setStatut("SUCCES");
            profilDTOResponseOutput.setMessage(" Profil trouvé !");
            profilDTOResponseOutput.setCollection(profilDTOS);

        }
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> searchProfilByID(Long id, Long societe) {
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("GET");
        ProfilID key = new ProfilID();
        key.setId(id);
        key.setSociete(societe);
        if (profilRepository.existsById(key))
        {
            profilDTOResponseOutput.setCode("200");
            profilDTOResponseOutput.setStatut("SUCCES");
            profilDTOResponseOutput.setMessage("Profil Trouvé ! ");
            profilDTOResponseOutput.setData(profilConverter.toVo(profilRepository.findById(key).get()));
        }
        else
        {
            profilDTOResponseOutput.setCode("500");
            profilDTOResponseOutput.setStatut("NOT FOUND");
            profilDTOResponseOutput.setMessage("Ce profil ne figure pas dans notre base ");
        }
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> getAllProfilsSortBy(String fieldName) {
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("GET");
        List <ProfilDTO> profilDTOList = profilConverter.toVoList(profilRepository.findAll(Sort.by(fieldName)));
        if(profilDTOList.size() != 0)
        {
            profilDTOResponseOutput.setCode("200");
            profilDTOResponseOutput.setStatut("SUCCES");
            profilDTOResponseOutput.setMessage(" Liste des profils grouper par "+ fieldName);
            profilDTOResponseOutput.setCollection(profilDTOList);
        }
        else
        {
            profilDTOResponseOutput.setCode("300");
            profilDTOResponseOutput.setStatut("NO DATA FOUND ");
            profilDTOResponseOutput.setMessage("Aucun profil n'est disponible !");
        }
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> persist(LocaliteDTO localiteDTO) {
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("POST");
        if(localiteRepository.existsByNom(localiteDTO.getNom()))
        {
            localiteDTOResponseOutput.setCode("404");
            localiteDTOResponseOutput.setStatut("ERROR");
            localiteDTOResponseOutput.setMessage(" Cet Localité est déjà inséré dans notre base ");
        }
        else
        {
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localité ajouté !");
            localiteRepository.save(localiteConverter.toBo(localiteDTO));

        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> update(Long id, LocaliteDTO localiteDTO) {
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("PATCH");
        if(localiteRepository.existsById(id))
        {
            LocaliteDTO wanted = localiteConverter.toVo(localiteRepository.findById(id).get());
            if(wanted.getNom().equalsIgnoreCase(localiteDTO.getNom())
                    && wanted.getCode().equalsIgnoreCase(localiteDTO.getCode())
                    && wanted.getNomAbrege().equalsIgnoreCase(localiteDTO.getNomAbrege()))
            {
                localiteDTOResponseOutput.setCode("300");
                localiteDTOResponseOutput.setStatut("WARNING");
                localiteDTOResponseOutput.setMessage(" Vous n'avez rien changer au niveau de la localité ");
                return localiteDTOResponseOutput;
            }

            if (localiteRepository.existsByNom(localiteDTO.getNom()) ||
                localiteRepository.existsByCode(localiteDTO.getCode()) ||
                localiteRepository.existsByNomAbrege(localiteDTO.getNomAbrege()))
            {
                localiteDTOResponseOutput.setCode("404");
                localiteDTOResponseOutput.setStatut("ERROR");
                localiteDTOResponseOutput.setMessage("Cet Localité est déjà insérer dans notre base ");
                return localiteDTOResponseOutput;
            }
            else
            {
                localiteDTO.setId(id);
                localiteRepository.save(localiteConverter.toBo(localiteDTO));
                localiteDTOResponseOutput.setCode("200");
                localiteDTOResponseOutput.setStatut("SUCCES");
                localiteDTOResponseOutput.setMessage("Localité ajouté ! ");
                return localiteDTOResponseOutput;

            }

        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND");
            localiteDTOResponseOutput.setMessage(" Cet localité ne figure pas dans notre base de donnée");
        }
        return null;
    }

    @Override
    public ResponseOutput<LocaliteDTO> deleteLocalite(Long id) {
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("DELETE");
        if(localiteRepository.existsById(id))
        {
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localité supprimé ! ");
            localiteRepository.deleteById(id);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" La localité n'a pas été supprimé ! ");
        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> searchLocaliteByNom(String nom) {
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("GET");
        LocaliteDTO localiteDTO = localiteConverter.toVo(localiteRepository.findByNom(nom));
        if (localiteDTO != null)
        {
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localité trouvé ! ");
            localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localité trouvé comportant le nom [ " + nom+ " ] ");
        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> searchLocaliteByCode(String code) {

        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("GET");
        LocaliteDTO localiteDTO = localiteConverter.toVo(localiteRepository.findByCode(code));
        if (localiteDTO != null)
        {
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localité trouvé ! ");
            localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localité trouvé comportant le code [ " + code+ " ] ");
        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> searchLocaliteByNomAbrege(String nomAbrege) {
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("GET");
        LocaliteDTO localiteDTO = localiteConverter.toVo(localiteRepository.findByNomAbrege(nomAbrege));
        if (localiteDTO != null)
        {
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localité trouvé ! ");
            localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localité trouvé comportant l'abreviation  [ " + nomAbrege+ " ] ");
        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> getAllLocaliteSortBy (String fieldName) {

        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("GET");
        List <LocaliteDTO> localiteDTO = localiteConverter.toVoList(localiteRepository.findAll(Sort.by(fieldName)));
        if (localiteDTO.size() != 0)
        {
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localité trouvé ! ");
            localiteDTOResponseOutput.setCollection(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localité trouvé ");
        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> persist(TypeEntiteDTO typeEntiteDTO) {
        ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("POST");
        if (typeEntiteRepository.existsByNom(typeEntiteDTO.getNom().toUpperCase()) ||
                typeEntiteRepository.existsByCode(typeEntiteDTO.getCode().toUpperCase()) &&
            typeEntiteRepository.existsBySociete(societeConverter.toBo(typeEntiteDTO.getSocieteDTO()))
            )
        {
            typeEntiteDTOResponseOutput.setCode("404");
            typeEntiteDTOResponseOutput.setStatut("ERROR");
            typeEntiteDTOResponseOutput.setMessage(" Ce type d'entite est déjà définit pour cet societe");
            return typeEntiteDTOResponseOutput;
        }
        typeEntiteRepository.save(typeEntiteConverter.toBo(typeEntiteDTO));
        typeEntiteDTOResponseOutput.setCode("200");
        typeEntiteDTOResponseOutput.setStatut("SUCCES");
        typeEntiteDTOResponseOutput.setMessage("Le type d'entité " + typeEntiteDTO.getNom() + " a été bien inséré");
        return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> update(TypeEntiteID typeEntiteID, TypeEntiteDTO typeEntiteDTO) {
        ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("PATCH");
        // SAME OBJECT
        if(typeEntiteRepository.existsById(typeEntiteID))
        {
            // same object
            TypeEntiteDTO wanted = typeEntiteConverter.toVo(typeEntiteRepository.findById(typeEntiteID).get());
            if(wanted.getNom().equalsIgnoreCase(typeEntiteDTO.getNom()) &&
               wanted.getCode().equalsIgnoreCase(typeEntiteDTO.getCode()) &&
               wanted.getSocieteDTO().getId() == typeEntiteDTO.getSocieteDTO().getId())
            {
                typeEntiteDTOResponseOutput.setCode("300");
                typeEntiteDTOResponseOutput.setStatut("WARNING");
                typeEntiteDTOResponseOutput.setMessage("Vous n'avez effectuez aucun changement sur l'objet");
                typeEntiteDTOResponseOutput.setData(typeEntiteDTO);
                return typeEntiteDTOResponseOutput;
            }
            for (TypeEntiteDTO typeEntiteDTO1  : typeEntiteConverter.toVoList(typeEntiteRepository.findAll()))
            {
                if (typeEntiteDTO1.getSocieteDTO().getId() == typeEntiteDTO.getSocieteDTO().getId() &&
                    typeEntiteDTO1.getId() != typeEntiteID.getId())
                {
                    if(typeEntiteDTO1.getNom().equalsIgnoreCase(typeEntiteDTO.getNom()))
                    {
                     typeEntiteDTOResponseOutput.setCode("404");
                     typeEntiteDTOResponseOutput.setStatut("ERROR");
                     typeEntiteDTOResponseOutput.setMessage(" Ce nom est déja utiliser pour cette societe ");
                     return typeEntiteDTOResponseOutput;
                    }
                    if(typeEntiteDTO1.getCode().equalsIgnoreCase(typeEntiteDTO.getCode()))
                    {
                        typeEntiteDTOResponseOutput.setCode("404");
                        typeEntiteDTOResponseOutput.setStatut("ERROR");
                        typeEntiteDTOResponseOutput.setMessage(" Ce code est déja utiliser pour cette societe ");
                        return typeEntiteDTOResponseOutput;
                    }
                }
            }

            typeEntiteDTO.setId(typeEntiteID.getId());
            typeEntiteDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(typeEntiteID.getSociete()).get()));
            typeEntiteRepository.save(typeEntiteConverter.toBo(typeEntiteDTO));
            typeEntiteDTOResponseOutput.setCode("200");
            typeEntiteDTOResponseOutput.setStatut("SUCCES");
            typeEntiteDTOResponseOutput.setMessage("Type Entité modifié ");

        }
        else
        {
            typeEntiteDTOResponseOutput.setCode("500");
            typeEntiteDTOResponseOutput.setStatut("NOT FOUND");
            typeEntiteDTOResponseOutput.setMessage(" Le type d'entité rechercher est introuvable");
        }
       return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> deleteTypeEntite(TypeEntiteID typeEntiteID) {
        ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("DELETE");
        if(typeEntiteRepository.existsById(typeEntiteID))
        {
            typeEntiteDTOResponseOutput.setCode("200");
            typeEntiteDTOResponseOutput.setStatut("SUCCES");
            typeEntiteDTOResponseOutput.setMessage(" Le type a été bien supprimer");
            typeEntiteRepository.deleteById(typeEntiteID);
        }
        else
        {
            typeEntiteDTOResponseOutput.setCode("500");
            typeEntiteDTOResponseOutput.setStatut("NOT FOUND");
            typeEntiteDTOResponseOutput.setMessage("Aucun element n'est supprimé");
        }
        return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> searchTypeEntiteByNom(String nom) {
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");
        typeEntiteDTOResponseOutput.setData(typeEntiteConverter.toVo(typeEntiteRepository.findByNom(nom).get(0)));
        return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> searchTypeEntiteByCode(String code) {
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");
        typeEntiteDTOResponseOutput.setData(typeEntiteConverter.toVo(typeEntiteRepository.findByCode(code).get(0)));
        return typeEntiteDTOResponseOutput;
    }
   
    
    @Override
    public ResponseOutput<TypeEntiteDTO> searchTypeEntiteByID(Long id, Long societe) {
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");
        typeEntiteDTOResponseOutput.setData(typeEntiteConverter.toVo(typeEntiteRepository.findById(new TypeEntiteID(id, societe)).get()));
        return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> getTypeEntitiesBySociete(Long id) {
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");
        typeEntiteDTOResponseOutput.setData(typeEntiteConverter.toVo(typeEntiteRepository.rechercheBysociete(id)));
        return typeEntiteDTOResponseOutput;
    }

    
    @Override
    public ResponseOutput<TypeEntiteDTO> searchTypeEntiteMere() {
        return null;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> getAllTypeEntitiesSortBy(String fieldName) {
        return null;
    }

    @Override
   	public String ctrlEntite(EntiteDTO entiteDTO) {
       	if(entiteRepository.existsByNom(entiteDTO.getNom())&&entiteRepository.existsBySociete(entiteDTO.getSocieteDTO().getId())) return "L'entite est déjà saisie dans la base de données";
           return null;
   	}
       
       @Override
       public ResponseOutput<EntiteDTO> persist(EntiteDTO entiteDTO) {
       	ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
       	entiteDTOResponseOutput.setTypeOperation("POST");
           String message = ctrlEntite(entiteDTO);
           if(message != null)
           {
           	entiteDTOResponseOutput.setCode("404");
           	entiteDTOResponseOutput.setStatut("ERROR");
           	entiteDTOResponseOutput.setMessage(message);
               return entiteDTOResponseOutput;
           }
           entiteRepository.save(entiteConverter.toBo(entiteDTO));
           entiteDTOResponseOutput.setCode("200");
           entiteDTOResponseOutput.setStatut("SUCCESS");
           entiteDTOResponseOutput.setMessage("Enregistrement Effectué ! ");
           return entiteDTOResponseOutput;
       }

    @Override
    public ResponseOutput<EntiteDTO> update(EntiteID entiteID, EntiteDTO entiteDTO) {
        return null;
    }

    @Override
    public ResponseOutput<EntiteDTO> deleteEntite(EntiteDTO entiteDTO) {
        return null;
    }

    @Override
    public ResponseOutput<EntiteDTO> searchEntiteByNom(String nom) {
    	ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
   	 entiteDTOResponseOutput.setTypeOperation("GET");
        List <EntiteDTO> entiteDTO = entiteConverter.toVoList(entiteRepository.findByNom(nom));
        if(entiteDTO.size() == 0)
        {
       	 entiteDTOResponseOutput.setCode("200");
       	 entiteDTOResponseOutput.setStatut("SUCCES");
       	 entiteDTOResponseOutput.setMessage("Operation Effectué !");
       	 entiteDTOResponseOutput.setCollection(entiteDTO);

        }
        else
        {
       	 entiteDTOResponseOutput.setCode("500");
       	 entiteDTOResponseOutput.setStatut("NOT FOUND");
       	 entiteDTOResponseOutput.setMessage("Operation Effectué !");
        }
        return entiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<EntiteDTO> searchEntiteByCode(String code) {
        return null;
    }

    @Override
    public ResponseOutput<EntiteDTO> searchEntiteByID(Long id, Long societe) {
        return null;
    }

    @Override
    public ResponseOutput<EntiteDTO> searchEntiteMere() {
        return null;
    }

    @Override
    public ResponseOutput<EntiteDTO> getAllEntitiesSortBy(String fieldName) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> persist(MenuDTO menuDTO) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> update(MenuID menuID, MenuDTO menuDTO) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> deleteMenu(MenuID menuID) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> searchMenuByNom(String nom) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> searchMenyByType(String type) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> searchMenuByLien(String lien) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> searchMenuByParametres(String parametres) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> searchMenuByID(Long id, Long societe, Long application) {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> searchMenuMere() {
        return null;
    }

    @Override
    public ResponseOutput<MenuDTO> getAllMenusSortBy(String fieldName) {
        return null;
    }

	@Override
	public ResponseOutput<TypeEntiteDTO> getAllTypeEntitiesBySociete(SocieteDTO societeDTO) {
		ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");
        typeEntiteDTOResponseOutput.setCollection(typeEntiteConverter.toVoList(typeEntiteRepository.findBySociete(societeConverter.toBo(societeDTO))));
        return typeEntiteDTOResponseOutput;
	}

	 
}
