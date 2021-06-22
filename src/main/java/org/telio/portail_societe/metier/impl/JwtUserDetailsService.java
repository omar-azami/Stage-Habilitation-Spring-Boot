package org.telio.portail_societe.metier.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telio.portail_societe.dao.RoleRepository;
import org.telio.portail_societe.dao.SocieteRepository;
import org.telio.portail_societe.dao.UtilisateurHistoriqueRepository;
import org.telio.portail_societe.dao.UtilisateurRepository;
import org.telio.portail_societe.dto.converter.RoleConverter;
import org.telio.portail_societe.dto.converter.SocieteConverter;
import org.telio.portail_societe.dto.converter.UtilisateurConverter;
import org.telio.portail_societe.dto.converter.UtilisateurHistoriqueConverter;
import org.telio.portail_societe.dto.entities.ProfilDTO;
import org.telio.portail_societe.dto.entities.RoleDTO;
import org.telio.portail_societe.dto.entities.SocieteDTO;
import org.telio.portail_societe.dto.entities.UtilisateurDTO;
import org.telio.portail_societe.dto.entities.UtilisateurHistoriqueDTO;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.idClass.UtilisateurID;
import org.telio.portail_societe.metier.interfaces.IUserService;
import org.telio.portail_societe.model.UtilisateurHistorique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class JwtUserDetailsService implements IUserService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private UtilisateurConverter utilisateurConverter;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SocieteRepository societeRepository;
    @Autowired
    private SocieteConverter societeConverter;
    @Autowired
    private UtilisateurHistoriqueRepository utilisateurHistoriqueRepository;
    @Autowired
    private UtilisateurHistoriqueConverter utilisateurHistoriqueConverter;
    
    @Override
    public ResponseOutput<UtilisateurDTO> persist(UtilisateurDTO utilisateurDTO) {
        ResponseOutput <UtilisateurDTO> utilisateurDTOResponseOutput = new ResponseOutput<>();
        utilisateurDTO.setPassword(bCryptPasswordEncoder.encode(utilisateurDTO.getPassword()));
        List<RoleDTO> rolesPersist = new ArrayList<>();
        for (RoleDTO role : utilisateurDTO.getRoleDTOList()) {
            RoleDTO userRole = roleConverter.toVo(roleRepository.findByLibele(role.getLibele()));
            rolesPersist.add(userRole);
        }
        utilisateurDTO.setRoleDTOList(rolesPersist);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) utilisateurDTO.setCreatedBy("SYSTEM");
        else
        {

            utilisateurDTO.setCreatedBy(authentication.getName());
            utilisateurDTO.setCreatedDate(new Date());
        }
        //utilisateurDTO.setCreatedBy("MODERATEUR");
        utilisateurDTO.setCreatedDate(new Date());
        if(utilisateurDTO.getProfilDTO() == null) {
            return utilisateurDTOResponseOutput;

        }
        if(utilisateurDTO.getEntiteDTO() == null) {
            return utilisateurDTOResponseOutput;

        }
        if(utilisateurDTO.getRoleDTOList() == null) {
            return utilisateurDTOResponseOutput;

        }

        utilisateurRepository.save(utilisateurConverter.toBo(utilisateurDTO));
        return utilisateurDTOResponseOutput;
    }

    @Override
    public ResponseOutput<UtilisateurDTO> getAllUtilisateur(String fieldName) {
        ResponseOutput <UtilisateurDTO> utilisateurDTOResponseOutput = new ResponseOutput<>();
        utilisateurDTOResponseOutput.setTypeOperation("GET");
        List <UtilisateurDTO> utilisateurDTOS = utilisateurConverter.toVoList(utilisateurRepository.findAll(Sort.by(fieldName)));
        if (utilisateurDTOS.size() == 0 )
        {
            utilisateurDTOResponseOutput.setCode("300");
            utilisateurDTOResponseOutput.setStatut("NOT FOUND");
            utilisateurDTOResponseOutput.setMessage(" Aucun utilisateur trouvé .. ");
        }
        else
        {
            utilisateurDTOResponseOutput.setCode("200");
            utilisateurDTOResponseOutput.setStatut("SUCCES");
            utilisateurDTOResponseOutput.setMessage(" Opération effectué ");
            utilisateurDTOResponseOutput.setCollection(utilisateurDTOS);
        }

        return utilisateurDTOResponseOutput;
    }

    @Override
	public ResponseOutput<UtilisateurDTO> updatePourAdmin(UtilisateurDTO utilisateurDTO, UtilisateurID utilisateurID) {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	utilisateurDTO.setLastModifiedBy(authentication.getName());
    	utilisateurDTO.setLastModifedDate(new Date());
    	UtilisateurDTO wanted  = utilisateurConverter.toVo(utilisateurRepository.findById(utilisateurID).get());
    	utilisateurDTO.setLogin(wanted.getLogin());
        utilisateurDTO.setPassword(wanted.getPassword());
        utilisateurDTO.setLogin(wanted.getLogin());
        utilisateurDTO.setCreatedBy(wanted.getCreatedBy());
        utilisateurDTO.setCreatedDate(wanted.getCreatedDate());
        if(utilisateurDTO.getProfilDTO() == null) {
        	utilisateurDTO.setProfilDTO(wanted.getProfilDTO());
        }
        if(utilisateurDTO.getEntiteDTO() == null) {
        	utilisateurDTO.setEntiteDTO(wanted.getEntiteDTO());
        }
        if(utilisateurDTO.getRoleDTOList() == null) {
        	utilisateurDTO.setRoleDTOList(wanted.getRoleDTOList());
        }
        ResponseOutput <UtilisateurDTO> utilisateurDTOResponseOutput = new ResponseOutput<>();
        utilisateurDTOResponseOutput.setTypeOperation("PATCH");

        if(utilisateurRepository.existsById(utilisateurID))
        {
            System.out.println("ID : "+ wanted.getNom());
            
            
            
            utilisateurDTO.setId(utilisateurID.getId());
            utilisateurDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(utilisateurID.getSociete()).get()));
            utilisateurRepository.save(utilisateurConverter.toBo(utilisateurDTO));
            utilisateurDTOResponseOutput.setCode("200");
            utilisateurDTOResponseOutput.setStatut("SUCCESS");
            utilisateurDTOResponseOutput.setMessage("Utilisateur Modifié");
            utilisateurDTOResponseOutput.setData(utilisateurDTO);
            return utilisateurDTOResponseOutput;
        }
        utilisateurDTOResponseOutput.setCode("500");
        utilisateurDTOResponseOutput.setStatut("NOT FOUND");
        utilisateurDTOResponseOutput.setMessage("Utilisateur que vous cherchez est introuvable " );
        return utilisateurDTOResponseOutput;
	}

    @Override
	public ResponseOutput<UtilisateurDTO> updatePourModerateur(UtilisateurDTO utilisateurDTO, UtilisateurID utilisateurID) {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	utilisateurDTO.setLastModifiedBy(authentication.getName());
    	utilisateurDTO.setLastModifedDate(new Date());
    	UtilisateurDTO wanted  = utilisateurConverter.toVo(utilisateurRepository.findById(utilisateurID).get());
    	utilisateurDTO.setLogin(wanted.getLogin());
        utilisateurDTO.setCreatedBy(wanted.getCreatedBy());
        utilisateurDTO.setCreatedDate(wanted.getCreatedDate());
        
        if(utilisateurDTO.getProfilDTO() == null) {
        	utilisateurDTO.setProfilDTO(wanted.getProfilDTO());
        }
        if(utilisateurDTO.getEntiteDTO() == null) {
        	utilisateurDTO.setEntiteDTO(wanted.getEntiteDTO());
        }
        if(utilisateurDTO.getRoleDTOList() == null) {
        	utilisateurDTO.setRoleDTOList(wanted.getRoleDTOList());
        }

        ResponseOutput <UtilisateurDTO> utilisateurDTOResponseOutput = new ResponseOutput<>();
        utilisateurDTOResponseOutput.setTypeOperation("PATCH");

        if(utilisateurRepository.existsById(utilisateurID))
        {
            System.out.println("ID : "+ wanted.getNom());
            utilisateurDTO.setPassword(bCryptPasswordEncoder.encode(utilisateurDTO.getPassword()));

            
            utilisateurDTO.setId(utilisateurID.getId());
            utilisateurDTO.setSocieteDTO(societeConverter.toVo(societeRepository.findById(utilisateurID.getSociete()).get()));
            utilisateurRepository.save(utilisateurConverter.toBo(utilisateurDTO));
            utilisateurDTOResponseOutput.setCode("200");
            utilisateurDTOResponseOutput.setStatut("SUCCESS");
            utilisateurDTOResponseOutput.setMessage("Utilisateur Modifié");
            utilisateurDTOResponseOutput.setData(utilisateurDTO);
            return utilisateurDTOResponseOutput;
        }
        utilisateurDTOResponseOutput.setCode("500");
        utilisateurDTOResponseOutput.setStatut("NOT FOUND");
        utilisateurDTOResponseOutput.setMessage("Utilisateur que vous cherchez est introuvable " );
        return utilisateurDTOResponseOutput;
	}
    
	@Override
	
	public ResponseOutput<UtilisateurDTO> delete(UtilisateurID utilisateurID) {

		ResponseOutput <UtilisateurDTO> UtilisateurDTOResponseOutput = new ResponseOutput<>();
		UtilisateurDTOResponseOutput.setTypeOperation("DELETE");
        // 
         if(utilisateurRepository.existsById(utilisateurID))
         {
        	 for (UtilisateurHistoriqueDTO utilisarueHistoriqueDTO : utilisateurHistoriqueConverter.toVoList(utilisateurHistoriqueRepository.findAll()))
        	 {
        		 if (utilisarueHistoriqueDTO.getUtilisateurDTO().getId() == utilisateurID.getId())
        		 {
        			 UtilisateurDTOResponseOutput.setCode("300");
        			 UtilisateurDTOResponseOutput.setStatut("WARNING");
        			 UtilisateurDTOResponseOutput.setMessage("Utiliateur n'a pas été supprimé ,Veuillez suprrimer les Utilisateurs ayant ce type  ");
                     return UtilisateurDTOResponseOutput;
        		 }
        		 
        	 }
        	 UtilisateurDTOResponseOutput.setCode("200");
        	 UtilisateurDTOResponseOutput.setStatut("SUCCES");
             UtilisateurDTOResponseOutput.setMessage(" Utiliateur a été supprimé ! ");
             utilisateurRepository.deleteById(utilisateurID);
             return UtilisateurDTOResponseOutput;
        	 
        	 
         }
         else {
        	 // element not found 
        	 UtilisateurDTOResponseOutput.setCode("500");
        	 UtilisateurDTOResponseOutput.setStatut("NOT FOUND");
        	 UtilisateurDTOResponseOutput.setMessage("Utiliateur que vous voulez supprimé ne figure pas dans notre liste des Utiliateurs ");
             return UtilisateurDTOResponseOutput;
         }
	}

	@Override
	public ResponseOutput<UtilisateurDTO> getUtilisateurBySociete(Long id) {

		SocieteDTO societeDTO = societeConverter.toVo(societeRepository.findById(id).get());
        ResponseOutput <UtilisateurDTO> utilisateurDTOResponseOutput = new ResponseOutput<>();
        utilisateurDTOResponseOutput.setTypeOperation("GET");

        utilisateurDTOResponseOutput.setCollection(utilisateurConverter.toVoList(utilisateurRepository.findBySociete(societeConverter.toBo(societeDTO))));
        return utilisateurDTOResponseOutput;
	}
    
    @Override
    public ResponseOutput<RoleDTO> persist(RoleDTO roleDTO) {
        ResponseOutput <RoleDTO> roleDTOResponseOutput = new ResponseOutput<>();
        if (roleRepository.existsByLibele(roleDTO.getLibele()))
        {
            roleDTOResponseOutput.setCode("404");
            roleDTOResponseOutput.setStatut("ERROR");
            roleDTOResponseOutput.setMessage(" Ce role est déja insérer dans notre base");
        }
        else
        {
            roleRepository.save(roleConverter.toBo(roleDTO));
            roleDTOResponseOutput.setCode("200");
            roleDTOResponseOutput.setStatut("SUCCES");
            roleDTOResponseOutput.setMessage("Operation effectué ");
            roleDTOResponseOutput.setData(roleDTO);
        }
        return roleDTOResponseOutput;
    }

    @Override
    public ResponseOutput<RoleDTO> searchRoleByLibele(String libele) {
        ResponseOutput <RoleDTO> roleDTOResponseOutput = new ResponseOutput<>();
        RoleDTO roleDTO = roleConverter.toVo(roleRepository.findByLibele(libele));
        if(roleDTO != null)
        {
            roleDTOResponseOutput.setCode("200");
            roleDTOResponseOutput.setStatut("SUCCES");
            roleDTOResponseOutput.setMessage("Operation Effectué avec succés");
            roleDTOResponseOutput.setData(roleDTO);
        }
        else
        {
            roleDTOResponseOutput.setCode("300");
            roleDTOResponseOutput.setStatut("NOT FOUND");
            roleDTOResponseOutput.setMessage("Aucun enregistrement comportant ce " + libele);
        }
        return roleDTOResponseOutput;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UtilisateurDTO userDTO = utilisateurConverter.toVo(utilisateurRepository.findByLogin(s));
        if (userDTO == null)
            throw new UsernameNotFoundException("User not found with username: " + s);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User(userDTO.getLogin(), userDTO.getPassword(), enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(userDTO.getRoleDTOList()));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<RoleDTO> roleDTOS) {
        List<GrantedAuthority> springSecurityAuthorities = new ArrayList<>();
        for (RoleDTO r : roleDTOS) {
            springSecurityAuthorities.add(new SimpleGrantedAuthority(r.getLibele()));
        }
        return springSecurityAuthorities;
    }

	@Override
	public ResponseOutput<RoleDTO> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
