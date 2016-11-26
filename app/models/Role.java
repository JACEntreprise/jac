package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
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
 * Created by brick on 02/11/2016.
 */
@Entity
public class Role extends Model {
    @Id
    private Long id;

    @Constraints.Required
    private String role;

    @Constraints.Required
    private String code;

    private Boolean etat;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<Membre> membres;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;

    public Role() {
        this.etat=true;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Role(String role, String code) {
        this.etat=true;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.role = role.toLowerCase();
        this.code = code.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return StringUtils.capitalize(role.toLowerCase());
    }

    public void setRole(String role) {
        this.role = role.toLowerCase();
    }

    public String getCode() {
        return StringUtils.capitalize(code.toLowerCase());
    }

    public void setCode(String code) {
        this.code = code.toLowerCase();
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @JsonIgnore
    public List<Membre> getMembres() {
        return membres;
    }

    public void setUtilisateurs(List<Membre> membres) {
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
     * @param code
     * @return
     */
    public static Role getRoleByCode(String code){
        return Ebean.find(Role.class).where().eq("code",code.toLowerCase()).findUnique();
    }

    /**
     * Methode pour retourner une ville connaissant son identifiant
     * @param id
     * @return
     */
    public static Role getRoleById(Long id){
        return Role.find.byId(id);
    }

    /**
     * Methode pour verifier si la ville existe deja dans la base de données
     * methode
     * @return
     */
    public static Boolean verifierSiLeRoleExiste(String code){
        if(Role.getRoleByCode(code)!=null)
            return true;
        return false;
    }

    /**
     * Methode permettant d'ajouter une nouvelle ville
     * @param code
     * @param role
     * @return
     */
    public static Long ajouter(String code, String role){
        int ok=0;
        /**
         * si la ville n'existe pas dans la base de données on
         * ajoute et on retourne true, sinon on retourne false
         * pour dire que cette ville existe deja dans la base de données
         */
        if(!Role.verifierSiLeRoleExiste(code)){
            Role v= new Role(role,code);
            v.save();
            return v.getId();
        }
        return new Long(ok);
    }

    /**
     * ajouter un role user s'il n'existe pas
     */
    public static void ajouterRoleUserAdmin(){
        if(!Role.verifierSiLeRoleExiste("Admin")){
            Long role=Role.ajouter("Admin","Administrateur");
        }
        if(!Role.verifierSiLeRoleExiste("User")){
            Long role=Role.ajouter("User","Utilisateur");
        }
    }

    /**
     * Modifier la ville en renseignant une nouvelle ville
     * @param code
     * @param role
     * @return
     */
    public Boolean modifier(String code,String role){
        Boolean ok=false;
        if(!this.getCode().equals(code)){
            if(!Role.verifierSiLeRoleExiste(code)){
                this.setCode(code);
                this.setRole(role);
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

    public static List<Role> getRoles(){
        return Ebean.find(Role.class).findList();
    }

    public static Finder<Long, Role> find = new Finder<Long, Role>(Role.class);
}
