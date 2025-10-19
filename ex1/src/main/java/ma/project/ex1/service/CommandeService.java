package ma.project.ex1.service;

import ma.project.ex1.classes.Commande;
import ma.project.ex1.dao.IDao;
import ma.project.ex1.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Commande getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Commande c = session.get(Commande.class, id);
        session.close();
        return c;
    }

    @Override
    public List<Commande> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Commande> list = session.createQuery("from Commande", Commande.class).list();
        session.close();
        return list;
    }
}

