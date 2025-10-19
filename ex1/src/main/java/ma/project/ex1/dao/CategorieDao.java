package ma.project.ex1.dao;

import ma.project.ex1.classes.Categorie;
import ma.project.ex1.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class CategorieDao implements IDao<Categorie> {

    @Override
    public boolean create(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Categorie getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categorie c = session.get(Categorie.class, id);
        session.close();
        return c;
    }

    @Override
    public List<Categorie> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> categories = session.createQuery("from Categorie", Categorie.class).list();
        session.close();
        return categories;
    }
}
