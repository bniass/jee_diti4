package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
    private String nom;
    @Column(length = 50)
    private String prenom;
    @Column(length = 20)
    private String tel;
    @Column(length = 200)
    private String adresse;
    @Column(length = 50)
    private String email;
    @Temporal(TemporalType.DATE)
    private Date datenaissance;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "medecin_specialite",
            joinColumns = @JoinColumn(name = "medecin_id"),
            inverseJoinColumns = @JoinColumn(name = "specialite_id"))
    private List<Specialite> specialites;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Specialite> getSpecialites() {
        return specialites;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }
}
