package tools;

import org.apache.commons.lang3.StringUtils;

import static play.mvc.Controller.session;

/**
 * Created by brick on 22/11/2016.
 */
public class Session {
    private String email;
    private String initialNom;
    private String nomProfil;
    private String role;
    private String type;

    public Session(String email, String nomProfil, String role,String type) {
        this.email = email;
        this.nomProfil = nomProfil;
        String[] tab=nomProfil.split(" ");
        this.initialNom= StringUtils.capitalize(tab[0]).substring(0,1);
        if(tab.length>1){
            this.initialNom+=StringUtils.capitalize(tab[tab.length-1]).substring(0,1);
        }
        this.role=role;
        this.type=type;
    }

    public void creerSession(){
        session("email", this.getEmail());
        session("initialNom", this.getInitialNom());
        session("nomProfil", this.getNomProfil());
        session("type", this.getType());
        if(role.equals("Admin")){
            session("admin", this.getRole());
        }else if(role.equals("User")){
            session("user", this.getRole());
        }
    }

    public static String nomInitial(String nom ){
        String[] tab=nom.split(" ");
        String ini=StringUtils.capitalize(tab[0]).substring(0,1);
        if(tab.length>1){
            ini+=StringUtils.capitalize(tab[tab.length-1]).substring(0,1);
        }
        return ini;
    }

    public static void deleteSession(){
        session().clear();
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInitialNom() {
        return initialNom;
    }

    public void setInitialNom(String initialNom) {
        this.initialNom = initialNom;
    }

    public String getNomProfil() {
        return nomProfil;
    }

    public void setNomProfil(String nomProfil) {
        this.nomProfil = nomProfil;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
