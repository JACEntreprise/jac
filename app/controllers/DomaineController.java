package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.action.Secured;
import controllers.action.SecuredAdmin;
import models.Domaine;
import models.Pays;
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
public class DomaineController {

    @Inject
    FormFactory formFactory;
    /**
     * Envoyer en format json la ville
     * @param id
     * @return
     */
    public Result getDomaineById(Long id){
        Domaine domaine= Domaine.getDomaineById(id);
        ObjectNode result = Json.newObject();
        if(domaine==null){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Une erreur s'est produite. pays non trouvé.");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("domaine", Json.toJson(domaine));
        }
        return ok(result);
    }

    /**
     * Envoyer en format json la liste des villes
     * @return
     */
        public Result getDomaines(){
        List<Domaine> domaines= Domaine.getDomaines();
        ObjectNode result = Json.newObject();
        if(domaines.size()==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Aucun role n'a été trouvé");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("domaines", Json.toJson(domaines));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result ajouterDomaine(){
        final Form<Domaine> form =formFactory.form(Domaine.class).bindFromRequest();
        ObjectNode result = Json.newObject();
        Long v= Domaine.ajouter(form.get().getSecteur());
        if(v==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("domaine", Json.toJson(Domaine.getDomaineById(v)));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result modifierDomaine(Long id){
        ObjectNode result = Json.newObject();
        final Form<Domaine> form =formFactory.form(Domaine.class).bindFromRequest();
        Domaine domaine= Domaine.getDomaineById(id);
        Boolean v=domaine.modifier(form.get().getSecteur());
        if(v==false){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("domaine", Json.toJson(domaine));
        }
        return ok(result);
    }

    public Result supprimerDomaine(Long id){
        ObjectNode result = Json.newObject();
        Domaine domaine= Domaine.getDomaineById(id);
        domaine.supprimer();
        result.put("result", "ok");
        result.put("code", "1000");
        result.set("domaine", Json.toJson(domaine));
        return ok(result);
    }
}
