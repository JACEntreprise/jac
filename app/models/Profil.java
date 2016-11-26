package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by julio on 25/06/2016.
 */
@Entity
public class Profil extends Model {
    @Id
    private Long id;

    @Constraints.Required
    private String profil;

    private boolean etat;

    @OneToMany(mappedBy = "profil")
    private List<Membre> membres;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    public Profil(){
        this.etat = true;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public Profil(String profil){
        this.profil = profil;
        this.etat = true;
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

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public List<Membre> getMembres() {
        return membres;
    }

    public void setMembres(List<Membre> membres) {
        this.membres = membres;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     * Retourner la liste des villes qui ont ce code
     * @param profil
     * @return
     */
    public static Profil getProfilByName(String profil){
        return Ebean.find(Profil.class).where().eq("profil",profil.toLowerCase()).findUnique();
    }

    /**
     * Methode pour retourner une ville connaissant son identifiant
     * @param id
     * @return
     */
    public static Profil getProfilById(Long id){
        return Profil.find.byId(id);
    }

    /**
     * Methode pour verifier si la ville existe deja dans la base de données
     * methode
     * @return
     */
    public static Boolean verifierSiLeProfilExiste(String profil){
        if(Profil.getProfilByName(profil)!=null)
            return true;
        return false;
    }

    /**
     * Methode permettant d'ajouter une nouvelle ville
     * @param profil
     * @return
     */
    public static Long ajouter(String profil){
        int ok=0;
        /**
         * si la ville n'existe pas dans la base de données on
         * ajoute et on retourne true, sinon on retourne false
         * pour dire que cette ville existe deja dans la base de données
         */
        if(!Profil.verifierSiLeProfilExiste(profil)){
            Profil v= new Profil(profil);
            v.save();
            return v.getId();
        }
        return new Long(ok);
    }

    /**
     * Modifier la ville en renseignant une nouvelle ville
     * @param profil
     * @return
     */
    public Boolean modifier(String profil){
        Boolean ok=false;
        if(!this.getProfil().equals(profil)){
            if(!Profil.verifierSiLeProfilExiste(profil)){
                this.setProfil(profil);
                this.update();
                ok=true;
            }else{
                ok=false;
            }
        }
        return ok;
    }

    /**
     * Supprimer une ville en changeant son etat
     */
    public void supprimer(){
        this.setEtat(false);
        this.update();
    }

    /**
     * Reactiver une ville qui a été supprimé
     */
    public void restaurer(){
        this.setEtat(true);
        this.update();
    }

    public static List<Profil> getProfils(){
        return Ebean.find(Profil.class).findList();
    }
    public static List<Profil> listeProfils(){
        return Ebean.find(Profil.class).where().eq("etat",true).findList();
    }

    public static Finder<Long, Profil> find = new Finder<Long, Profil>(Profil.class);
}
