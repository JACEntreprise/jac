package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.action.Secured;
import controllers.action.SecuredAdmin;
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
public class RoleController {

    @Inject
    FormFactory formFactory;
    /**
     * Envoyer en format json la ville
     * @param id
     * @return
     */
    public Result getRoleById(Long id){
        Role role= Role.getRoleById(id);
        ObjectNode result = Json.newObject();
        if(role==null){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Une erreur s'est produite. role non trouvé.");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("role", Json.toJson(role));
        }
        return ok(result);
    }

    /**
     * Envoyer en format json la liste des villes
     * @return
     */
        public Result getRoles(){
        List<Role> roles= Role.getRoles();
        ObjectNode result = Json.newObject();
        if(roles.size()==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Aucun role n'a été trouvé");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("roles", Json.toJson(roles));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result ajouterRole(){
        final Form<Role> form =formFactory.form(Role.class).bindFromRequest();
        ObjectNode result = Json.newObject();
        Long v= Role.ajouter(form.get().getCode(),form.get().getRole());
        if(v==0){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("role", Json.toJson(Role.getRoleById(v)));
        }
        return ok(result);
    }

    /**
     * Action permettant d'ajouter une ville
     * @return
     */
    public Result modifierRole(Long id){
        ObjectNode result = Json.newObject();
        final Form<Role> form =formFactory.form(Role.class).bindFromRequest();
        Role vil= Role.getRoleById(id);
        Boolean v=vil.modifier(form.get().getCode(),form.get().getRole());
        if(v==false){
            result.put("result", "nok");
            result.put("code", "3004");
            result.put("message", "Erreur veuillez reessayer");
        }else{
            result.put("result", "ok");
            result.put("code", "1000");
            result.set("role", Json.toJson(vil));
        }
        return ok(result);
    }

    public Result supprimerRole(Long id){
        ObjectNode result = Json.newObject();
        Role vil= Role.getRoleById(id);
        vil.supprimer();
        result.put("result", "ok");
        result.put("code", "1000");
        result.set("role", Json.toJson(vil));
        return ok(result);
    }
}
