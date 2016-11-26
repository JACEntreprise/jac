package models;

import com.avaje.ebean.Ebean;

import javax.persistence.*;
import java.util.List;

/**
 * Created by julio on 25/06/2016.
 */
@Entity
@DiscriminatorValue("article")
public class Article extends Publication {
    @ManyToMany(mappedBy = "articles")
    private List<Domaine> domaines;

    private boolean publie;
    private boolean etat;

    public Article(){
        etat = false;
        publie = false;
    }

    public List<Domaine> getDomaines() {
        return domaines;
    }

    public void setDomaines(List<Domaine> domaines) {
        this.domaines = domaines;
    }

    public boolean isPublie() {
        return publie;
    }

    public void setPublie(boolean publie) {
        this.publie = publie;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }




    public String validate(){
        return null;
    }
}
