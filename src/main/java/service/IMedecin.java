package service;

import model.Medecin;

import javax.ejb.Local;
import java.util.List;
@Local
public interface IMedecin {
    public List<Medecin> findAll();
    public Medecin findById(long id);
    public void save(Medecin m);
}
