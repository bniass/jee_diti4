package service;

import model.Medecin;
import model.Service;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class ServiceDAO implements IService {
    Session session;
    public ServiceDAO(){
        session = HibernateUtil.getSession();
    }
    @Override
    public List<Service> findAll() {
        return session.createQuery("SELECT s FROM Service s", Service.class).list();
    }

    @Override
    public Service findById(long id) {
        return session.find(Service.class, id);
    }
}
