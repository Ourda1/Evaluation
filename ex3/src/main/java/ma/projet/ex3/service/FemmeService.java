package ma.projet.ex3.service;

import ma.projet.ex3.beans.Femme;
import ma.projet.ex3.beans.Mariage;
import ma.projet.ex3.dao.IDao;
import ma.projet.ex3.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public void create(Femme femme) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(femme);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Femme femme) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(femme);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Femme femme) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(femme);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Femme findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Femme.class, id);
        }
    }

    @Override
    public List<Femme> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Femme", Femme.class).list();
        }
    }

    // Nombre d'enfants d'une femme entre deux dates (requête nommée)
    public Long getNbEnfantsBetween(int femmeId, String d1, String d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createNamedQuery("Femme.nbEnfantsBetween", Long.class);
            query.setParameter("femmeId", femmeId);
            query.setParameter("d1", java.time.LocalDate.parse(d1));
            query.setParameter("d2", java.time.LocalDate.parse(d2));
            return query.getSingleResult();
        }
    }

    // Femmes mariées au moins deux fois
    public List<Femme> getMarriedAtLeastTwice() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Femme.marriedAtLeastTwice", Femme.class).list();
        }
    }
}
