package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.action.Secured;
import controllers.action.SecuredAdmin;
import models.Pays;
import models.Role;
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
public class PaysController {

    @Inject
    FormFactory formFactory;
    /**
     * Envoyer en format json la ville
     * @param id
     * @return
     */
    public Result getPaysById(Long id){
        Pays pays= Pays.getPaysById(id);
        ObjectNode result = Json.newObject();
        if(pays==null){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Une erreur s'est produite. pays non trouvé.");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("pays", Json.toJson(pays));
        }
        return ok(result);
    }

    /**
     * Envoyer en format json la liste des villes
     * @return
     */
        public Result getPays(){
        List<Pays> pays= Pays.getPayss();
        ObjectNode result = Json.newObject();
        if(pays.size()==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Aucun role n'a été trouvé");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("pays", Json.toJson(pays));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result ajouterPays(){
        final Form<Pays> form =formFactory.form(Pays.class).bindFromRequest();
        ObjectNode result = Json.newObject();
        Long v= Pays.ajouter(form.get().getPays());
        if(v==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("pays", Json.toJson(Pays.getPaysById(v)));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result modifierPays(Long id){
        ObjectNode result = Json.newObject();
        final Form<Pays> form =formFactory.form(Pays.class).bindFromRequest();
        Pays pays= Pays.getPaysById(id);
        Boolean v=pays.modifier(form.get().getPays());
        if(v==false){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("pays", Json.toJson(pays));
        }
        return ok(result);
    }

    public Result supprimerPays(Long id){
        ObjectNode result = Json.newObject();
        Pays pays= Pays.getPaysById(id);
        pays.supprimer();
        result.put("result", "ok");
        result.put("code", "1000");
        result.set("pays", Json.toJson(pays));
        return ok(result);
    }
}
