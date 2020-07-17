package service;

import model.Service;
import model.Specialite;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class SpecialiteDAO implements ISpecialite {
    Session session;
    public SpecialiteDAO(){
        session = HibernateUtil.getSession();
    }
    @Override
    public List<Specialite> findSpecialiteByServiceId(long id) {
        return session.createQuery("SELECT s FROM Specialite s JOIN s.service srv WHERE srv.id = :id",
                Specialite.class)
                .setParameter("id", id)
                .list();
    }

    @Override
    public Specialite findById(long id) {
        return session.find(Specialite.class, id);
    }
}
