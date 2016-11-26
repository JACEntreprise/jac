package tools;

import com.google.inject.Inject;
import controllers.routes;
import models.Membre;
import notifiers.Mails;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.mailer.MailerClient;

/**
 * Created by brick on 22/11/2016.
 */
public class SendMail {

    public static void confirmationMail(Membre membre,MailerClient mailerClient){
        /**
         * parametre du mail
         */
        String titre="Mail de confirmation Reseau Professionel";
        String contenuTexte="Probleme de connexion l'envoie n'a pas pu se derouler correctement";
        String lien= routes.HomeController.confirmerInscription(BCrypt.hashpw(String.valueOf(membre.getId()), membre.getSalt())).absoluteURL(false,"localhost:9000");
        String contenuHtml="<html>" +
                                "<body>" +
                                    "<h2>Bonjour " + membre.getNomProfil() +",</h2>" +
                                    "<p>Pour finaliser votre inscription, nous n'avons plus qu'à valider l'adresse e-mail de votre compte .</p>" +
                                    "<p><a href='"+lien+"'>Cliquer ici</a></p>" +
                                "</body>" +
                            "</html>"
        ;
        Mails mail=new Mails(mailerClient,membre,titre,contenuTexte,contenuHtml);
        mail.sendEmail();
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String envoieMotDePasse(String titre, Membre membre,MailerClient mailerClient){
        String motDePasse=SendMail.randomAlphaNumeric(18);
        String contenuTexte="Probleme de connexion l'envoie n'a pas pu se derouler correctement";
        String contenuHtml="<html>" +
                "<body>" +
                "<h2>Bonjour " + membre.getNomProfil() +",</h2>" +
                "<p>Votre nouvel mot de passe:</p>" + motDePasse +
                "</body>" +
                "</html>"
                ;
        Mails mail=new Mails(mailerClient,membre,titre,contenuTexte,contenuHtml);
        mail.sendEmail();
        return motDePasse;
    }
}
