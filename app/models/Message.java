package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entite Message
 */

@Entity
public class Message extends Model {
    /**
     * Identifiant de message
     */
    @Id
    public long id;

    /**
     * le contenu du message
     */
    @Constraints.Required
    public String contenu;

    /**
     * message lu
     */
    public Boolean lu;

    /**
     * Relation entre Message et Membre(envoi)
     * plusieurs messages sont envoyés par un membre
     */
    @ManyToOne
    public Membre expediteur;

    /**
     * Relation entre Message et Membre(reception)
     * plusieurs messages sont reçus par un membre
     */
    @ManyToOne
    public Membre destinataire;

    /**
     * La date d'envoi du message
     */
    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date date;

    /**
     * Constructeur par defaut
     */
    public Message() {
        date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(date);
        try {
            date = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.lu=false;
    }

    public Message(String contenu, Membre expediteur, Membre destinataire) {
        this.contenu = contenu;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.date = new Date();
        this.lu=false;
        SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String nvd = dt.format(this.date);
        try {
            this.date = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * creer un nouveau message
     * @param
     */
    public static void creerNewMessage(String contenu, Membre expediteur, Membre destinataire){
        Message message= new Message(contenu,expediteur,destinataire);
        message.save();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Membre getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Membre expediteur) {
        this.expediteur = expediteur;
    }

    public Membre getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Membre destinataire) {
        this.destinataire = destinataire;
    }

    /**
     * finder permettant d'accedant aux donnees de l'entite
     */
    public static Finder<Long, Message> find = new Finder<Long,Message>(Message.class);
}
