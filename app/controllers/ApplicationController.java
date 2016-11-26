package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.action.Secured;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import tools.Session;
import views.html.accueil;
import views.html.completerInfo;
import views.html.attenteConfirmation;
import views.html.infoProfil;
import views.html.inscriptionPhotoProfil;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

/**
 * Created by brick on 21/10/2016.
 */
@Security.Authenticated(Secured.class)
public class ApplicationController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * Attente confirmation mail
     * @return
     */
    public Result attente() {
        Membre membre= Membre.getMembreByEmail(session("email"));
        if(membre.isEtat()==true){
            return redirect(routes.ApplicationController.completerInfo());
        }
        Session sess=new Session(session("email"),session("nomProfil"),session("user"),session("type"));
        return ok(attenteConfirmation.render(sess));
    }

    /**
     * Renseigner ses informations personnelles
     * @return
     */
    public Result completerInfo() {
        Membre membre= Membre.getMembreByEmail(session("email"));
        if(membre==null){
            return redirect(routes.HomeController.index());
        }
        if(membre.isEtat()==false){
            return redirect(routes.ApplicationController.attente());
        }
        if(session("type").equals("particulier")){
            if(membre.getParticulier().getAnneeDeNaissance()!=null){
                return redirect(routes.ApplicationController.infoProfil());
            }
        }
        if(membre.isStatut()==true){
            return redirect(routes.ApplicationController.completerImageProfil());
        }
        Session sess=new Session(session("email"),session("nomProfil"),session("user"),session("type"));
        return ok(completerInfo.render(sess));
    }

    /**
     * valider les informations personnelles
     * @return
     */
    public Result actionCompleterInfo(){
        Membre membre=Membre.getMembreByEmail(session("email"));
        final Form<Membre> formMembre =formFactory.form(Membre.class).bindFromRequest();
        if(session("type").equals("particulier")){
            final Form<Particulier> formParticulier =formFactory.form(Particulier.class).bindFromRequest();
            membre.completInfoParticulier(formMembre.get().getAdresse(),formMembre.get().getTelephone(),formMembre.get().getSiteweb(),formParticulier.get().getAnneeDeNaissance(),formParticulier.get().getLieuDeNaissance());
        }else{
            membre.completInfoEntreprise(formMembre.get().getAdresse(),formMembre.get().getTelephone(),formMembre.get().getSiteweb());
        }
        ObjectNode result = Json.newObject();
        result.put("result","ok");
        result.put("code", "1000");
        result.put("message", "Creation avec succes");
        return ok(result);
    }

    /**
     * Completer son profil par sa situation son secteur son pays etc.
     * @return
     */
    public Result infoProfil() {
        Membre membre= Membre.getMembreByEmail(session("email"));
        if(membre.isEtat()==false){
            return redirect(routes.ApplicationController.attente());
        }
        if(session("type").equals("particulier")){
            if(membre.getParticulier().getAnneeDeNaissance()==null){
                return redirect(routes.ApplicationController.completerInfo());
            }
        }
        if(membre.isStatut()==true){
            return redirect(routes.ApplicationController.completerImageProfil());
        }
        Session sess=new Session(session("email"),session("nomProfil"),session("user"),session("type"));
        return ok(infoProfil.render(sess));
    }

    /**
     * action pour ajouter les informations du profil
     */
    public Result actionInfoProfil(){
        Membre membre=Membre.getMembreByEmail(session("email"));
        final Form<Pays> formPays =formFactory.form(Pays.class).bindFromRequest();
        final Form<Titre> formTitre =formFactory.form(Titre.class).bindFromRequest();
        final Form<Profil> formProfil =formFactory.form(Profil.class).bindFromRequest();
        final Form<Domaine> formDomaine =formFactory.form(Domaine.class).bindFromRequest();
        Pays pays=Pays.getPaysByName(formPays.get().getPays());
        Profil situation=Profil.getProfilByName(formProfil.get().getProfil());
        Domaine secteur=Domaine.getDomaineByName(formDomaine.get().getSecteur());
        membre.infoProfil(pays,secteur,situation,formTitre.get().getTitre());
        ObjectNode result = Json.newObject();
        result.put("result","ok");
        result.put("code", "1000");
        result.put("message", "Creation avec succes");
        return ok(result);
    }

    /**
     * Ajouter une image de profil
     * @return
     */
    public Result completerImageProfil() {
        Membre membre= Membre.getMembreByEmail(session("email"));
        if(membre.isEtat()==false){
            return redirect(routes.ApplicationController.attente());
        }
        if(session("type")=="particulier"){
            if(membre.getParticulier().getAnneeDeNaissance()==null){
                return redirect(routes.ApplicationController.completerInfo());
            }
        }
        if(membre.isStatut()==false){
            return redirect(routes.ApplicationController.infoProfil());
        }
        Session sess=new Session(session("email"),session("nomProfil"),session("user"),session("type"));
        return ok(inscriptionPhotoProfil.render(sess));
    }

    /**
     * enregistrer image webcam
     */
    public Result enregisterWebcam() throws IOException {
        String email = session("email");
        ObjectNode result = Json.newObject();
        if(email != null) {
            Membre membre=Membre.getMembreByEmail(email);
            if(membre!=null){
                if(membre.isEtat()==true){
                    final Form<WebcamForm> form =formFactory.form(WebcamForm.class).bindFromRequest();
                    String img_data=form.get().getImage().replace("data:image/png;base64,","");
                    byte[] imageByte = Base64.getDecoder().decode(img_data);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                    BufferedImage image = ImageIO.read(bis);
                    bis.close();
                    String nom = Image.genererNom(new Date());
                    String path=new File("").getAbsolutePath();
                    nom = String.format("%s.png",nom);
                    File outputfile = new File("public/images/profil/"+nom);
                    Image.imageProfil(nom,path+"/public/images/profil/"+nom,membre);
                    ImageIO.write(image, "png", outputfile);
                    result.put("result","ok");
                    result.put("code", "1000");
                    return ok(result);
                }
            }
        }
        result.put("result","nok");
        result.put("code", "304");
        return ok(result);
    }

    public Result ajouterImageProfil(){
        Membre membre= Membre.getMembreByEmail(session("email"));
        ObjectNode result = Json.newObject();
        if(membre!=null){
            if(membre.isEtat()==true){

                Http.MultipartFormData<File> body = request().body().asMultipartFormData();
                /**
                 * On recupere le fichier par son nom
                 */
                Http.MultipartFormData.FilePart<File> picture = body.getFile("image");
                /**
                 * On verifie si e fichier recupéré est non vide
                 */
                if (picture != null) {

                    /**
                     * On recupere le nom de ce fichier
                     */
                    String fileName = picture.getFilename();
                    /**
                     * son type de contenu
                     */
                    String contentType =picture.getContentType();
                    /**
                     * On verifie si le ichier est une image
                     */
                    if(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg") || contentType.equals("image/gif")){
                        File file = picture.getFile();
                        String nom = picture.getFilename();
                        //extension du fichier
                        String ext = Image.getFileExtension(nom);
                        String path=new File("").getAbsolutePath();
                        //on génère un nom unique pour l'image
                        nom = Image.genererNom(new Date());

                        nom = String.format("%s.%s",nom,ext);

                        file.renameTo(new File("public/images/profil",nom));
                        Image.imageProfil(nom,path+"/public/images/profil/"+nom,membre);

                        result.put("result", "ok");
                        result.put("code", "1000");
                        result.put("message", "creer avec succes");
                        return ok(result);
                    }
                }
            }
        }
        result.put("result", "nok");
        result.put("code", "304");
        result.put("erreur", "image svp");

        return ok(result);
    }

    /**
     * Pae d'accueil apres validation de linscription
     * @return
     */
    public Result accueil() {
        Membre membre= Membre.getMembreByEmail(session("email"));
        if(membre.isEtat()==false){
            return redirect(routes.ApplicationController.attente());
        }
        if(session("type")=="particulier"){
            if(membre.getParticulier().getAnneeDeNaissance()==null){
                return redirect(routes.ApplicationController.completerInfo());
            }
        }
        if(membre.isStatut()==false){
            return redirect(routes.ApplicationController.infoProfil());
        }
        Session sess=new Session(session("email"),session("nomProfil"),session("user"),session("type"));
        return ok(accueil.render(sess));
    }

}
