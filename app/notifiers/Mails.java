package notifiers;

import models.Membre;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

/**
 * Created by brick on 12/11/2016.
 */
public class Mails {
    MailerClient mailerClient;
    private Membre membre;
    private String titre;
    private String contenuTexte;
    private String contenuHtml;

    public Mails(MailerClient mailerClient, Membre membre, String titre, String contenuTexte, String contenuHtml) {
        this.mailerClient = mailerClient;
        this.membre = membre;
        this.titre = titre;
        this.contenuTexte = contenuTexte;
        this.contenuHtml = contenuHtml;
    }

    public Membre getUtilisateur() {
        return membre;
    }

    public void setUtilisateur(Membre utilisateur) {
        this.membre = utilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenuTexte() {
        return contenuTexte;
    }

    public void setContenuTexte(String contenuTexte) {
        this.contenuTexte = contenuTexte;
    }

    public String getContenuHtml() {
        return contenuHtml;
    }

    public void setContenuHtml(String contenuHtml) {
        this.contenuHtml = contenuHtml;
    }

    public void sendEmail() {
        Email email = new Email()
                .setSubject(this.getTitre())
                .setFrom("jacentrprise@gmail.com")
                .addTo(this.membre.getEmail())
                .setBodyText(this.getContenuTexte())
                .setBodyHtml(this.getContenuHtml());
        mailerClient.send(email);
    }
}
