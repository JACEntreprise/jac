package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Creation de l'Entité Membre
 */
@Entity
public class Image extends Model {

    /**
     * id de l'entité
     */
    @Id
    private Long id;

    /**
     * nom de l'image
     */
    @Constraints.Required
    private String nom;

    /**
     * chemin vers l'image
     */
    @Constraints.Required
    private String chemin;

    private Boolean profil;

    private Boolean etat;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    /**
     * Relation entre Membe et Image
     * Un membre peut avoir plusieurs photo de profil
     */
    @ManyToOne
    private Membre membre;

    /**
     * Constructeur par defaut
     */
    public Image() {
        this.profil=true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Boolean getProfil() {
        return profil;
    }

    public void setProfil(Boolean profil) {
        this.profil = profil;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public static String  genererNom(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
        String format1 = dt1.format(date);

        return format1;
    }

    public Image(String nom, String chemin, Membre membre) {
        this.etat=true;
        this.nom = nom;
        this.chemin = chemin;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.membre=membre;
        this.profil=true;
    }

    /**
     * Ajouter image de profil
     */

    public static void imageProfil(String nom, String chemin, Membre membre){
        Image image=membre.imageProfil();
        if(image!=null){
            image.setProfil(false);
            image.update();
        }
        Image img=new Image(nom,chemin,membre);
        img.save();

    }

    public static String getFileExtension(String NomFichier) {
        File tmpFichier = new File(NomFichier);
        tmpFichier.getName();
        int posPoint = tmpFichier.getName().lastIndexOf('.');
        if (0 < posPoint && posPoint <= tmpFichier.getName().length() - 2 ) {
            return tmpFichier.getName().substring(posPoint + 1);
        }
        return "";
    }

    /**
     * finder permettant d'accedant aux donnees de l'entite
     */
    public static Finder<String, Image> find = new Finder<String, Image>(Image.class);
}
