package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Constraint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by julio on 25/06/2016.
 */
@Entity
public class Domaine extends Model {
    @Id
    private Long id;

    @Constraints.Required
    private String secteur;

    private boolean etat;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    @ManyToMany
    private List<Article> articles;

    @OneToMany(mappedBy = "domaine")
    private List<Membre> membres;

    public Domaine(){
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

    public Domaine(String domaine){
        this.secteur = domaine;
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

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }


    public List<Membre> getMembres() {
        return membres;
    }

    public void setMembres(List<Membre> membres) {
        this.membres = membres;
    }

    /**
     * Retourner la liste des villes qui ont ce code
     * @param secteur
     * @return
     */
    public static Domaine getDomaineByName(String secteur){
        return Ebean.find(Domaine.class).where().eq("secteur",secteur.toLowerCase()).findUnique();
    }

    /**
     * Methode pour retourner une ville connaissant son identifiant
     * @param id
     * @return
     */
    public static Domaine getDomaineById(Long id){
        return Domaine.find.byId(id);
    }

    /**
     * Methode pour verifier si la ville existe deja dans la base de données
     * methode
     * @return
     */
    public static Boolean verifierSiLeDomaineExiste(String secteur){
        if(Domaine.getDomaineByName(secteur)!=null)
            return true;
        return false;
    }

    /**
     * Methode permettant d'ajouter une nouvelle ville
     * @param secteur
     * @return
     */
    public static Long ajouter(String secteur){
        int ok=0;
        /**
         * si la ville n'existe pas dans la base de données on
         * ajoute et on retourne true, sinon on retourne false
         * pour dire que cette ville existe deja dans la base de données
         */
        if(!Domaine.verifierSiLeDomaineExiste(secteur)){
            Domaine v= new Domaine(secteur);
            v.save();
            return v.getId();
        }
        return new Long(ok);
    }

    /**
     * Modifier la ville en renseignant une nouvelle ville
     * @param secteur
     * @return
     */
    public Boolean modifier(String secteur){
        Boolean ok=false;
        if(!this.getSecteur().equals(secteur)){
            if(!Domaine.verifierSiLeDomaineExiste(secteur)){
                this.setSecteur(secteur);
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

    public static List<Domaine> getDomaines(){
        return Ebean.find(Domaine.class).findList();
    }

    public static List<Domaine> listeDomaines(){
        return Ebean.find(Domaine.class).where().eq("etat",true).findList();
    }

    public static Finder<Long, Domaine> find = new Finder<Long, Domaine>(Domaine.class);
}
