package org.telio.portail_societe.metier.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.telio.portail_societe.dao.*;
import org.telio.portail_societe.dto.converter.*;
import org.telio.portail_societe.dto.entities.*;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.idClass.*;
import org.telio.portail_societe.metier.interfaces.IHabilitation;
import org.telio.portail_societe.model.Application;
import org.telio.portail_societe.model.PieceJointe;
import org.telio.portail_societe.model.ProfilMenu;

import com.ctc.wstx.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
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
    private ProfilMenuRepository profilMenuRepository;
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
    private ProfilMenuConverter profilMenuConverter;
    @Autowired
    private UtilisateurConverter utilisateurConverter;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Override
     public String ctrlTypeSociete (TypeSocieteDTO typeSocieteDTO) {
        if(typeSocieteRepository.existsByNom(typeSocieteDTO.getNom())) return "Le nom du type de soci??te est d??j?? saisie dans la base de donn??es";
        if(typeSocieteRepository.existsByCode(typeSocieteDTO.getCode())) return "Le code du type de soci??te est d??j?? saisie dans la base de donn??es";
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
        societeDTOResponseOutput.setMessage("Enregistrement Effectu?? ! ");
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeSocieteDTO> update(Long id, TypeSocieteDTO typeSocieteDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	typeSocieteDTO.setLastModifiedBy(authentication.getName());
    	typeSocieteDTO.setLastModifedDate(new Date());
        ResponseOutput <TypeSocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("PUT");
        TypeSocieteDTO typeSocieteDTO1 = typeSocieteConverter.toVo(typeSocieteRepository.findById(id).get());
        typeSocieteDTO.setCreatedBy(typeSocieteDTO1.getCreatedBy());
        typeSocieteDTO.setCreatedDate(typeSocieteDTO1.getCreatedDate());
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
                societeDTOResponseOutput.setMessage("Enregistrement Non  Modifi?? ! ");
            }
            else{
                typeSocieteDTO.setId(id);
                typeSocieteRepository.save(typeSocieteConverter.toBo(typeSocieteDTO));
                societeDTOResponseOutput.setCode("200");
                societeDTOResponseOutput.setStatut("SUCCESS");
                societeDTOResponseOutput.setMessage("Enregistrement   Modifi?? ! ");
                societeDTOResponseOutput.setData(typeSocieteDTO);
            }

            return societeDTOResponseOutput;
        }
        societeDTOResponseOutput.setCode("500");
        societeDTOResponseOutput.setStatut("NOT FOUND");
        societeDTOResponseOutput.setMessage("Le type de societe Selectionner ne figure pas dans notre base de donn??es");
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
                     societeDTOResponseOutput.setMessage("Le type de societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les societes ayant ce type  ");
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
            societeDTOResponseOutput.setMessage(" Operation Effectu?? avec succ??s ");
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
            societeDTOResponseOutput.setMessage(" Operation Effectu?? avec succ??s ");
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
            societeDTOResponseOutput.setMessage(" Operation Effectu?? avec succ??s ");
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
            societeDTOResponseOutput.setMessage(" Operation Effectu?? avec succ??s ");
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
            societeDTOResponseOutput.setMessage(" Operation Effectu?? avec succ??s ");
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
        if (societeRepository.existsByNom(societeDTO.getNom())) return " Le nom de la soci??te saisie est d??j?? utilis?? !";
        if (societeRepository.existsByCode(societeDTO.getCode())) return "Le code de la soci??t?? saisie est d??j?? utilis?? !";
        return null;
    }


    

    @Override
    public ResponseOutput <SocieteDTO> persist (SocieteDTO societeDTO){
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	societeDTO.setCreatedBy(authentication.getName());
    	societeDTO.setCreatedDate(new Date());
        societeDTOResponseOutput.setTypeOperation("POST");
        String message = ctrlSociete(societeDTO);
        System.out.println("societeDTO"+societeDTO);
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
        societeDTOResponseOutput.setMessage("Soci??t?? Ins??r??");
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<SocieteDTO> update(Long id, SocieteDTO societeDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	societeDTO.setLastModifiedBy(authentication.getName());
    	societeDTO.setLastModifedDate(new Date());
        ResponseOutput <SocieteDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("PATCH");
        if (societeRepository.existsById(id))
        {
            SocieteDTO societeWantedDTO = societeConverter.toVo(societeRepository.getOne(id));
            societeDTO.setCreatedBy(societeWantedDTO.getCreatedBy());
            societeDTO.setCreatedDate(societeWantedDTO.getCreatedDate());
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
                societeDTOResponseOutput.setMessage(" Modification effectu??");
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
                societeDTOResponseOutput.setMessage("Enregistrement Non  Modifi?? ! ");
            } else {
                societeDTO.setId(id);
                societeRepository.save(societeConverter.toBo(societeDTO));
                societeDTOResponseOutput.setCode("200");
                societeDTOResponseOutput.setStatut("SUCCESS");
                societeDTOResponseOutput.setMessage("Enregistrement   Modifi?? ! ");
                societeDTOResponseOutput.setData(societeDTO);
            }
            return societeDTOResponseOutput;

        }
        societeDTOResponseOutput.setStatut("NOT FOUND");
        societeDTOResponseOutput.setCode("500");
        societeDTOResponseOutput.setMessage("La societe Selectionn?? ne figure pas dans notre base ! ");
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
			                societeDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les types entites ayant ce type  ");
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
		                  societeDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les entites ayant ce type  ");
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
//		                   societeDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les entites ayant ce type  ");
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
	                     societeDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les profils ayant ce type  ");
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
	                      societeDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les applications ayant ce type  ");
	                      return societeDTOResponseOutput;}
	         		 }
	         		 
	         	 }
	        societeRepository.deleteById(id);
	        societeDTOResponseOutput.setStatut("SUCCES");
	        societeDTOResponseOutput.setCode("200");
	        societeDTOResponseOutput.setMessage("La societe a ??t?? supprim?? ! ");
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
             societeDTOResponseOutput.setMessage("Operation Effectu?? !");
             societeDTOResponseOutput.setData(societeDTO);

         }
         else
         {
             societeDTOResponseOutput.setCode("500");
             societeDTOResponseOutput.setStatut("NOT FOUND");
             societeDTOResponseOutput.setMessage("Operation Effectu?? !");
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
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setData(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
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
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setData(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
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
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setData(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
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
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setCollection(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
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
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setCollection(societeDTO);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return societeDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> persist(ApplicationDTO applicationDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	applicationDTO.setCreatedBy(authentication.getName());
    	applicationDTO.setCreatedDate(new Date());
        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        applicationDTOResponseOutput.setTypeOperation("POST");
        applicationRepository.save(applicationConverter.toBo(applicationDTO));
        applicationDTOResponseOutput.setStatut("SUCCESS");
        applicationDTOResponseOutput.setCode("200");
        applicationDTOResponseOutput.setMessage("Application Ajout?? !");
        return applicationDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ApplicationDTO> update(ApplicationID applicationID, ApplicationDTO applicationDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	applicationDTO.setCreatedBy(authentication.getName());
    	applicationDTO.setCreatedDate(new Date());
        ResponseOutput <ApplicationDTO> applicationDTOResponseOutput = new ResponseOutput<>();
        ApplicationDTO wanted  = applicationConverter.toVo(applicationRepository.findById(applicationID).get());
        applicationDTO.setCreatedBy(wanted.getCreatedBy());
        applicationDTO.setCreatedDate(wanted.getCreatedDate());
        applicationDTOResponseOutput.setTypeOperation("PATCH | PUT");
        if (applicationRepository.existsById(applicationID))
        {
            applicationDTOResponseOutput.setCode("200");
            applicationDTOResponseOutput.setStatut("SUCCES");
            applicationDTOResponseOutput.setMessage("Element Modifier ! ");
            applicationDTO.setId(applicationID.getId());
            SocieteDTO wanted1  = societeConverter.toVo(societeRepository.findById(applicationID.getSociete()).get());
            
            applicationDTO.setSocieteDTO(wanted1);
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
        // 
         if(applicationRepository.existsById(applicationID))
         {
        	 for (MenuDTO menuDTO : menuConverter.toVoList(menuRepository.findAll()))
        	 {
        		 if (menuDTO.getApplicationDTO().getId() == applicationID.getId())
        		 {
        			 applicationDTOResponseOutput.setCode("300");
        			 applicationDTOResponseOutput.setStatut("WARNING");
        			 applicationDTOResponseOutput.setMessage("Le type de societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les societes ayant ce type  ");
                     return applicationDTOResponseOutput;
        		 }
        		 
        	 }
        	 applicationRepository.deleteById(applicationID);
        	 applicationDTOResponseOutput.setCode("500");
        	 applicationDTOResponseOutput.setStatut("SUCCESS");
        	 applicationDTOResponseOutput.setMessage("Le type de societe a ete bien supprime");
             return applicationDTOResponseOutput;
        	 
        	 
         }
         else {
        	 // element not found 
        	 applicationDTOResponseOutput.setCode("500");
        	 applicationDTOResponseOutput.setStatut("NOT FOUND");
        	 applicationDTOResponseOutput.setMessage("Le type de societe chercher est introuvable");
             return applicationDTOResponseOutput;
         }
        
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
	public ResponseOutput<ApplicationDTO> getApplicationById(Long id) {
	    	ResponseOutput <ApplicationDTO> ApplicationDTOResponseOutput = new ResponseOutput<>();
	    	ApplicationDTOResponseOutput.setTypeOperation("GET");
	        ApplicationDTOResponseOutput.setData(applicationConverter.toVo(applicationRepository.findById(id)));
	        return ApplicationDTOResponseOutput;
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
    public ResponseOutput<ApplicationDTO> getApplicationBySociete(Long id) {
        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
        ResponseOutput <ApplicationDTO> ApplicationDTOResponseOutput = new ResponseOutput<>();
        ApplicationDTOResponseOutput.setTypeOperation("GET");

        ApplicationDTOResponseOutput.setCollection(applicationConverter.toVoList(applicationRepository.findBySociete(societeConverter.toBo(societeDTO))));
        return ApplicationDTOResponseOutput;

    }
    
    
    @Override
    public ResponseOutput<ProfilDTO> persist(ProfilDTO profilDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	profilDTO.setCreatedBy(authentication.getName());
    	profilDTO.setCreatedDate(new Date());
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("POST");

        if (profilRepository.existsByNom(profilDTO.getNom()))
        {
            List <ProfilDTO> wanted = profilConverter.toVoList(profilRepository.findByNom(profilDTO.getNom()));
            wanted.forEach( data ->{
                if (data.getSocieteDTO().getId() == data.getSocieteDTO().getId())
                {
                    profilDTOResponseOutput.setMessage(" Ce profil est d??ja ins??r?? dans notre base ");
                }
            });
            profilDTOResponseOutput.setStatut("ERROR");
            profilDTOResponseOutput.setCode("404");
            profilDTOResponseOutput.setMessage(profilDTOResponseOutput.getMessage() == null ? "Le nom du profil  est d??ja utilis?? dans notre base" : profilDTOResponseOutput.getMessage());
        }
        else
        {
            profilDTOResponseOutput.setStatut("SUCCES");
            profilDTOResponseOutput.setCode("200");
            profilRepository.save(profilConverter.toBo(profilDTO));
            profilDTOResponseOutput.setMessage(" Profil Ajout?? !");
        }
        return profilDTOResponseOutput;
    }

    @Override
    public ResponseOutput<ProfilDTO> update(ProfilID profilID, ProfilDTO profilDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	profilDTO.setLastModifiedBy(authentication.getName());
    	profilDTO.setLastModifedDate(new Date());
        ResponseOutput <ProfilDTO> profilDTOResponseOutput = new ResponseOutput<>();
        profilDTOResponseOutput.setTypeOperation("PATCH");
        if(profilRepository.existsById(profilID))
        {
            ProfilDTO wanted  = profilConverter.toVo(profilRepository.findById(profilID).get());
            profilDTO.setCreatedBy(wanted.getCreatedBy());
            profilDTO.setCreatedDate(wanted.getCreatedDate());
            System.out.println("ID : "+ wanted.getNom());
            if (wanted.getNom().equals(profilDTO.getNom()) && profilID.getSociete() == wanted.getSocieteDTO().getId())
            {
                profilDTOResponseOutput.setCode("300");
                profilDTOResponseOutput.setStatut("WARNING");
                profilDTOResponseOutput.setMessage("Vous n'avez rien modifier au niveau du profil ! ");
                profilDTOResponseOutput.setData(wanted);
                return profilDTOResponseOutput;
            }
            
            
            
           	 for ( ProfilDTO profilDTO1 : profilConverter.toVoList(profilRepository.findAll()))
           	 {
           		 if (profilDTO1.getSocieteDTO().getId() == profilID.getSociete() && profilDTO.getNom().equals(profilDTO1.getNom()))
           		 {
           			 System.out.println("Ce profil est deja enregestre");
                     profilDTOResponseOutput.setCode("300");
                     profilDTOResponseOutput.setStatut("WARNING");
                     profilDTOResponseOutput.setMessage("Ce profil est deja enregestrer  ");
                        return  profilDTOResponseOutput;
           		 }
           		 
           	 }
           	
           	 
           	 
                        profilDTO.setId(profilID.getId());
            profilDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(profilID.getSociete()).get()));
            profilRepository.save(profilConverter.toBo(profilDTO));
            profilDTOResponseOutput.setCode("200");
            profilDTOResponseOutput.setStatut("SUCCESS");
            profilDTOResponseOutput.setMessage("Profil Modifi??");
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
        // 
         if(profilRepository.existsById(profilID))
         {
        	 for (UtilisateurDTO utilisarueDTO : utilisateurConverter.toVoList(utilisateurRepository.findAll()))
        	 {
        		 if (utilisarueDTO.getProfilDTO().getId() == profilID.getId())
        		 {
        			 profilDTOResponseOutput.setCode("300");
        			 profilDTOResponseOutput.setStatut("WARNING");
        			 profilDTOResponseOutput.setMessage("Le profil n'a pas ??t?? supprim?? ,Veuillez suprrimer les Utilisateurs ayant ce type  ");
                     return profilDTOResponseOutput;
        		 }
        		 
        	 }
        	 profilDTOResponseOutput.setCode("200");
             profilDTOResponseOutput.setStatut("SUCCES");
             profilDTOResponseOutput.setMessage(" Le profil a ??t?? supprim?? ! ");
             profilRepository.deleteById(profilID);
             return profilDTOResponseOutput;
        	 
        	 
         }
         else {
        	 // element not found 
        	 profilDTOResponseOutput.setCode("500");
        	 profilDTOResponseOutput.setStatut("NOT FOUND");
             profilDTOResponseOutput.setMessage("Le profil que vous voulez supprim?? ne figure pas dans notre liste des profils ");
             return profilDTOResponseOutput;
         }
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
            profilDTOResponseOutput.setMessage(" Profil trouv?? !");
            profilDTOResponseOutput.setCollection(profilDTOS);

        }
        return profilDTOResponseOutput;
    }
    
    @Override
    public ResponseOutput<ProfilDTO> getProfilBySociete(Long id) {
        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
        ResponseOutput <ProfilDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");

        typeEntiteDTOResponseOutput.setCollection(profilConverter.toVoList(profilRepository.findBySociete(societeConverter.toBo(societeDTO))));
        return typeEntiteDTOResponseOutput;

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
            profilDTOResponseOutput.setMessage("Profil Trouv?? ! ");
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
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	localiteDTO.setCreatedBy(authentication.getName());
    	localiteDTO.setCreatedDate(new Date());
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("POST");
        if(localiteRepository.existsByNom(localiteDTO.getNom()))
        {
            localiteDTOResponseOutput.setCode("404");
            localiteDTOResponseOutput.setStatut("ERROR");
            localiteDTOResponseOutput.setMessage(" Cet Localit?? est d??j?? ins??r?? dans notre base ");
        }
        else
        {
            localiteRepository.save(localiteConverter.toBo(localiteDTO));
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage(" Localit?? ajout?? !");

        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<LocaliteDTO> update(Long id, LocaliteDTO localiteDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	localiteDTO.setLastModifiedBy(authentication.getName());
    	localiteDTO.setLastModifedDate(new Date());
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("PATCH");
        LocaliteDTO wanted = localiteConverter.toVo(localiteRepository.findById(id).get());
        localiteDTO.setCreatedBy(wanted.getCreatedBy());
        localiteDTO.setCreatedDate(wanted.getCreatedDate());
        if(localiteRepository.existsById(id))
        {
            if(wanted.getNom().equals(localiteDTO.getNom())
                    && wanted.getCode().equals(localiteDTO.getCode())
                    && wanted.getNomAbrege().equals(localiteDTO.getNomAbrege()))
            {
                localiteDTOResponseOutput.setCode("300");
                localiteDTOResponseOutput.setStatut("WARNING");
                localiteDTOResponseOutput.setMessage(" Vous n'avez rien changer au niveau de la localit?? ");
                return localiteDTOResponseOutput;
            }
            if (localiteRepository.existsByNom(localiteDTO.getNom()))
            {
                if(wanted.getNom().equals(localiteDTO.getNom())){
                	localiteDTO.setId(id);
                    localiteRepository.save(localiteConverter.toBo(localiteDTO));
                    localiteDTOResponseOutput.setCode("200");
                    localiteDTOResponseOutput.setStatut("SUCCES");
                    localiteDTOResponseOutput.setMessage("Localit?? ajout?? ! ");
                    return localiteDTOResponseOutput;

               
            }else
            {
                
                localiteDTOResponseOutput.setCode("404");
                localiteDTOResponseOutput.setStatut("ERROR");
                localiteDTOResponseOutput.setMessage("Cet Localit?? est d??j?? ins??rer dans notre base ");
                return localiteDTOResponseOutput;

            }

            }
            
            localiteDTO.setId(id);
            localiteRepository.save(localiteConverter.toBo(localiteDTO));
            localiteDTOResponseOutput.setCode("200");
            localiteDTOResponseOutput.setStatut("SUCCES");
            localiteDTOResponseOutput.setMessage("Localit?? ajout?? ! ");
            return localiteDTOResponseOutput;


        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND");
            localiteDTOResponseOutput.setMessage(" Cet localit?? ne figure pas dans notre base de donn??e");
        }
        return null;
    }

    @Override
    public ResponseOutput<LocaliteDTO> deleteLocalite(Long id) {
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("DELETE");

        if(localiteRepository.existsById(id))
         {
        	Long a;
        	 for (EntiteDTO entiteDTO : entiteConverter.toVoList(entiteRepository.findAll()))
        	 {
        		 a = entiteDTO.getLocaliteDTO().getId();
        		 System.out.println(" -- ");
        		 if (a == id)
        		 {
        			 localiteDTOResponseOutput.setCode("300");
        			 localiteDTOResponseOutput.setStatut("WARNING");
                     localiteDTOResponseOutput.setMessage("L entite n'a pas ??t?? supprim?? ,Veuillez suprrimer les societes ayant ce type  ");
                     return localiteDTOResponseOutput;
        		 }
        		 
        	 }
        	 localiteRepository.deleteById(id);
        	 localiteDTOResponseOutput.setCode("500");
        	 localiteDTOResponseOutput.setStatut("SUCCESS");
        	 localiteDTOResponseOutput.setMessage("Le type de societe a ete bien supprime");
             return localiteDTOResponseOutput;
          }
        else {
       	 // element not found 
        	localiteDTOResponseOutput.setCode("500");
	       	localiteDTOResponseOutput.setStatut("NOT FOUND");
	       	localiteDTOResponseOutput.setMessage("Localite chercher est introuvable");
            return localiteDTOResponseOutput;
        }
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
            localiteDTOResponseOutput.setMessage(" Localit?? trouv?? ! ");
            localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localit?? trouv?? comportant le nom [ " + nom+ " ] ");
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
            localiteDTOResponseOutput.setMessage(" Localit?? trouv?? ! ");
            localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localit?? trouv?? comportant le code [ " + code+ " ] ");
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
            localiteDTOResponseOutput.setMessage(" Localit?? trouv?? ! ");
            localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localit?? trouv?? comportant l'abreviation  [ " + nomAbrege+ " ] ");
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
            localiteDTOResponseOutput.setMessage(" Localit?? trouv?? ! ");
            localiteDTOResponseOutput.setCollection(localiteDTO);
        }
        else
        {
            localiteDTOResponseOutput.setCode("500");
            localiteDTOResponseOutput.setStatut("NO DATA FOUND ");
            localiteDTOResponseOutput.setMessage(" Aucune Localit?? trouv?? ");
        }
        return localiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput <LocaliteDTO> searchLocaliteByID (Long id){
    	
        ResponseOutput <LocaliteDTO> localiteDTOResponseOutput = new ResponseOutput<>();
        localiteDTOResponseOutput.setTypeOperation("GET");
        if(localiteRepository.findById(id) != null) {
        LocaliteDTO localiteDTO = localiteConverter.toVo(localiteRepository.findById(id).get());
        if(localiteDTO != null)
        {
        	localiteDTOResponseOutput.setStatut("SUCCESS");
        	localiteDTOResponseOutput.setCode("200");
        	localiteDTOResponseOutput.setMessage(" Operation Effectu?? avec succ??s ");
        	localiteDTOResponseOutput.setData(localiteDTO);
        }
        else
        {
        	localiteDTOResponseOutput.setStatut("NOT FOUND");
        	localiteDTOResponseOutput.setCode("500");
        	localiteDTOResponseOutput.setMessage(" Aucun enregistrement comportant ce nom :  "+id);
        }
        }
        return localiteDTOResponseOutput;
    }

    
    @Override
    public ResponseOutput<TypeEntiteDTO> persist(TypeEntiteDTO typeEntiteDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	typeEntiteDTO.setCreatedBy(authentication.getName());
    	typeEntiteDTO.setCreatedDate(new Date());
        ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("POST");
        if (typeEntiteRepository.existsByNom(typeEntiteDTO.getNom().toUpperCase()) ||
                typeEntiteRepository.existsByCode(typeEntiteDTO.getCode().toUpperCase()) &&
            typeEntiteRepository.existsBySociete(societeConverter.toBo(typeEntiteDTO.getSocieteDTO()))
            )
        {
            typeEntiteDTOResponseOutput.setCode("404");
            typeEntiteDTOResponseOutput.setStatut("ERROR");
            typeEntiteDTOResponseOutput.setMessage(" Ce type d'entite est d??j?? d??finit pour cet societe");
            return typeEntiteDTOResponseOutput;
        }
        typeEntiteRepository.save(typeEntiteConverter.toBo(typeEntiteDTO));
        typeEntiteDTOResponseOutput.setCode("200");
        typeEntiteDTOResponseOutput.setStatut("SUCCES");
        typeEntiteDTOResponseOutput.setMessage("Le type d'entit?? " + typeEntiteDTO.getNom() + " a ??t?? bien ins??r??");
        return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> update(TypeEntiteID typeEntiteID, TypeEntiteDTO typeEntiteDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	typeEntiteDTO.setLastModifiedBy(authentication.getName());
    	typeEntiteDTO.setLastModifedDate(new Date());
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("PATCH");
        // SAME OBJECT
        if(typeEntiteRepository.existsById(typeEntiteID))
        {
            // same object
            TypeEntiteDTO wanted = typeEntiteConverter.toVo(typeEntiteRepository.findById(typeEntiteID).get());
            typeEntiteDTO.setCreatedBy(wanted.getCreatedBy());
            typeEntiteDTO.setCreatedDate(wanted.getCreatedDate());
            if(wanted.getNom().equals(typeEntiteDTO.getNom()) &&
               wanted.getCode().equals(typeEntiteDTO.getCode()) &&
               wanted.getSocieteDTO().getId() == typeEntiteDTO.getSocieteDTO().getId()
               
               )
            {
            	if(wanted.getTypeEntiteMereDTO()!=null) {
                	if(wanted.getTypeEntiteMereDTO().getId() == typeEntiteDTO.getTypeEntiteMereDTO().getId()) {
                		 typeEntiteDTOResponseOutput.setCode("300");
                         typeEntiteDTOResponseOutput.setStatut("WARNING");
                         typeEntiteDTOResponseOutput.setMessage("Vous n'avez effectuez aucun changement sur l'objet");
                         typeEntiteDTOResponseOutput.setData(typeEntiteDTO);
                         return typeEntiteDTOResponseOutput;
                		
                	}

            	}
               
            }
            for (TypeEntiteDTO typeEntiteDTO1  : typeEntiteConverter.toVoList(typeEntiteRepository.findAll()))
            {
                if (typeEntiteDTO1.getSocieteDTO().getId() == typeEntiteDTO.getSocieteDTO().getId() &&
                    typeEntiteDTO1.getId() != typeEntiteID.getId())
                {
                    if(typeEntiteDTO1.getNom().equals(typeEntiteDTO.getNom()))
                    {
                    	if(!wanted.getNom().equals(typeEntiteDTO.getNom())) {
                    		typeEntiteDTOResponseOutput.setCode("404");
                            typeEntiteDTOResponseOutput.setStatut("ERROR");
                            typeEntiteDTOResponseOutput.setMessage(" Ce nom est d??ja utiliser pour cette societe ");
                            return typeEntiteDTOResponseOutput;
                    	}
                     
                    }
                    if(typeEntiteDTO1.getCode().equals(typeEntiteDTO.getCode()))
                    {
                        if(!wanted.getCode().equals(typeEntiteDTO.getCode())) {
                        	typeEntiteDTOResponseOutput.setCode("404");
                            typeEntiteDTOResponseOutput.setStatut("ERROR");
                            typeEntiteDTOResponseOutput.setMessage(" Ce code est d??ja utiliser pour cette societe ");
                            return typeEntiteDTOResponseOutput;
                        }

                        
                    }
                }
            }

            typeEntiteDTO.setId(typeEntiteID.getId());
            typeEntiteDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(typeEntiteID.getSociete()).get()));
            typeEntiteRepository.save(typeEntiteConverter.toBo(typeEntiteDTO));
            typeEntiteDTOResponseOutput.setCode("200");
            typeEntiteDTOResponseOutput.setStatut("SUCCES");
            typeEntiteDTOResponseOutput.setMessage("Type Entit?? modifi?? ");

        }
        else
        {
            typeEntiteDTOResponseOutput.setCode("500");
            typeEntiteDTOResponseOutput.setStatut("NOT FOUND");
            typeEntiteDTOResponseOutput.setMessage(" Le type d'entit?? rechercher est introuvable");
        }
       return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> deleteTypeEntite(TypeEntiteID typeEntiteID) {
       
        
        ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("DELETE");
        if(typeEntiteRepository.existsById(typeEntiteID))
        {
	        for (TypeEntiteDTO typeEntiteDTO : typeEntiteConverter.toVoList(typeEntiteRepository.findAll()))
	   	 		{
			   		 if (typeEntiteDTO.getTypeEntiteMereDTO() != null)
			   		 {
				   		 if (typeEntiteDTO.getTypeEntiteMereDTO().getId() != null)
				   		 {
			   			 
				   		 if (typeEntiteDTO.getTypeEntiteMereDTO().getId() == typeEntiteID.getId()) {

				   			typeEntiteDTOResponseOutput.setCode("300");
				   			typeEntiteDTOResponseOutput.setStatut("WARNING");
				   			typeEntiteDTOResponseOutput.setMessage("Le Type Entite n'a pas ??t?? supprim?? ,Veuillez suprrimer les types entites fils ayant ce type  ");
			                return typeEntiteDTOResponseOutput;
				   		 }
			   		 }
			   		 }
	   		 
	   	 		}
        
        
	        for (EntiteDTO entiteDTO : entiteConverter.toVoList(entiteRepository.findAll()))
	     	 	{
		     		 if (entiteDTO.getTypeEntiteDTO().getId() != null)
		     		 {
		     			if (entiteDTO.getTypeEntiteDTO().getId() == typeEntiteID.getId())
			     		 {
		     				typeEntiteDTOResponseOutput.setCode("300");
		     				typeEntiteDTOResponseOutput.setStatut("WARNING");
		     				typeEntiteDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les entites ayant ce type  ");
		                  return typeEntiteDTOResponseOutput;}
		     		 }
		     		 
		     	 } 
      
	        typeEntiteRepository.deleteById(typeEntiteID);
	        typeEntiteDTOResponseOutput.setStatut("SUCCES");
	        typeEntiteDTOResponseOutput.setCode("200");
	        typeEntiteDTOResponseOutput.setMessage("Le Type Entite a ??t?? supprim?? ! ");
	        return typeEntiteDTOResponseOutput;
	         }
        typeEntiteDTOResponseOutput.setCode("500");
        typeEntiteDTOResponseOutput.setStatut("NOT FOUND");
        typeEntiteDTOResponseOutput.setMessage("Lae de societe chercher est introuvable");
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
	public ResponseOutput<TypeEntiteDTO> getAllTypeEntitiesBySociete(SocieteDTO societeDTO) {
		ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
        typeEntiteDTOResponseOutput.setTypeOperation("GET");

        typeEntiteDTOResponseOutput.setCollection(typeEntiteConverter.toVoList(typeEntiteRepository.findBySociete(societeConverter.toBo(societeDTO))));
        return typeEntiteDTOResponseOutput;
	}
	
	 @Override
	public ResponseOutput<TypeEntiteDTO> getTypeEntitiesBySociete(Long id) {
	        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
	        ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
	        typeEntiteDTOResponseOutput.setTypeOperation("GET");

	        typeEntiteDTOResponseOutput.setCollection(typeEntiteConverter.toVoList(typeEntiteRepository.findBySociete(societeConverter.toBo(societeDTO))));
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
	public ResponseOutput<TypeEntiteDTO> getTypeEntitiesById(Long id) {
	    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
	        typeEntiteDTOResponseOutput.setTypeOperation("GET");
	        typeEntiteDTOResponseOutput.setData(typeEntiteConverter.toVo(typeEntiteRepository.findById(id)));
	        return typeEntiteDTOResponseOutput;
	    }

    @Override
    public ResponseOutput<TypeEntiteDTO> searchTypeEntiteMere() {
        return null;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> getAllTypeEntitiesSortBy(String fieldName) {
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
    	typeEntiteDTOResponseOutput.setTypeOperation("GET");
        List<TypeEntiteDTO> typeEntiteDTO = typeEntiteConverter.toVoList(typeEntiteRepository.findAll(Sort.by(fieldName)));
        if( typeEntiteDTO.size() !=0 )
        {
        	typeEntiteDTOResponseOutput.setCode("200");
        	typeEntiteDTOResponseOutput.setStatut("SUCCES");
        	typeEntiteDTOResponseOutput.setMessage("Operation Effectu?? !");
        	typeEntiteDTOResponseOutput.setCollection(typeEntiteDTO);

        }
        else
        {
        	typeEntiteDTOResponseOutput.setCode("500");
        	typeEntiteDTOResponseOutput.setStatut("NOT FOUND");
        	typeEntiteDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return typeEntiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<TypeEntiteDTO> getAllTypeEntities() {
    	ResponseOutput <TypeEntiteDTO> typeEntiteDTOResponseOutput = new ResponseOutput<>();
    	typeEntiteDTOResponseOutput.setTypeOperation("GET");
        List<TypeEntiteDTO> typeEntiteDTO = typeEntiteConverter.toVoList(typeEntiteRepository.findAll());
        if( typeEntiteDTO.size() !=0 )
        {
        	typeEntiteDTOResponseOutput.setCode("200");
        	typeEntiteDTOResponseOutput.setStatut("SUCCES");
        	typeEntiteDTOResponseOutput.setMessage("Operation Effectu?? !");
        	typeEntiteDTOResponseOutput.setCollection(typeEntiteDTO);

        }
        else
        {
        	typeEntiteDTOResponseOutput.setCode("500");
        	typeEntiteDTOResponseOutput.setStatut("NOT FOUND");
        	typeEntiteDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return typeEntiteDTOResponseOutput;
    }

    
    @Override
   	public String ctrlEntite(EntiteDTO entiteDTO) {
       	if(entiteRepository.existsByNom(entiteDTO.getNom())&&entiteRepository.existsBySociete(entiteDTO.getSocieteDTO().getId())) return "L'entite est d??j?? saisie dans la base de donn??es";
           return null;
   	}
       
       @Override
   public ResponseOutput<EntiteDTO> persist(EntiteDTO entiteDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       	entiteDTO.setCreatedBy(authentication.getName());
       	entiteDTO.setCreatedDate(new Date());
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
           entiteDTOResponseOutput.setMessage("Enregistrement Effectu?? ! ");
           return entiteDTOResponseOutput;
       }

    @Override
    public ResponseOutput<EntiteDTO> update(EntiteID entiteID, EntiteDTO entiteDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	entiteDTO.setLastModifiedBy(authentication.getName());
    	entiteDTO.setLastModifedDate(new Date());
    	ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
        entiteDTOResponseOutput.setTypeOperation("PATCH");
        // SAME OBJECT
        if(entiteRepository.existsById(entiteID))
        {
            // same object
            EntiteDTO wanted = entiteConverter.toVo(entiteRepository.findById(entiteID).get());
            entiteDTO.setCreatedBy(wanted.getCreatedBy());
            entiteDTO.setCreatedDate(wanted.getCreatedDate());
            if(wanted.getNom().equals(entiteDTO.getNom()) &&
                    wanted.getCode().equals(entiteDTO.getCode()) &&
                    wanted.getLocaliteDTO().getId()==entiteDTO.getLocaliteDTO().getId() &&
                    wanted.getTypeEntiteDTO().getId()==entiteDTO.getTypeEntiteDTO().getId() &&
                    wanted.getEntiteMereDTO().getId()==entiteDTO.getEntiteMereDTO().getId() &&
               wanted.getSocieteDTO().getId() == entiteDTO.getSocieteDTO().getId())
            {
            	entiteDTOResponseOutput.setCode("300");
            	entiteDTOResponseOutput.setStatut("WARNING");
            	entiteDTOResponseOutput.setMessage("Vous n'avez effectuez aucun changement sur l'objet");
            	entiteDTOResponseOutput.setData(entiteDTO);
                return entiteDTOResponseOutput;
            }
            for (EntiteDTO entiteDTO1  : entiteConverter.toVoList(entiteRepository.findAll()))
            {
                if (entiteDTO1.getSocieteDTO().getId() == entiteDTO.getSocieteDTO().getId()
                		&&(entiteDTO1.getId()-entiteID.getId()!=0)) 
                	
                {
                    if(entiteDTO1.getNom().equals(entiteDTO.getNom()))
                    {
                    	
                    	entiteDTOResponseOutput.setCode("404");
                    	entiteDTOResponseOutput.setStatut("ERROR");
                    	entiteDTOResponseOutput.setMessage(" Ce nom est d??ja utiliser pour cette societe ");
                     return entiteDTOResponseOutput;
                    }
                    if(entiteDTO1.getCode().equals(entiteDTO.getCode()))
                    {
                    	entiteDTOResponseOutput.setCode("404");
                    	entiteDTOResponseOutput.setStatut("ERROR");
                    	entiteDTOResponseOutput.setMessage(" Ce code est d??ja utiliser pour cette societe ");
                        return entiteDTOResponseOutput;
                    }
                }
            }

            entiteDTO.setId(entiteID.getId());
            entiteDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(entiteID.getSociete()).get()));
            entiteRepository.save(entiteConverter.toBo(entiteDTO));
            entiteDTOResponseOutput.setCode("200");
            entiteDTOResponseOutput.setStatut("SUCCES");
            entiteDTOResponseOutput.setMessage(" Entit?? modifi?? ");

        }
        else
        {
        	entiteDTOResponseOutput.setCode("500");
            entiteDTOResponseOutput.setStatut("NOT FOUND");
            entiteDTOResponseOutput.setMessage(" Le type d'entit?? rechercher est introuvable");
        }
       return entiteDTOResponseOutput;
    }

    @Override
    public ResponseOutput<EntiteDTO> deleteEntite(EntiteID entiteID) {
    	 
        ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
        entiteDTOResponseOutput.setTypeOperation("DELETE");
        if(entiteRepository.existsById(entiteID))
        {
	        for (EntiteDTO entiteDTO : entiteConverter.toVoList(entiteRepository.findAll()))
	   	 		{
			   		 if (entiteDTO.getEntiteMereDTO() != null)
			   		 {
				   		 if (entiteDTO.getEntiteMereDTO().getId() != null)
				   		 {
			   			 
				   		 if (entiteDTO.getEntiteMereDTO().getId() == entiteID.getId()) {

				   			entiteDTOResponseOutput.setCode("300");
				   			entiteDTOResponseOutput.setStatut("WARNING");
				   			entiteDTOResponseOutput.setMessage("Le Type Entite n'a pas ??t?? supprim?? ,Veuillez supprimer les entits fils   ");
			                return entiteDTOResponseOutput;
				   		 }
			   		 }
			   		 }
	   		 
	   	 		}
        
        
	        for (UtilisateurDTO utilisateurDTO : utilisateurConverter.toVoList(utilisateurRepository.findAll()))
	     	 	{
		     		 if (utilisateurDTO.getEntiteDTO().getId() != null)
		     		 {
		     			if (utilisateurDTO.getEntiteDTO().getId() == entiteID.getId())
			     		 {
		     				entiteDTOResponseOutput.setCode("300");
		     				entiteDTOResponseOutput.setStatut("WARNING");
		     				entiteDTOResponseOutput.setMessage("La societe n'a pas ??t?? supprim?? ,Veuillez suprrimer les entites ayant ce type  ");
		                  return entiteDTOResponseOutput;}
		     		 }
		     		 
		     	 } 
      
	        entiteRepository.deleteById(entiteID);
	        entiteDTOResponseOutput.setStatut("SUCCES");
	        entiteDTOResponseOutput.setCode("200");
	        entiteDTOResponseOutput.setMessage("Le Type Entite a ??t?? supprim?? ! ");
	        return entiteDTOResponseOutput;
	         }
        entiteDTOResponseOutput.setCode("500");
        entiteDTOResponseOutput.setStatut("NOT FOUND");
        entiteDTOResponseOutput.setMessage("Lae de societe chercher est introuvable");
        return entiteDTOResponseOutput;
    }

    @Override
   	public ResponseOutput<EntiteDTO> getEntitiesBySociete(Long id) {
   	        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
   	        ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
   	       entiteDTOResponseOutput.setTypeOperation("GET");

   	        entiteDTOResponseOutput.setCollection(entiteConverter.toVoList(entiteRepository.findBySociete(societeConverter.toBo(societeDTO))));
   	        return entiteDTOResponseOutput;

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
       	 entiteDTOResponseOutput.setMessage("Operation Effectu?? !");
       	 entiteDTOResponseOutput.setCollection(entiteDTO);

        }
        else
        {
       	 entiteDTOResponseOutput.setCode("500");
       	 entiteDTOResponseOutput.setStatut("NOT FOUND");
       	 entiteDTOResponseOutput.setMessage("Operation Effectu?? !");
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
    public ResponseOutput<EntiteDTO> getAllEntities() {
    	ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
    	entiteDTOResponseOutput.setTypeOperation("GET");
        List<EntiteDTO> entiteDTO = entiteConverter.toVoList(entiteRepository.findAll());
        if( entiteDTO.size() !=0 )
        {
        	entiteDTOResponseOutput.setCode("200");
        	entiteDTOResponseOutput.setStatut("SUCCES");
        	entiteDTOResponseOutput.setMessage("Operation Effectu?? !");
        	entiteDTOResponseOutput.setCollection(entiteDTO);

        }
        else
        {
        	entiteDTOResponseOutput.setCode("500");
        	entiteDTOResponseOutput.setStatut("NOT FOUND");
        	entiteDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return entiteDTOResponseOutput;
      }
    @Override
	public ResponseOutput<EntiteDTO> getEntitiesById(Long id) {
	    	ResponseOutput <EntiteDTO> entiteDTOResponseOutput = new ResponseOutput<>();
	        entiteDTOResponseOutput.setTypeOperation("GET");
	        entiteDTOResponseOutput.setData(entiteConverter.toVo(entiteRepository.findById(id)));
	        return entiteDTOResponseOutput;
	    }
    @Override
    public ResponseOutput<MenuDTO> persist(MenuDTO menuDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	menuDTO.setCreatedBy(authentication.getName());
    	menuDTO.setCreatedDate(new Date());
       	ResponseOutput <MenuDTO> menuDTOResponseOutput = new ResponseOutput<>();
       	menuDTOResponseOutput.setTypeOperation("POST");
           
           for (MenuDTO menuDTO1  : menuConverter.toVoList(menuRepository.findAll()))
           {
               if (menuDTO1.getMenuPereDTO()!=null  && menuDTO.getMenuPereDTO()!=null && menuDTO1.getApplicationDTO().getId()-menuDTO.getApplicationDTO().getId()==0) 
               	
               {
                   if (menuDTO1.getMenuPereDTO().getId()-menuDTO.getMenuPereDTO().getId()==0) {
                	   
                	   if(menuDTO1.getNom().equals(menuDTO.getNom()))
                       {
                       	
                    	   menuDTOResponseOutput.setCode("404");
                    	   menuDTOResponseOutput.setStatut("ERROR");
                    	   menuDTOResponseOutput.setMessage(" Ce nom est d??ja utiliser dans  ce menu ");
                        return menuDTOResponseOutput;
                       }
                       if(menuDTO1.getOrdre()-menuDTO.getOrdre()==0)
                       {
                    	   menuDTOResponseOutput.setCode("404");
                    	   menuDTOResponseOutput.setStatut("ERROR");
                    	   menuDTOResponseOutput.setMessage(" on peut pas ajouter ce menu ");
                           return menuDTOResponseOutput;
                       }
                   } 
                   
                  
               }
               	if(menuDTO1.getMenuPereDTO()==null  && menuDTO.getMenuPereDTO()==null && menuDTO1.getApplicationDTO().getId()-menuDTO.getApplicationDTO().getId()==0){
               		if(menuDTO1.getNom().equals(menuDTO.getNom()))
                    {
                    	
                 	   menuDTOResponseOutput.setCode("404");
                 	   menuDTOResponseOutput.setStatut("ERROR");
                 	   menuDTOResponseOutput.setMessage(" Ce nom est d??ja utiliser dans  ce menu ");
                     return menuDTOResponseOutput;
                    }
                    if(menuDTO1.getOrdre()-menuDTO.getOrdre()==0)
                    {
                 	   menuDTOResponseOutput.setCode("404");
                 	   menuDTOResponseOutput.setStatut("ERROR");
                 	   menuDTOResponseOutput.setMessage(" on peut pas ajouter ce menu ");
                        return menuDTOResponseOutput;
                    }
               }
           }


           menuRepository.save(menuConverter.toBo(menuDTO));
           menuDTOResponseOutput.setCode("200");
           menuDTOResponseOutput.setStatut("SUCCESS");
           menuDTOResponseOutput.setMessage("Enregistrement Effectu?? ! ");
           return menuDTOResponseOutput;
    }

    @Override
    public ResponseOutput<MenuDTO> update(MenuID menuID, MenuDTO menuDTO) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	menuDTO.setLastModifiedBy(authentication.getName());
    	menuDTO.setLastModifedDate(new Date());
    	ResponseOutput <MenuDTO> menuDTOResponseOutput = new ResponseOutput<>();
    	menuDTOResponseOutput.setTypeOperation("PATCH");
        // SAME OBJECT
        if(menuRepository.existsById(menuID))
        {
            // same object
            MenuDTO wanted = menuConverter.toVo(menuRepository.findById(menuID).get());
            menuDTO.setCreatedBy(wanted.getCreatedBy());
            menuDTO.setCreatedDate(wanted.getCreatedDate());
            
            for (MenuDTO menuDTO1  : menuConverter.toVoList(menuRepository.findAll()))
            {
                if (menuDTO1.getMenuPereDTO()!=null  && menuDTO.getMenuPereDTO()!=null && menuDTO1.getApplicationDTO().getId()-menuDTO.getApplicationDTO().getId()==0)  
                	
                {
                	if(menuDTO1.getMenuPereDTO().getId()-menuDTO.getMenuPereDTO().getId()==0)  {
                		if(menuDTO1.getNom().equals(menuDTO.getNom()))
                        {
                        	
                        	menuDTOResponseOutput.setCode("404");
                        	menuDTOResponseOutput.setStatut("ERROR");
                        	menuDTOResponseOutput.setMessage(" Ce nom est d??ja utiliser pour ce menu ");
                         return menuDTOResponseOutput;
                        }
                        if(menuDTO1.getOrdre()-menuDTO.getOrdre()==0)
                        {
                        	menuDTOResponseOutput.setCode("404");
                        	menuDTOResponseOutput.setStatut("ERROR");
                        	menuDTOResponseOutput.setMessage(" Cette ordre est d??ja utiliser pour ce menu ");
                            return menuDTOResponseOutput;
                        }
                	}
                    
                }
                	if (menuDTO1.getMenuPereDTO()==null  && menuDTO.getMenuPereDTO()==null && menuDTO1.getApplicationDTO().getId()-menuDTO.getApplicationDTO().getId()==0)  
                	
	                {
	                	if(menuDTO1.getMenuPereDTO().getId()-menuDTO.getMenuPereDTO().getId()==0)  {
	                		if(menuDTO1.getNom().equals(menuDTO.getNom()))
	                        {
	                        	
	                        	menuDTOResponseOutput.setCode("404");
	                        	menuDTOResponseOutput.setStatut("ERROR");
	                        	menuDTOResponseOutput.setMessage(" Ce nom est d??ja utiliser pour ce menu ");
	                         return menuDTOResponseOutput;
	                        }
	                        if(menuDTO1.getOrdre()-menuDTO.getOrdre()==0)
	                        {
	                        	menuDTOResponseOutput.setCode("404");
	                        	menuDTOResponseOutput.setStatut("ERROR");
	                        	menuDTOResponseOutput.setMessage(" Cette ordre est d??ja utiliser pour ce menu ");
	                            return menuDTOResponseOutput;
	                        }
	                	}
	                    
	                }
                
            }

            menuDTO.setId(menuID.getId());
            menuDTO.setApplicationDTO(applicationConverter.toVo(applicationRepository.findById(menuID.getApplication()).get()));
            menuRepository.save(menuConverter.toBo(menuDTO));
            menuDTOResponseOutput.setCode("200");
            menuDTOResponseOutput.setStatut("SUCCES");
            menuDTOResponseOutput.setMessage(" Entit?? modifi?? ");

        }
        else
        {
        	menuDTOResponseOutput.setCode("500");
        	menuDTOResponseOutput.setStatut("NOT FOUND");
        	menuDTOResponseOutput.setMessage(" Le type d'entit?? rechercher est introuvable");
        }
       return menuDTOResponseOutput;
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
    public ResponseOutput<MenuDTO> getAllMenus() {
    	ResponseOutput <MenuDTO> menuDTOResponseOutput = new ResponseOutput<>();
    	menuDTOResponseOutput.setTypeOperation("GET");
        List<MenuDTO> menuDTO = menuConverter.toVoList(menuRepository.findAll());
        if( menuDTO.size() !=0 )
        {
        	menuDTOResponseOutput.setCode("200");
        	menuDTOResponseOutput.setStatut("SUCCES");
        	menuDTOResponseOutput.setMessage("Operation Effectu?? !");
        	menuDTOResponseOutput.setCollection(menuDTO);

        }
        else
        {
        	menuDTOResponseOutput.setCode("500");
        	menuDTOResponseOutput.setStatut("NOT FOUND");
        	menuDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return menuDTOResponseOutput;
    }
    
    @Override
	public ResponseOutput <MenuDTO> getAllParSocieteMenus (Long id){
//    	SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
//        ResponseOutput <MenuDTO> MenuDTOResponseOutput = new ResponseOutput<>();
////        MenuDTOResponseOutput.setTypeOperation("GET");
////
////        MenuDTOResponseOutput.setCollection(menuConverter.toVoList(menuRepository.findBySociete(societeConverter.toBo(societeDTO))));
//        return MenuDTOResponseOutput;

        SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
        ResponseOutput <MenuDTO> menuDTOResponseOutput = new ResponseOutput<>();
        List  <MenuDTO> menu =new ArrayList();
        List  <MenuDTO> menu1 =new ArrayList();
        menuDTOResponseOutput.setTypeOperation("GET");
//        menuDTOResponseOutput.setCollection(menuConverter.toVoList(menuRepository.findByApplication(applicationRepository.findBySociete(societeConverter.toBo(societeDTO)))));
        List<ApplicationDTO> applicationDTO =applicationConverter.toVoList(applicationRepository.findBySociete(societeConverter.toBo(societeDTO)));
        for (ApplicationDTO applicationDTO1  : applicationDTO) {
        menu=menuConverter.toVoList(menuRepository.findByApplication(applicationConverter.toBo(applicationDTO1)));
	        for(MenuDTO menuu : menu) {
	            menu1.add(menuu);
	
	        }
        }
        menuDTOResponseOutput.setCollection(menu1);

        return menuDTOResponseOutput;
    }
    
    
    @Override
   	public ResponseOutput<MenuDTO> getMenuById(Long id) {
   	    	ResponseOutput <MenuDTO> MenuDTOResponseOutput = new ResponseOutput<>();
   	    	MenuDTOResponseOutput.setTypeOperation("GET");
   	    	MenuDTOResponseOutput.setData(menuConverter.toVo(menuRepository.findById(id)));
   	        return MenuDTOResponseOutput;
   	    }

    @Override
   	public String ctrlMenu(MenuDTO menuDTO) {
//       	if(menuRepository.existsByNom(menuDTO.getNom())&&applicationRepository.existsBySociete(menuDTO.getApplicationDTO().getSocieteDTO().getId())) return "L'entite est d??j?? saisie dans la base de donn??es";
           return null;
   	}

	@Override
	public ResponseOutput<ProfilMenuDTO> persist(ProfilMenuDTO profilMenuDT) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 ;
        ResponseOutput <ProfilMenuDTO> profilMenuDTOResponseOutput = new ResponseOutput<>();
        profilMenuDTOResponseOutput.setTypeOperation("POST");
        ProfilMenu profilMenuDTO= profilMenuConverter.toBo(profilMenuDT);
        Date dateDebut=profilMenuDTO.getDateDebut();
        Date dateFin=profilMenuDTO.getDateFin();
        Long profilid=profilMenuDTO.getProfil().getId();
        Long societeeid=profilMenuDTO.getProfil().getSociete().getId();
        Long applicationid=profilMenuDTO.getMenu().getApplication().getId();
        Long societeid=profilMenuDTO.getProfil().getSociete().getId();
        Long menuid=profilMenuDTO.getMenu().getId();
 
        profilMenuRepository.insertUser( dateDebut,dateFin,  profilid ,  societeeid , applicationid , societeid , menuid );

        profilMenuDTOResponseOutput.setCode("200");
        profilMenuDTOResponseOutput.setStatut("SUCCESS");
        profilMenuDTOResponseOutput.setMessage("Enregistrement Effectu?? ! ");
        return profilMenuDTOResponseOutput;
	}

	@Override
	public ResponseOutput<ProfilMenu> persist(ProfilMenu profilMenuDTO) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
        ResponseOutput <ProfilMenu> profilMenuDTOResponseOutput = new ResponseOutput<>();
        profilMenuDTOResponseOutput.setTypeOperation("POST");
        
        profilMenuDTOResponseOutput.setCode("200");
        profilMenuDTOResponseOutput.setStatut("SUCCESS");
        profilMenuDTOResponseOutput.setMessage("Enregistrement Effectu?? ! ");
        return profilMenuDTOResponseOutput;
	}
	
	@Override
	public ResponseOutput<ProfilMenuDTO> update(ProfilMenuID profilMenuID, ProfilMenuDTO menuDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseOutput<ProfilMenuDTO> deleteMenu(ProfilMenuID profilMenuID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseOutput<ProfilMenuDTO> getAllParSocietProfilMenu(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseOutput<ProfilMenuDTO> getAllProfilMenu() {
		ResponseOutput <ProfilMenuDTO> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List<ProfilMenuDTO> ProfilMenu = profilMenuConverter.toVoList(profilMenuRepository.findAll());
        
        if( ProfilMenu.size() !=0 )
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setCollection(ProfilMenu);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return societeDTOResponseOutput;
    }
	
	
	
	@Override
	public ResponseOutput<ProfilMenu> getAllProfilMen() {
		ResponseOutput <ProfilMenu> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List<ProfilMenu> ProfilMenu = profilMenuRepository.findAll();
        
        if( ProfilMenu.size() !=0 )
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            societeDTOResponseOutput.setCollection(ProfilMenu);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return societeDTOResponseOutput;
    }
       
	@Override
	public ResponseOutput<ProfilMenu> getAllProfilMenParSociete(Long id) {
		ResponseOutput <ProfilMenu> societeDTOResponseOutput = new ResponseOutput<>();
        societeDTOResponseOutput.setTypeOperation("GET");
        List<ProfilMenu> ProfilMenu = profilMenuRepository.findAll();
        List<ProfilMenu> profilMenu2=new ArrayList();
        if( ProfilMenu.size() !=0 )
        {
            societeDTOResponseOutput.setCode("200");
            societeDTOResponseOutput.setStatut("SUCCES");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
            for(ProfilMenu profillMenu :ProfilMenu) {
            	if(profillMenu.getProfil().getSociete().getId()-id==0) {
            		profilMenu2.add(profillMenu);
            	}
            }
            societeDTOResponseOutput.setCollection(profilMenu2);

        }
        else
        {
            societeDTOResponseOutput.setCode("500");
            societeDTOResponseOutput.setStatut("NOT FOUND");
            societeDTOResponseOutput.setMessage("Operation Effectu?? !");
        }
        return societeDTOResponseOutput;
    }
       
    /*==========================================================================*/

	 
}
