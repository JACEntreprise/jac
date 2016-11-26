package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import org.jetbrains.annotations.Contract;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * L'entité Publication
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="type")
@DiscriminatorValue("publication")
public class Publication extends Model{
    /**
     * Identifiant de publication
     */
    @Id
    private Long id;

    /**
     * Le titre de la publication
     */

    private String titre;

    /**
     * le contenu de la publication
     */
    @Constraints.Required
    @Lob
    private String contenu;

    /**
     * La date de la publication
     */
    @Formats.DateTime(pattern="dd.MM.yyyy HH:mm:ss")
    private Date datePublication;

    /**
     * L'image associée à la publication
     */
    private String urlImage;

    /**
     * L'image associée à la publication
     */
    private String nomImage;

    /**
     * Relation entre publication et Commentaire
     * Une privateation est associée à plusieurs commentaires
     */
    @OneToMany(mappedBy = "publication")
    private List<Commentaire> commentaires;

    /**
     * Relation entre publication et Membre
     * plusieurs publications sont associées à un seul membre
     */
    @ManyToOne
    private Membre membre;

    /**
     * Relation entre publication et VuePublication
     * Une publication peut etre vue par plusieurs membres
     */
    @OneToMany(mappedBy = "publication")
    private List<VuePublication> vuePublications;

    /**
     * Constructeur par defaut
     */
    public Publication() {
        datePublication = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String nvd = dt.format(datePublication);
        try {
            datePublication = dt.parse(nvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Publication(String titre, String contenu, String urlImage,String nomImage, Membre membre) {
        this.titre = titre;
        this.contenu = contenu;
        this.urlImage = urlImage;
        this.membre = membre;
        this.nomImage = nomImage;
        this.datePublication = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String nvd = dt.format(this.datePublication);
        try {
            this.datePublication = dt.parse(nvd);
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public List<VuePublication> getVuePublications() {
        return vuePublications;
    }

    public void setVuePublications(List<VuePublication> vuePublications) {
        this.vuePublications = vuePublications;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    /**
     * finder permettant d'accedant aux donnees de l'entite
     */
    public static Finder<Long, Publication> find = new Finder<Long,Publication>(Publication.class);

}
