package org.telio;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.telio.portail_societe.dao.*;
import org.telio.portail_societe.dto.converter.*;
import org.telio.portail_societe.dto.entities.*;
import org.telio.portail_societe.metier.interfaces.IHabilitation;
import org.telio.portail_societe.metier.interfaces.IUserService;
import org.telio.portail_societe.model.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@CrossOrigin(origins = "*")

public class StageHabilitationApplication implements CommandLineRunner {

	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private TypeSocieteRepository typeSocieteRepository;
	@Autowired
	private SocieteRepository societeRepository;
	@Autowired
	private ProfilRepository profilRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private LocaliteRepository localiteRepository;
	@Autowired
	private EntiteRepository entiteRepository;
	@Autowired
	private TypeEntiteRepository typeEntiteRepository;
	@Autowired
	private TypeSocieteConverter converter;
	@Autowired
	private SocieteConverter soConverter;
	@Autowired
	private ApplicationConverter appConverter;
	@Autowired
	private MenuConverter menuConverter;
	@Autowired
	private ProfilConverter profilConverter;
	@Autowired
	private LocaliteConverter localiteConverter;
	@Autowired
	private TypeEntiteConverter typeEntiteConverter;
	@Autowired
	private EntiteConverter entiteConverter;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private UtilisateurConverter utilisateurConverter;
	@Autowired
	private RoleConverter roleConverter;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private SocieteConverter societeConverter;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}


	public static void main(String[] args) {
		SpringApplication.run(StageHabilitationApplication.class, args);
		System.out.println("It's working !");
	}

	@Override
	public void run(String... args) throws Exception {
//
//		TypeSocieteDTO informatique = new TypeSocieteDTO("InforMAtique","15887","InFO","A");
//		TypeSocieteDTO banque = new TypeSocieteDTO("BANQUE","78898","BQ","A");
//		TypeSocieteDTO assurance = new TypeSocieteDTO("ASSURANCE", "441541","AR","V");
//
//		typeSocieteRepository.save(converter.toBo(informatique));
//		typeSocieteRepository.save(converter.toBo(banque));
//		typeSocieteRepository.save(converter.toBo(assurance));
////
//		TypeSocieteDTO infos = converter.toVo(typeSocieteRepository.findByNom("informatique"));
//		TypeSocieteDTO banques = converter.toVo(typeSocieteRepository.findByNom("BANQUE"));
//		TypeSocieteDTO assurances = converter.toVo(typeSocieteRepository.findByNom("ASSURANCE"));
//
////
//		SocieteDTO telio = new SocieteDTO("TELIO maroc","102322","TELIO","SA",infos);
//		SocieteDTO creditAgricole = new SocieteDTO("CREDIT agricole","12366","cam","RA",banques);
//		SocieteDTO atlanta = new SocieteDTO("ATLANTA","588954","AT","AS",assurances);
//
//		societeRepository.save(soConverter.toBo(telio));
//		societeRepository.save(soConverter.toBo(creditAgricole));
//		societeRepository.save(soConverter.toBo(atlanta));
//
//		SocieteDTO searchedTelio = soConverter.toVo(societeRepository.findByNom("TELIO MAROC"));
//		SocieteDTO searchedCam = soConverter.toVo(societeRepository.findByNom("CREDIT AGRICOLE"));
//
//
//		ApplicationDTO gestionFamille = new ApplicationDTO("TRAVAILLEUR NON SALARIALE","Opportunité qui permet l'assurance des TNS",searchedTelio);
//		ApplicationDTO systemeIternational = new ApplicationDTO("Systeme international","Possibilité d'effectuer des transations dans le monde ",searchedCam);
////
//		gestionFamille.setCreatedBy("SYSTEM");
//		gestionFamille.setCreatedDate(new Date());
//		applicationRepository.save(appConverter.toBo(gestionFamille));
//		applicationRepository.save(appConverter.toBo(systemeIternational));
//
//		applicationRepository.findAll().forEach(data ->{
//			System.out.println("=========================");
//			System.out.println(data);
//			System.out.println("========================");
//		});
//
//		ApplicationDTO gestion = appConverter.toVo(applicationRepository.findByNom("TRAVAILLEUR NON SALARIALE").get(0));
//
//		MenuDTO admin = new MenuDTO("M","MODERATEUR","MENU CONCUE POUR LES MODERATEURS","MODERATEUR ADMIN ","CRUD ET TNS",1,gestion,null);
//		ProfilDTO profil1 = new ProfilDTO("profil java",searchedCam);
//		profilRepository.save(profilConverter.toBo(profil1));
//		Set<ProfilDTO> listProfils = new HashSet<>();
//		ProfilDTO foundProfil = profilConverter.toVo(profilRepository.findByNom("profil java").get(0));
//		listProfils.add(foundProfil);
//		admin.setProfilDTOS(listProfils);
//		menuRepository.save(menuConverter.toBo(admin));
//		MenuDTO adminMenu = menuConverter.toVo(menuRepository.findByNom("MODERATEUR").get(0));
//		MenuDTO user = new MenuDTO("U","UTILISATEUR","MENU CONCUE POUR LES USERS","MODERATEUR ADMIN USERS ","CRUD ",2,gestion,adminMenu);
//		user.setProfilDTOS(listProfils);
//		menuRepository.save(menuConverter.toBo(user));
//
//		LocaliteDTO albania = new LocaliteDTO("albania","5465858","alb");
//		localiteRepository.save(localiteConverter.toBo(albania));
//
//		LocaliteDTO albaniafound = localiteConverter.toVo(localiteRepository.findByNom("ALbania"));
//
//		System.out.println("======================================");
//		System.out.println(albaniafound);
//		System.out.println("=======================================");
//
//		TypeEntiteDTO direction = new TypeEntiteDTO("Direction General","13D5",null,searchedCam);
//
//		typeEntiteRepository.save(typeEntiteConverter.toBo(direction));
//		TypeEntiteDTO foundDirection = typeEntiteConverter.toVo(typeEntiteRepository.findByNom("Direction General").get(0));
//
//		EntiteDTO directionRegional = new EntiteDTO("Direction Regional","0236",null,albaniafound,searchedCam,foundDirection);
//
//		entiteRepository.save(entiteConverter.toBo(directionRegional));
//
//		EntiteDTO foundRegion = entiteConverter.toVo(entiteRepository.findByNom("Direction Regional").get(0));
//
//		System.out.println("======================================");
//		System.out.println(foundRegion);
//		System.out.println("=======================================");
////		menuRepository.findAll().forEach(data->{
////			System.out.println(data.getProfils());
////		});
//
//
//
//
////		typeSocieteRepository.save(new TypeSociete("BANQUE","12365","BQ"));
////		TypeSociete banque = typeSocieteRepository.findByNom("BANQUE");
//
////		societeRepository.save(new Societe("TELIO MAROC","10225","TELIO",banque));
////		Societe telio = societeRepository.findByNom("TELIO MAROC");
////		applicationRepository.save(new Application("GESTION DE LA FAMILLE","Application dédié au travailleur non salairiale",telio));
////
////		Application tns = applicationRepository.findByNom("GESTION DE LA FAMILLE");
////		menuRepository.save(new Menu("M","PORTAIL MOEDRATEUR","MENU CONCU AU MODERATEUR","MODERATEUR ADMIN USER","OPERATION CRUD ET GESTION DES USERS",tns,null));
////		Menu moderateur = menuRepository.findByNom("PORTAIL MOEDRATEUR");
////		menuRepository.save(new Menu("A","PORTAIL ADMIN","MENU CONCU AU ADMINS","ADMIN USER","OPERATION CRUD",tns,moderateur));
////		menuRepository.save(new Menu("A","PORTAIL UTILISATEUR","MENU CONCU A TOUTE PERSONNE AYANT UN COMPTE","USER","OPERATION AFFICHAGE",tns,moderateur));
////
//
//		typeEntiteRepository.deleteAll();
		
		RoleDTO roleDTO = new RoleDTO("ADMIN");
		iUserService.persist(roleDTO); 
		RoleDTO admin = iUserService.searchRoleByLibele("ADMIN").getData();
		SocieteDTO SocieteDTO = soConverter.toVo(societeRepository.findByCode("123666"));//		TypeEntite type = typeEntiteRepository.findByNom("DIRECTION GENERAL").get(0);
		LocaliteDTO localiteDTO = localiteConverter.toVo(localiteRepository.findByNom("ALBANIA"));
//		entiteRepository.save(new Entite("ab", "456", null, localiteConverter.toBo(localiteDTO), soConverter.toBo(SocieteDTO),type));
//
		TypeEntiteDTO entiteDTO = (TypeEntiteDTO) typeEntiteConverter.toVoList(typeEntiteRepository.findBySociete(societeConverter.toBo(SocieteDTO)));
		System.out.println("tttype : "+entiteDTO);
		ProfilDTO profilDTO = profilConverter.toVo(profilRepository.findByNom("PROFIL JAVA").get(0));
//
//		utilisateurRepository.deleteAll();

//		iUserService.persist(new UtilisateurDTO("AZAMI", "Omar015", "omarazami15@gmail.com", "123456789", "public", "0623061248",
//												SocieteDTO, entiteDTO, profilDTO, Arrays.asList(admin)));


	}
}
