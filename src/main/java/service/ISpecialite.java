package service;

import model.Specialite;

import javax.ejb.Local;
import java.util.List;
@Local
public interface ISpecialite {
    public List<Specialite> findSpecialiteByServiceId(long id);
    public Specialite findById(long id);
}
