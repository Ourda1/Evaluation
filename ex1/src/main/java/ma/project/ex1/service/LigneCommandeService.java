package ma.project.ex1.service;

import ma.project.ex1.classes.LigneCommande;
import ma.project.ex1.dao.IDao;
import ma.project.ex1.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommande> {

    @Override
    public boolean create(LigneCommande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(LigneCommande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(LigneCommande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public LigneCommande getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        LigneCommande l = session.get(LigneCommande.class, id);
        session.close();
        return l;
    }

    @Override
    public List<LigneCommande> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommande> list = session.createQuery("from LigneCommande", LigneCommande.class).list();
        session.close();
        return list;
    }
}
