package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.action.Secured;
import controllers.action.SecuredAdmin;
import models.Pays;
import models.Profil;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by brick on 31/10/2016.
 */
@Security.Authenticated(SecuredAdmin.class)
public class SituationController {

    @Inject
    FormFactory formFactory;
    /**
     * Envoyer en format json la ville
     * @param id
     * @return
     */
    public Result getProfilById(Long id){
        Profil profil= Profil.getProfilById(id);
        ObjectNode result = Json.newObject();
        if(profil==null){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Une erreur s'est produite. pays non trouvé.");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("profil", Json.toJson(profil));
        }
        return ok(result);
    }

    /**
     * Envoyer en format json la liste des villes
     * @return
     */
        public Result getProfils(){
        List<Profil> profils= Profil.getProfils();
        ObjectNode result = Json.newObject();
        if(profils.size()==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Aucun profil n'a été trouvé");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("profils", Json.toJson(profils));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result ajouterProfil(){
        final Form<Profil> form =formFactory.form(Profil.class).bindFromRequest();
        ObjectNode result = Json.newObject();
        Long v= Profil.ajouter(form.get().getProfil());
        if(v==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("profil", Json.toJson(Profil.getProfilById(v)));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result modifierProfil(Long id){
        ObjectNode result = Json.newObject();
        final Form<Profil> form =formFactory.form(Profil.class).bindFromRequest();
        Profil profil= Profil.getProfilById(id);
        Boolean v=profil.modifier(form.get().getProfil());
        if(v==false){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("profil", Json.toJson(profil));
        }
        return ok(result);
    }

    public Result supprimerProfil(Long id){
        ObjectNode result = Json.newObject();
        Profil profil= Profil.getProfilById(id);
        profil.supprimer();
        result.put("result", "ok");
        result.put("code", "1000");
        result.set("profil", Json.toJson(profil));
        return ok(result);
    }
}
