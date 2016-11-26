package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Query;
import javax.xml.transform.Result;

import com.avaje.ebean.*;
import org.jetbrains.annotations.Nullable;
import org.mindrot.jbcrypt.BCrypt;
import play.data.format.Formats;
import play.data.validation.*;
import tools.Utils;


/**
 * Creation de l'Entité Membre
 */
@Entity
public class Membre extends Model {

    /**
     * id de l'entité
     */
    @Id
    protected Long id;

    /**
     * Email d'un membre qui est obligatoire
     */
    protected String email;

    /**
     * Mot de passe d'un membre qui est obligatoire
     */
    protected String motDePasse;

    /**
     * adresse du membre qui est obligatoire
     */
    @Lob
    protected String adresse;

    /**
     * Numéro de téléphone
     */
    protected String telephone;

    /**
     * Pseudo Admin
     */
    protected String pseudo;

    /**
     * Site web s'il y'en a
     */
    protected String siteweb;

    /**
        * Etat du membre
    */
    protected boolean etat;

    protected boolean statut;

    /**
     * cle pour hachage mot de passe
    */
    protected String salt;

    /**
     * la date de création du membre
     */
    protected Date dateCreation;

    /**
     * Relation d'héritage entre Membe et Particulier
     */
    @OneToOne
    private Particulier particulier;

    /**
     * Relation d'héritage entre Membe et Entreprise
     */
    @OneToOne
    private Entreprise entreprise;

    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dateInscription;


    /**
     * Relation entre Membe et Message dans un sens(envoi)
     * Un membre peut envoyer plusieurs messages
     */
    @Column(nullable = true)
    @OneToMany(mappedBy = "expediteur")
    private List<Message> messagesEpediteurs;

    /**
     * Relation entre Membe et Message dans l'autre sens(reception)
     * Un membre peut recevoir plusieurs messages
     */
    @Column(nullable = true)
    @OneToMany(mappedBy = "destinataire")
    private List<Message> messagesDestinataires;

    @OneToMany(mappedBy = "membre")
    private List<SessionUser> sessions;

    /**
     * Relation entre Membe et Image
     * Un membre peut avoir plusieurs photo de profil
     */
    @OneToMany(mappedBy = "membre")
    private List<Image> images;

    /**
     * Relation entre Membe et Groupe(appartenance)
     * Plusieurs peuvent appartenir à un Groupe
     */
    @ManyToMany
    private List<Groupe> groupeAppartenances;

    /**
     * Relation de création entre Membe et Groupe
     * Un membre peut creer plusieurs Groupe
     */
    @OneToMany(mappedBy = "createur")
    private List<Groupe> groupes;

    @ManyToOne
    private Role role;

    /**
     * La liste d'amis demandés par ce membre
     */
    @OneToMany(mappedBy = "membreSource")
    private List<Amitie> amities;

    /**
     * La liste d'amis qui ont demandé une relation d'amitie à ce membre
     */
    @OneToMany(mappedBy = "membreCible")
    private List<Amitie> demandeAmities;

    /**
     * Relation entre Membre et Publication
     * Un membre peut faire plusieurs publications
     */
    @OneToMany(mappedBy = "membre")
    private List<Publication> publications;
    /**
     * Concernant le profil: relation entre membre et compétence
     * Un membre a plusieurs compétences, plusieurs membres peuvent partager une même compétence
     */
    @ManyToMany
    private List<Competence> competences;

    /**
     * Concernant toujours le profil: relation entre membre et expériences
     * Un membre peut avoir plusieurs expériences, plusieurs membre peuvent avoir vécus les mêmes expériences
     */
    @OneToMany(mappedBy = "membre")
    private List<Experience> experiences;

    /**
     * Relation entre Membre et Commentaire
     * Un membre peut faire plusieurs commentaires
     */
    @OneToMany(mappedBy = "membre")
    private List<Commentaire> commentaires;

    /**
     * Relation entre Membre et VuePublication
     * Un membre peut voir plusieurs publications
     */
    @OneToMany(mappedBy = "membre")
    private List<VuePublication> vuePublications;

    /**
     * Relation entre Membre et VueCommentaire
     * Un membre peut voir plusieurs commentaires
     */
    @OneToMany(mappedBy = "membre")
    private List<VueCommentaire> vueCommentaires;

    @OneToMany(mappedBy = "membre")
    private List<Titre> titres;

    @ManyToOne
    private Domaine domaine;

    @ManyToOne
    private Pays pays;

    @ManyToOne
    private Profil profil;

    /**
     *  Recuperer un membre par son email
     * @param email
     * @return Membre
     */

    @Nullable
    public static Membre getMembreByEmail(String email){
        List<Membre> membres= Membre.find.all();
        for (Membre m:membres){
            if(m.email.equals(email)){
                return m;
            }
        }
        return null;
    }

