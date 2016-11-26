package controllers;

import controllers.action.Secured;
import controllers.action.SecuredAdmin;
import play.mvc.Result;
import play.mvc.Security;
import play.routing.JavaScriptReverseRouter;
import views.html.admin.*;
import views.html.admin.accueil;

import static play.mvc.Results.ok;
import views.html.admin.roleViews.role;
import views.html.admin.paysViews.pays;
import views.html.admin.secteurViews.secteur;
import views.html.admin.situationViews.situation;

/**
 * Created by brick on 31/10/2016.
 */
@Security.Authenticated(SecuredAdmin.class)
public class AdminController {

    public Result accueil() {
        return ok(accueil.render());
    }

    /**
     * page d'accueil des roless pour afficher les vues et faire du crud
     * @return
     */
    public Result role() {
        return ok( role.render());
    }

    /**
     * page d'accueil des situations pour afficher les vues et faire du crud
     * @return
     */
    public Result situation() {
        return ok( situation.render());
    }

    /**
     * page d'accueil des domaines pour afficher les vues et faire du crud
     * @return
     */
    public Result secteur() {
        return ok( secteur.render());
    }

    /**
     * page d'accueil des pays pour afficher les vues et faire du crud
     * @return
     */
    public Result pays() {
        return ok( pays.render());
    }

    // Methode contenant les URL Ajax pour ce controller
    public Result jsRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.RoleController.getRoleById(),
                        routes.javascript.RoleController.getRoles(),
                        routes.javascript.RoleController.ajouterRole(),
                        routes.javascript.RoleController.supprimerRole(),
                        routes.javascript.RoleController.modifierRole(),

                        routes.javascript.PaysController.getPaysById(),
                        routes.javascript.PaysController.getPays(),
                        routes.javascript.PaysController.ajouterPays(),
                        routes.javascript.PaysController.supprimerPays(),
                        routes.javascript.PaysController.modifierPays(),

                        routes.javascript.DomaineController.getDomaineById(),
                        routes.javascript.DomaineController.getDomaines(),
                        routes.javascript.DomaineController.ajouterDomaine(),
                        routes.javascript.DomaineController.supprimerDomaine(),
                        routes.javascript.DomaineController.modifierDomaine(),

                        routes.javascript.SituationController.getProfilById(),
                        routes.javascript.SituationController.getProfils(),
                        routes.javascript.SituationController.ajouterProfil(),
                        routes.javascript.SituationController.supprimerProfil(),
                        routes.javascript.SituationController.modifierProfil()
                )
        ).as("text/javascript");
    }
}
