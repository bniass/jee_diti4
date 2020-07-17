package service;

import model.Medecin;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class MedecinDAO implements IMedecin {
    Session session;
    public MedecinDAO(){
        session = HibernateUtil.getSession();
    }
    @Override
    public List<Medecin> findAll() {
        return session.createQuery("SELECT m FROM Medecin m", Medecin.class).list();
    }

    @Override
    public Medecin findById(long id) {
        return session.find(Medecin.class, id);
    }

    @Override
    public void save(Medecin m) {
        try {
            session.save(m);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