    /**
     * Methode pour verifier si le membre existe deja dans la base de données
     * methode
     * @return
     */
    public static Boolean verifierSiLeMembreExiste(String email){
        if(Membre.getMembreByEmail(email)!=null)
            return true;
        return false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Particulier getParticulier() {
        return particulier;
    }

    public void setParticulier(Particulier particulier) {
        this.particulier = particulier;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public List<Message> getMessagesEpediteurs() {
        return messagesEpediteurs;
    }

    public void setMessagesEpediteurs(List<Message> messagesEpediteurs) {
        this.messagesEpediteurs = messagesEpediteurs;
    }

    public List<Message> getMessagesDestinataires() {
        return messagesDestinataires;
    }

    public void setMessagesDestinataires(List<Message> messagesDestinataires) {
        this.messagesDestinataires = messagesDestinataires;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Groupe> getGroupeAppartenances() {
        return groupeAppartenances;
    }

    public void setGroupeAppartenances(List<Groupe> groupeAppartenances) {
        this.groupeAppartenances = groupeAppartenances;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }

    public List<Amitie> getAmities() {
        return amities;
    }

    public void setAmities(List<Amitie> amities) {
        this.amities = amities;
    }

    public List<Amitie> getDemandeAmities() {
        return demandeAmities;
    }

    public void setDemandeAmities(List<Amitie> demandeAmities) {
        this.demandeAmities = demandeAmities;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public List<VuePublication> getVuePublications() {
        return vuePublications;
    }

    public void setVuePublications(List<VuePublication> vuePublications) {
        this.vuePublications = vuePublications;
    }

    public List<VueCommentaire> getVueCommentaires() {
        return vueCommentaires;
    }

    public void setVueCommentaires(List<VueCommentaire> vueCommentaires) {
        this.vueCommentaires = vueCommentaires;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void addCompetence(Competence competence) {
        this.getCompetences().add(competence);
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Titre> getTitres() {
        return titres;
    }

    public void setTitres(List<Titre> titres) {
        this.titres = titres;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public List<SessionUser> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionUser> sessions) {
        this.sessions = sessions;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Membre(String email, String motDePasse) {
        Utils util= new Utils();
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.salt=util.getSalt();
        this.email = email;
        this.motDePasse = util.hash(motDePasse);
        this.etat=false;
    }

    @Nullable
    public static Membre inscrireParticulier(String email, String motDePasse, String nom, String prenom){
        if(Membre.verifierSiLeMembreExiste(email)==true){
            return null;
        }
        Role role=Role.getRoleByCode("User");
        Membre membre= new Membre(email,motDePasse);
        Particulier particulier=new Particulier(nom,prenom);
        particulier.save();
        membre.setParticulier(particulier);
        membre.setRole(role);
        membre.save();
        return membre;
    }

    @Nullable
    public static Membre inscrireEntreprise(String email, String motDePasse, String raisonSocial){
        if(Membre.verifierSiLeMembreExiste(email)==true){
            return null;
        }
        Role role=Role.getRoleByCode("User");
        Membre membre= new Membre(email,motDePasse);
        Entreprise entreprise=new Entreprise(raisonSocial);
        entreprise.save();
        membre.setEntreprise(entreprise);
        membre.setRole(role);
        membre.save();
        return membre;
    }

    public void completInfo(String adresse,String telephone,String siteweb){
        this.setAdresse(adresse);
        this.setTelephone(telephone);
        this.setSiteweb(siteweb);

    }
    public void completInfoEntreprise(String adresse,String telephone,String siteweb){
        completInfo(adresse,telephone,siteweb);
        this.update();

    }
    public void completInfoParticulier(String adresse,String telephone,String siteweb,Long anneeDeNaissance,String lieuDeNaissance){
        completInfo(adresse,telephone,siteweb);
        Particulier particulier=this.getParticulier();
        particulier.setAnneeDeNaissance(anneeDeNaissance);
        particulier.setLieuDeNaissance(lieuDeNaissance);
        particulier.update();
        this.update();

    }

    public void infoProfil(Pays pays, Domaine secteur, Profil situation, String titre){
        this.setPays(pays);
        this.setDomaine(secteur);
        this.setProfil(situation);
        this.setStatut(true);
        for(Titre t:this.getTitres()){
            if(t.isActif()==true){
                t.setActif(false);
            }
        }
        Titre titr=Titre.ajouter(titre);
        titr.setMembre(this);
        titr.update();
        this.update();
    }

    /**
     * Image de profil
     */
    public Image imageProfil(){
        for(Image image :this.getImages()){
            if(image.getProfil()==true){
                return image;
            }
        }
        return null;
    }

    /**
     * finder permettant d'accedant aux donnees de l'entite
     */
    public static Finder<String, Membre> find = new Finder<>(Membre.class);

    public static Membre getMembreById(Long id){
        return Ebean.find(Membre.class).where().idEq(id).findUnique();
    }

    public static List<Membre> getMembres(){
        return Ebean.find(Membre.class).findList();
    }

    public void activer(){
        this.setEtat(true);
        this.update();
    }

    /**
     * recuperer le nom de profil
     * @return
     */
    public String getNomProfil(){
        String nomProfil="";
        if(this.particulier==null){
            nomProfil=this.getEntreprise().getRaisonSocial();
        }else{
            nomProfil=this.getParticulier().getPrenom()+" "+this.getParticulier().getNom();
        }
        return nomProfil;
    }



    /**
     * activer toutes mes sesions
     */
    public void activerSession(){
        for(SessionUser sess:this.getSessions()){
            if(sess.getActive()==false){
                sess.activer();
            }
        }
    }

    /**
     * verifier s'il y'a une session qu'on a pas utilisé
     */
    public Boolean isSessionNonActive(){
        for(SessionUser sess:this.getSessions()){
            if(sess.getActive()==false){
                return true;
            }
        }
        return false;
    }

    /**
     * la derniere session non active
     */
    public SessionUser lastSession(){
        for(SessionUser sess:this.getSessions()){
            if(sess.getActive()==false){
                return sess;
            }
        }
        return null;
    }

    public Membre() {
        Utils util= new Utils();
        this.etat=true;
        this.dateInscription = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(this.dateInscription);
        try {
            this.dateInscription = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.salt=util.getSalt();
    }

    /**
     * creer un administrateur s'il n'existe pas
     */

    public static void addAmin(){
        Role role=Role.getRoleByCode("Admin");
        if(role.getMembres().size()<1){
            Membre membre=new Membre();
            membre.setPseudo("Admin");
            membre.setMotDePasse(BCrypt.hashpw("admin", membre.getSalt()));
            membre.setRole(role);
            membre.save();
        }
    }
}
