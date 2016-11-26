package models;

/**
 * Created by brick on 01/06/2016.
 */
import com.avaje.ebean.Model;
import play.data.format.Formats;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class SessionUser extends Model {
    @Id
    private Long id;

    private Boolean active;

    private String motDePasse;

    private int tentative;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    @ManyToOne
    private Membre membre;

    public SessionUser() {
        this.active=false;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.tentative=0;
    }

    public SessionUser(String motDePasse, Membre membre) {
        this.motDePasse = motDePasse;
        this.membre = membre;
        this.active=false;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.tentative=0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getTentative() {
        return tentative;
    }

    public void setTentative(int tentative) {
        this.tentative = tentative;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    /**
     * activer la session
     */
    public void activer(){
        this.active=true;
    }

    /**
     * incrementer les tentatives
     */
    public int incrementeTentative(){
        this.setTentative(this.getTentative()+1);
        this.update();
        return this.getTentative();
    }

    /**
     * creer une nouvelle session
     */
    public static SessionUser newSession(String motDePasse,Membre membre){
        SessionUser sess=new SessionUser(motDePasse,membre);
        sess.save();
        return sess;
    }

}
