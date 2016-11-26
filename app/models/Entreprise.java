package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Entite Entreprise
 */
@Entity
public class Entreprise extends Model{
    /**
     * Identifiant d'entreprise
     */
    @Id
    public long id;

    /**
     * La raison sociale de l'entreprise
     */
    public String raisonSocial;

    /**
     * Relation d'héritage entre Entreprise et Membre
     * Une entreprise est un membre
     */
    @OneToOne(mappedBy = "entreprise")
    public Membre membre;

    public Entreprise() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Entreprise(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    /**
     * finder permettant d'accedant aux donnees de l'entite
     */
    public static Finder<Long, Entreprise> find = new Finder<Long,Entreprise>(Entreprise.class);
}