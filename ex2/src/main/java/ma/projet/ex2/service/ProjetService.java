package ma.projet.ex2.service;

import ma.projet.ex2.dao.IDao;
import ma.projet.ex2.classes.Projet;
import ma.projet.ex2.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public void create(Projet projet) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(projet);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Projet update(Projet projet) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(projet);
            tx.commit();
            return projet;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Projet projet) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(projet);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Projet findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Projet.class, id);
        }
    }

    @Override
    public List<Projet> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Projet", Projet.class).list();
        }
    }

    // Méthode spécifique : afficher les tâches planifiées pour un projet
    public void afficherTachesProjet(int projetId) {
        Projet projet = findById(projetId);
        if (projet != null && projet.getTaches() != null) {
            System.out.println("Projet : " + projet.getId() + " | " + projet.getNom());
            System.out.println("Liste des tâches planifiées :");
            projet.getTaches().forEach(t -> {
                System.out.println(t.getId() + " | " + t.getNom() + " | " + t.getDateDebut() + " - " + t.getDateFin());
            });
        }
    }

    // Méthode spécifique : afficher les tâches réalisées avec dates réelles
    public void afficherTachesRealisees(int projetId) {
        Projet projet = findById(projetId);
        if (projet != null && projet.getTaches() != null) {
            System.out.println("Projet : " + projet.getId() + " | " + projet.getNom());
            projet.getTaches().forEach(t -> {
                if (t.getEmployeTaches() != null) {
                    t.getEmployeTaches().forEach(et -> {
                        System.out.println(et.getId() + " | " + t.getNom() + " | " +
                                et.getDateDebutReelle() + " - " + et.getDateFinReelle());
                    });
                }
            });
        }
    }
}
