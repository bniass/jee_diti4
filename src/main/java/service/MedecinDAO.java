package service;

import model.Medecin;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.saveOrUpdate(m);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
