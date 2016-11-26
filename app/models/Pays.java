package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import org.apache.commons.lang3.StringUtils;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by julio on 25/06/2016.
 */
@Entity
public class Pays extends Model {
    @Id
    private Long id;

    @Constraints.Required
    private String pays;

    private boolean etat;

    @OneToMany(mappedBy = "pays")
    private List<Membre> membres;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    public Pays(){
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

    public Pays(String pays){
        this.pays = pays.toLowerCase();
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

    public String getPays() {
        return StringUtils.capitalize(pays.toLowerCase());
    }

    public void setPays(String pays) {
        this.pays = pays.toLowerCase();
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
     * @param pays
     * @return
     */
    public static Pays getPaysByName(String pays){
        return Ebean.find(Pays.class).where().eq("pays",pays.toLowerCase()).findUnique();
    }

    /**
     * Methode pour retourner une ville connaissant son identifiant
     * @param id
     * @return
     */
    public static Pays getPaysById(Long id){
        return Pays.find.byId(id);
    }

    /**
     * Methode pour verifier si la ville existe deja dans la base de données
     * methode
     * @return
     */
    public static Boolean verifierSiLePaysExiste(String pays){
        if(Pays.getPaysByName(pays)!=null)
            return true;
        return false;
    }

    /**
     * Methode permettant d'ajouter une nouvelle ville
     * @param pays
     * @return
     */
    public static Long ajouter(String pays){
        int ok=0;
        /**
         * si la ville n'existe pas dans la base de données on
         * ajoute et on retourne true, sinon on retourne false
         * pour dire que cette ville existe deja dans la base de données
         */
        if(!Pays.verifierSiLePaysExiste(pays)){
            Pays v= new Pays(pays);
            v.save();
            return v.getId();
        }
        return new Long(ok);
    }

    /**
     * Modifier la ville en renseignant une nouvelle ville
     * @param pays
     * @return
     */
    public Boolean modifier(String pays){
        Boolean ok=false;
        if(!this.getPays().equals(pays)){
            if(!Pays.verifierSiLePaysExiste(pays)){
                this.setPays(pays);
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

    public static List<Pays> getPayss(){
        return Ebean.find(Pays.class).findList();
    }

    public static List<Pays> listePays(){
        return Ebean.find(Pays.class).where().eq("etat",true).findList();
    }

    public static Finder<Long, Pays> find = new Finder<Long, Pays>(Pays.class);
}
