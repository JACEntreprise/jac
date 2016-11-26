package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Entité Amitié
 */

@Entity
public class VuePublication extends Model {
    @Id
    public long id;

    /**
     * plusieurs vues pour un membre
     */
    @ManyToOne
    public Membre membre;


    /**
     * plusieurs vues pour pour plusieurs publications
     */
    @ManyToOne
    public Publication publication;

    /**
     * Attribut qui permet de connaitre les amis acceptés
     */
    public boolean vue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public boolean getVue() {
        return vue;
    }

    public void setVue(boolean vue) {
        this.vue = vue;
    }

    public static Finder<Long, VuePublication> find = new Finder<Long, VuePublication>(VuePublication.class);
}
