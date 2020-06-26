package service;

import model.Utilisateur;
import org.hibernate.Session;
import utils.HibernateUtil;

import javax.ejb.Stateless;

@Stateless
public class UserDAO implements IUser {
    Session session;
    public UserDAO(){
        session = HibernateUtil.getSession();
    }
    @Override
    public Utilisateur findUser(String username, String pwd) {
        try {
            return session.createQuery("SELECT u FROM Utilisateur u " +
                    "WHERE u.username = :username AND u.password = :pwd", Utilisateur.class)
                    .setParameter("username", username)
                    .setParameter("pwd", pwd).getSingleResult();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
