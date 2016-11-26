package controllers;

import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import views.html.admin.accueil;
import views.html.admin.paysViews.pays;
import views.html.admin.roleViews.role;
import views.html.admin.secteurViews.secteur;
import views.html.admin.situationViews.situation;

import static play.mvc.Results.ok;

/**
 * Created by brick on 31/10/2016.
 */
public class MembreController {
    // Methode contenant les URL Ajax pour ce controller
    public Result jsRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.HomeController.inscriptionParticulier(),
                        routes.javascript.HomeController.index(),
                        routes.javascript.HomeController.inscriptionEntreprise(),
                        routes.javascript.HomeController.connexionSession(),
                        routes.javascript.HomeController.actionConnexionSession(),
                        routes.javascript.HomeController.actionConnexionAdmin(),
                        routes.javascript.HomeController.connexionUser(),
                        routes.javascript.ApplicationController.attente(),
                        routes.javascript.ApplicationController.actionCompleterInfo(),
                        routes.javascript.ApplicationController.infoProfil(),
                        routes.javascript.ApplicationController.completerImageProfil(),
                        routes.javascript.ApplicationController.enregisterWebcam(),
                        routes.javascript.ApplicationController.actionInfoProfil(),
                        routes.javascript.ApplicationController.ajouterImageProfil(),
                        routes.javascript.AdminController.accueil(),
                        routes.javascript.ApplicationController.accueil()
                )
        ).as("text/javascript");
    }
}
