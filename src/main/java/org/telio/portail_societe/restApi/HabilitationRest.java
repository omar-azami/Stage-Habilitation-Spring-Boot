package org.telio.portail_societe.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.telio.portail_societe.dto.entities.*;
import org.telio.portail_societe.generic.classes.ResponseOutput;
import org.telio.portail_societe.idClass.ApplicationID;
import org.telio.portail_societe.idClass.EntiteID;
import org.telio.portail_societe.idClass.ProfilID;
import org.telio.portail_societe.idClass.TypeEntiteID;
import org.telio.portail_societe.metier.interfaces.IHabilitation;
import org.telio.portail_societe.metier.interfaces.IModerateur;
@CrossOrigin(origins = "*")

@RestController

@RequestMapping("/admin")
public class HabilitationRest {

    @Autowired
    private IHabilitation iHabilitation;
    
    @Autowired
    private IModerateur iModeratuer;

    @PostMapping("/add/typeSociete")
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > persist (@RequestBody TypeSocieteDTO typeSocieteDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(typeSocieteDTO), HttpStatus.OK);
    }

    @PatchMapping("/update/typeSociete/{id}")
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > update ( @PathVariable("id") Long id, @RequestBody TypeSocieteDTO typeSocieteDTO)
    {
        return new ResponseEntity<>(iHabilitation.update(id,typeSocieteDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/typeSociete/{id}")
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > deleteTypeEntite ( @PathVariable("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.deleteTypeSociete(id),HttpStatus.OK);
    }

    @GetMapping(value = {"/show/typeSociete/{fieldName}", "/show/typeSociete"})
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > getAllTypeSocietes ( @PathVariable(name = "fieldName", required = false) String fieldName)
    {
        String field = fieldName == null ? "nom": fieldName;
        return new ResponseEntity<>(iHabilitation.getAllTypeSocieteSortBy(field),HttpStatus.OK);
    }

    @GetMapping(value = {"/show/typeSociete/id/{id}"})
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > getTypeSocieteByID ( @PathVariable(name = "id", required = true) Long id)
    {
        return new ResponseEntity<>(iHabilitation.searchTypeSocieteByID(id),HttpStatus.OK);
    }
    @GetMapping("/show/typeSocieteBy/code/{code}")
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > getTypeSocieteByCode (@PathVariable ("code") String code)
    {
        return new ResponseEntity<>(iHabilitation.searchTypeSocieteByCode(code), HttpStatus.OK);
    }

    @GetMapping("/show/typeSocieteBy/statut/{statut}")
    public ResponseEntity <ResponseOutput <TypeSocieteDTO> > getTypeSocieteByStatut (@PathVariable ("statut") String statut)
    {
        return new ResponseEntity<>(iHabilitation.searchTypeSocieteByStatut(statut),HttpStatus.OK);
    }

    @PostMapping("/add/societesa")
    public ResponseEntity <ResponseOutput <SocieteDTO> > persistSA (@RequestBody SocieteDTO societeDTO)
    {
        return new ResponseEntity<>(iModeratuer.fonctionGlobale(societeDTO),HttpStatus.OK);
    }
    
    @PostMapping("/add/societe")
    public ResponseEntity <ResponseOutput <SocieteDTO> > persist (@RequestBody SocieteDTO societeDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(societeDTO),HttpStatus.OK);
    }

    @PatchMapping("/patch/societe/{id}")
    public ResponseEntity <ResponseOutput <SocieteDTO> > update ( @PathVariable("id") Long id, @RequestBody SocieteDTO societeDTO)
    {
        return new ResponseEntity<>(iHabilitation.update(id, societeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/societe/{id}")
    public ResponseEntity <ResponseOutput <SocieteDTO> > deleteSociete (@PathVariable("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.deleteSociete(id),HttpStatus.OK);
    }

    @GetMapping("/show/societe/nom/{nom}")
    public ResponseEntity <ResponseOutput <SocieteDTO> > getSocieteByNom ( @PathVariable("nom") String nom)
    {
        return new ResponseEntity<>(iHabilitation.searchSocieteByNom(nom),HttpStatus.OK);
    }

    @GetMapping("/show/societeBy/code/{code}")
    public ResponseEntity <ResponseOutput <SocieteDTO> > getSocieteByCode ( @PathVariable("code") String code)
    {
        return new ResponseEntity<>(iHabilitation.searchSocieteByCode(code),HttpStatus.OK);
    }

    @GetMapping("/show/societe/nomAbrege/{nomAbrege}")
    public ResponseEntity <ResponseOutput <SocieteDTO> > getSocieteByNomAbrege ( @PathVariable("nomAbrege") String nomAbrege)
    {
        return new ResponseEntity<>(iHabilitation.searchSocieteByNomAbrege(nomAbrege),HttpStatus.OK);
    }

    @GetMapping("/show/societe/statut/{statut}")
    public ResponseEntity <ResponseOutput <SocieteDTO> > getSocieteByStatut ( @PathVariable("statut") String statut)
    {
        return new ResponseEntity<>(iHabilitation.searchSocieteByStatut(statut),HttpStatus.OK);
    }

    @GetMapping(value = {"/show/societe/{fieldName}","/show/societe"})
    public ResponseEntity <ResponseOutput <SocieteDTO> > getAllSocietes ( @PathVariable(name = "fieldName", required = false) String fieldName)
    {
        String field = fieldName == null ? "id": fieldName;
        return new ResponseEntity<>(iHabilitation.getAllSocietesSortBy(field),HttpStatus.OK);
    }

    @GetMapping(value = {"/show/societe/id/{id}"})
    public ResponseEntity <ResponseOutput <SocieteDTO> > searchSocieteByID ( @PathVariable(name = "id", required = true) Long id)
    {
        return new ResponseEntity<>(iHabilitation.searchSocieteByID(id),HttpStatus.OK);
    }
    
    
    
    @PostMapping("/add/application")
    public ResponseEntity <ResponseOutput <ApplicationDTO> > persist (@RequestBody ApplicationDTO applicationDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(applicationDTO),HttpStatus.OK);
    }

    @PatchMapping ("/patch/application/{id}/{societe}")
    public ResponseEntity <ResponseOutput <ApplicationDTO> > update ( @PathVariable("id") Long id, @PathVariable("societe") Long societe, @RequestBody ApplicationDTO applicationDTO)
    {
        ApplicationID app = new ApplicationID();
        app.setId(id);
        app.setSociete(societe);
        return new ResponseEntity<>(iHabilitation.update(app,applicationDTO),HttpStatus.OK);
    }

    @DeleteMapping("/delete/application/{id}/{societe}")
    public ResponseEntity <ResponseOutput <ApplicationDTO> > deleteApplication (@PathVariable("id") Long id, @PathVariable("societe") Long societe)
    {
        ApplicationID app = new ApplicationID();
        app.setId(id);
        app.setSociete(societe);
        return new ResponseEntity<>(iHabilitation.deleteApplication(app), HttpStatus.OK);
    }

    @GetMapping("/show/application/nom/{nom}")
    public ResponseEntity <ResponseOutput <ApplicationDTO> > getApplicationByNom ( @PathVariable ("nom") String nom)
    {
        return new ResponseEntity<>(iHabilitation.searchApplicationByNom(nom), HttpStatus.OK);
    }

    @GetMapping("/show/application/ID/{id}/{societe}")
    public ResponseEntity <ResponseOutput <ApplicationDTO> > getApplicationByID (@PathVariable ("id") Long id, @PathVariable ("societe") Long societe)
    {
        return new ResponseEntity<>(iHabilitation.searchApplicationByID(id,societe),HttpStatus.OK);
    }

    @GetMapping(value = {"/show/applications", "/show/applications/{fieldName}"})
    public ResponseEntity <ResponseOutput <ApplicationDTO> > getAllApplications (@PathVariable (name = "fieldName", required = false) String fieldName)
    {
        String field = fieldName == null ? "id" : fieldName;
        return new ResponseEntity<>(iHabilitation.getAllApplicationsSortBy(field),HttpStatus.OK);
    }
    
    @GetMapping("/show/application/societe/id/{id}")
    public ResponseEntity <ResponseOutput <ApplicationDTO> > getApplicationBySociete ( @PathVariable ("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.getApplicationBySociete(id), HttpStatus.OK);
    }
    

    @PostMapping("/add/profil")
    public ResponseEntity <ResponseOutput <ProfilDTO> > persist (@RequestBody ProfilDTO profilDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(profilDTO),HttpStatus.OK);
    }

    @PatchMapping("/patch/profil/{id}/{societe}")
    public ResponseEntity <ResponseOutput <ProfilDTO> > update ( @PathVariable("id") Long id, @PathVariable("societe") Long societe, @RequestBody ProfilDTO profilDTO)
    {
        ProfilID profilID = new ProfilID();
        profilID.setId(id);
        profilID.setSociete(societe);
        return new ResponseEntity<>(iHabilitation.update(profilID,profilDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/profil/{id}/{societe}")
    public ResponseEntity <ResponseOutput <ProfilDTO> > deleteProfile (@PathVariable ("id") Long id, @PathVariable ("societe") Long societe)
    {
        ProfilID profilID = new ProfilID();
        profilID.setId(id);
        profilID.setSociete(societe);
        return new ResponseEntity<>(iHabilitation.deleteProfil(profilID), HttpStatus.OK);
    }

    @GetMapping("/show/profil/nom/{nom}")
    public ResponseEntity <ResponseOutput <ProfilDTO> > getProfilByNom (@PathVariable ("nom") String nom)
    {
        return new ResponseEntity<>(iHabilitation.searchProfilByNom(nom), HttpStatus.OK);
    }
    
    @GetMapping("/show/profil/societe/id/{id}")
    public ResponseEntity <ResponseOutput <ProfilDTO> > getProfilBySociete ( @PathVariable ("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.getProfilBySociete(id), HttpStatus.OK);
    }
    

    @GetMapping("/show/profil/id/{id}/{societe}")
    public ResponseEntity <ResponseOutput <ProfilDTO> > getProfilByID ( @PathVariable ("id") Long id, @PathVariable ("societe") Long societe)
    {
        return new ResponseEntity<>(iHabilitation.searchProfilByID(id,societe), HttpStatus.OK);
    }

    @GetMapping(value = {"/show/profil/{fieldName}", "/show/profil"})
    public ResponseEntity <ResponseOutput <ProfilDTO> > getAllProfiles ( @PathVariable (name = "fieldName" , required = false) String fieldName)
    {
        String field = fieldName == null ? "id": fieldName;
        return new ResponseEntity<>(iHabilitation.getAllProfilsSortBy(field), HttpStatus.OK);
    }

    @PostMapping("/add/localite")
    public ResponseEntity <ResponseOutput <LocaliteDTO> > persist (@RequestBody LocaliteDTO localiteDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(localiteDTO), HttpStatus.OK);
    }

    @PatchMapping("/patch/localite/{id}")
    public ResponseEntity <ResponseOutput <LocaliteDTO> > update ( @PathVariable ("id") Long id, @RequestBody LocaliteDTO localiteDTO)
    {
        return new ResponseEntity<>(iHabilitation.update(id,localiteDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/localite/{id}")
    public ResponseEntity <ResponseOutput <LocaliteDTO> > deleteLocalite ( @PathVariable ("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.deleteLocalite(id), HttpStatus.OK);
    }

    @GetMapping("/show/localite/nom/{nom}")
    public ResponseEntity <ResponseOutput <LocaliteDTO> > getLocaliteByNom ( @PathVariable ("nom") String nom)
    {
        return new ResponseEntity<>(iHabilitation.searchLocaliteByNom(nom), HttpStatus.OK);
    }

    @GetMapping("/show/localite/code/{code}")
    public ResponseEntity <ResponseOutput <LocaliteDTO> > getLocaliteByCode ( @PathVariable ("code") String code)
    {
        return new ResponseEntity<>(iHabilitation.searchLocaliteByCode(code), HttpStatus.OK);
    }

    @GetMapping("/show/localite/abrev/{nomAbrege}")
    public ResponseEntity <ResponseOutput <LocaliteDTO> > getLocaliteByNomAbrege ( @PathVariable ("nomAbrege") String nomAbrege)
    {
        return new ResponseEntity<>(iHabilitation.searchLocaliteByNomAbrege(nomAbrege), HttpStatus.OK);
    }

    @GetMapping(value = {"/show/localite","/show/localite/{fieldName}"})
    public ResponseEntity <ResponseOutput <LocaliteDTO> > getAllLocalities ( @PathVariable (name = "fieldName", required = false) String fieldName)
    {
        String field = fieldName == null ? "id" : fieldName;
        return new ResponseEntity<>(iHabilitation.getAllLocaliteSortBy(field), HttpStatus.OK);
    }


    @GetMapping(value = {"/show/localite/id/{id}"})
    public ResponseEntity <ResponseOutput <LocaliteDTO> > getLocaliteByID ( @PathVariable(name = "id", required = true) Long id)
    {
        return new ResponseEntity<>(iHabilitation.searchLocaliteByID(id),HttpStatus.OK);
    }
    
    @PostMapping("/add/type-entite")
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> > persist (@RequestBody TypeEntiteDTO typeEntiteDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(typeEntiteDTO), HttpStatus.OK);
    }

    @PatchMapping("/update/type-entite/{id}/{societe}")
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> > update ( @PathVariable("id") Long id, @PathVariable("societe") Long societe, @RequestBody TypeEntiteDTO typeEntiteDTO)
    {
        TypeEntiteID key = new TypeEntiteID();
        key.setId(id);
        key.setSociete(societe);
        return  new ResponseEntity<>(iHabilitation.update(key, typeEntiteDTO), HttpStatus.OK);
    }
    
    @GetMapping(value = {"/show/type-entite/{societeDTO}"})
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> > getAllTypeEntiteSociete ( @RequestBody SocieteDTO societeDTO)
    {
        return new ResponseEntity<>(iHabilitation.getAllTypeEntitiesBySociete(societeDTO),HttpStatus.OK);
    }
    
    @GetMapping("/show/type-entite/societe/id/{id}")
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> > getTypeEntitiesBySociete ( @PathVariable ("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.getTypeEntitiesBySociete(id), HttpStatus.OK);
    }
    
    @GetMapping(value = {"/show/type-entite/all/{fieldName}", "/show/type-entite/all"})
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> > getAllTypeEntities ( @PathVariable(name = "fieldName", required = false) String fieldName)
    {
        String field = fieldName == null ? "nom": fieldName;
        return new ResponseEntity<>(iHabilitation.getAllTypeEntitiesSortBy(field),HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/type-entite/{id}/{societe}")
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> >  deleteTypeEntite ( @PathVariable("id") Long id, @PathVariable("societe") Long societe)
    {
    TypeEntiteID key = new TypeEntiteID();
    key.setId(id);
    key.setSociete(societe);
    System.out.println("key  :"+key);
    
        return new ResponseEntity<>(iHabilitation.deleteTypeEntite(key), HttpStatus.OK);
    }
    
    @GetMapping(value = {"/show/typeEntiteById/id/{id}"})
    public ResponseEntity <ResponseOutput <TypeEntiteDTO> > getTypeEntitiesById ( @PathVariable(name = "id", required = true) Long id)
    {
        return new ResponseEntity<>(iHabilitation.getTypeEntitiesById(id),HttpStatus.OK);
    }
    
    
    @PostMapping("/add/entite")
    public ResponseEntity <ResponseOutput <EntiteDTO> > persist (@RequestBody EntiteDTO entiteDTO)
    {
        return new ResponseEntity<>(iHabilitation.persist(entiteDTO), HttpStatus.OK);
    }

    @PatchMapping("/update/entite/{id}/{societe}")
    public ResponseEntity <ResponseOutput <EntiteDTO> > update ( @PathVariable("id") Long id, @PathVariable("societe") Long societe, @RequestBody EntiteDTO entiteDTO)
    {
        EntiteID key = new EntiteID();
        key.setId(id);
        key.setSociete(societe);
        return  new ResponseEntity<>(iHabilitation.update(key, entiteDTO), HttpStatus.OK);
    }
    
    
    
    @GetMapping("/show/entite/societe/id/{id}")
    public ResponseEntity <ResponseOutput <EntiteDTO> > getEntitiesBySociete ( @PathVariable ("id") Long id)
    {
        return new ResponseEntity<>(iHabilitation.getEntitiesBySociete(id), HttpStatus.OK);
    }
    
    @GetMapping(value = {"/show/entite/all"})
    public ResponseEntity <ResponseOutput <EntiteDTO> > getAllEntities ( )
    {
        return new ResponseEntity<>(iHabilitation.getAllEntities(),HttpStatus.OK);
    }
    
    
    @DeleteMapping("/delete/entite/{id}/{societe}")
    public ResponseEntity <ResponseOutput <EntiteDTO> >  deleteEntite ( @PathVariable("id") Long id, @PathVariable("societe") Long societe)
    {
    EntiteID key = new EntiteID();
    key.setId(id);
    key.setSociete(societe);
    System.out.println("key  :"+key);
    
        return new ResponseEntity<>(iHabilitation.deleteEntite(key), HttpStatus.OK);
    }
    @GetMapping(value = {"/show/entiteById/id/{id}"})
    public ResponseEntity <ResponseOutput <EntiteDTO> > getEntitiesById ( @PathVariable(name = "id", required = true) Long id)
    {
        return new ResponseEntity<>(iHabilitation.getEntitiesById(id),HttpStatus.OK);
    }
}
