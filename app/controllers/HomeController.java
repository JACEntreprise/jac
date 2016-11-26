package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.mindrot.jbcrypt.BCrypt;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.mailer.MailerClient;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import tools.SendMail;
import tools.Session;
import views.html.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by brick on 21/10/2016.
 */
public class HomeController extends Controller {
    @Inject
    MailerClient mailerClient;
    @Inject
    FormFactory formFactory;

    /**
     * page d'accueil du ite
     * @return
     */
    public Result index() {
        Role.ajouterRoleUserAdmin();
        return ok(index.render("yes"));
    }


    public Result motDePasseOublie() {
        return ok(mot_de_passe_oublie.render());
    }

    public Result connexionSession() {
        String email=session("email");
        if(email!=null){
            return redirect(routes.ApplicationController.accueil());
        }
        String sess=session("session");
        if(sess==null){
            return redirect(routes.HomeController.index());
        }
        Membre membre=Membre.getMembreByEmail(sess);
        if(membre.lastSession().getTentative()>=3){
            membre.lastSession().activer();
            return redirect(routes.HomeController.index());
        }
        return ok(connexion_compte.render(membre.imageProfil().getNom(),membre.getNomProfil()));
    }

    public Result connexionAdmin() {
        if(session("admin")!=null){
            return redirect(routes.AdminController.accueil());
        }
        Membre.addAmin();
        return ok(connexion_admin.render());
    }

    public Result actionConnexionAdmin() {
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        String motDePasse = json.findPath("motDePasse").textValue();
        String pseudo = json.findPath("pseudo").textValue();
        Role role=Role.getRoleByCode("Admin");
        Membre membre=role.getMembres().get(0);
        if(membre.getPseudo().equals(pseudo) && BCrypt.hashpw(motDePasse, membre.getSalt()).equals(membre.getMotDePasse())){
            Session.deleteSession();
            session("admin","admin");
            result.put("result","ok");
            result.put("code", "1000");
            result.put("message", "Creation avec succes");
            return ok(result);
        }
        result.put("result","nok");
        result.put("code", "304");
        result.put("message", "pseudo ou mot de passe incorrect");
        return ok(result);
    }

    public Result inscriptionParticulier(){
        ObjectNode result = Json.newObject();
        /**
         * récupération des données du formulaire
         */
        final Form<Particulier> formParticulier =formFactory.form(Particulier.class).bindFromRequest();
        final Form<Membre> formMembre =formFactory.form(Membre.class).bindFromRequest();
        Membre membre=Membre.inscrireParticulier(formMembre.get().getEmail(),formMembre.get().getMotDePasse(),formParticulier.get().getNom(),formParticulier.get().getPrenom());
        if(membre==null){
            result.put("result","nok");
            result.put("code", "304");
            result.put("message", "Cet email est deja utilisé");
            return ok(result);
        }
        Session sess=new Session(membre.getEmail(),membre.getNomProfil(),membre.getRole().getCode(),"particulier");
        sess.creerSession();
        SendMail.confirmationMail(membre,mailerClient);
        result.put("result","ok");
        result.put("code", "1000");
        result.put("message", "Creation avec succes");
        return ok(result);
    }

    public Result inscriptionEntreprise(){
        ObjectNode result = Json.newObject();
        /**
         * récupération des données du formulaire
         */
        final Form<Entreprise> formEntreprise =formFactory.form(Entreprise.class).bindFromRequest();
        final Form<Membre> formMembre =formFactory.form(Membre.class).bindFromRequest();
        Membre membre=Membre.inscrireEntreprise(formMembre.get().getEmail(),formMembre.get().getMotDePasse(),formEntreprise.get().getRaisonSocial());
        if(membre==null){
            result.put("result","nok");
            result.put("code", "304");
            result.put("message", "Cet email est deja utilisé");
            return ok(result);
        }
        Session sess=new Session(membre.getEmail(),membre.getNomProfil(),membre.getRole().getCode(),"entreprise");
        sess.creerSession();
        SendMail.confirmationMail(membre,mailerClient);
        result.put("result","ok");
        result.put("code", "1000");
        result.put("message", "Creation avec succes");
        return ok(result);
    }

    /**
     * lien confirmation d'inscription
     * @param code
     * @return
     */
    public Result confirmerInscription(String code){
        List<Membre> membres=Membre.getMembres();
        for(Membre membre : membres){
            if(BCrypt.hashpw(String.valueOf(membre.getId()), membre.getSalt()).equals(code)){
                if(membre.isEtat()==true){
                    String email=session("email");
                    if(email==null){
                        return redirect(routes.HomeController.index());
                    }
                }
                membre.activer();
                String type;
                if(membre.getParticulier()==null){
                    type="entreprise";
                }else{
                    type="particulier";
                }
                Session sess=new Session(membre.getEmail(),membre.getNomProfil(),membre.getRole().getCode(),type);
                sess.creerSession();
                return redirect(routes.ApplicationController.completerInfo());
            }
        }
        return redirect(routes.ApplicationController.attente());
    }

    public Result connexionUser(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        String email = json.findPath("email").textValue();
        String motDePasse = json.findPath("motDePasse").textValue();
        if(email==null || motDePasse==null){
            result.put("result","nok");
            result.put("code", "304");
            result.put("message", "Email et mot de passe obligatoire");
            return ok(result);
        }
        Membre membre=Membre.getMembreByEmail(email);
        if(membre==null){
            result.put("result","nok");
            result.put("code", "304");
            result.put("message", "Email non valide");
            return ok(result);
        }
        if(!BCrypt.hashpw(motDePasse, membre.getSalt()).equals(membre.getMotDePasse())){
            result.put("result","nok");
            result.put("code", "304");
            result.put("message", "Mot de passe incorrecte");
            return ok(result);
        }
        String password=SendMail.envoieMotDePasse("Nouvelle connexion",membre,mailerClient);
        membre.activerSession();
        SessionUser sess=SessionUser.newSession(BCrypt.hashpw(password, membre.getSalt()),membre);

        session("session",membre.getEmail());
        result.put("result","ok");
        result.put("code", "1000");
        result.put("message", "Creation avec succes");
        return ok(result);
    }

    public Result actionConnexionSession(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        String motDePasse = json.findPath("motDePasse").textValue();
        String email=session("session");
        Membre membre=Membre.getMembreByEmail(email);
        if(!BCrypt.hashpw(motDePasse, membre.getSalt()).equals(membre.lastSession().getMotDePasse())){
            int tent=membre.lastSession().incrementeTentative();
            if(tent>=3){
                Session.deleteSession();
            }
            result.put("result","nok");
            result.put("tentative",tent);
            result.put("code", "304");
            result.put("message", "Mot de passe incorrecte");
            return ok(result);
        }
        membre.activerSession();
        String type;
        if(membre.getParticulier()==null){
            type="entreprise";
        }else{
            type="particulier";
        }
        Session sess=new Session(membre.getEmail(),membre.getNomProfil(),membre.getRole().getCode(),type);
        sess.creerSession();
        result.put("result","ok");
        result.put("code", "1000");
        result.put("message", "Connexion avec succes");
        return ok(result);
    }
}
