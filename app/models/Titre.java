package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by julio on 25/06/2016.
 */
@Entity
public class Titre extends Model {
    @Id
    private Long id;

    @Constraints.Required
    private String titre;

    private boolean etat;

    private boolean actif;

    @ManyToOne
    private Membre membre;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    public Titre(){
        etat = true;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Titre(String titre){
        this.titre = titre;
        this.etat = true;
        this.actif=true;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    public static Titre ajouter(String titre){
        Titre t=new Titre(titre);
        t.save();
        return t;
    }


}
