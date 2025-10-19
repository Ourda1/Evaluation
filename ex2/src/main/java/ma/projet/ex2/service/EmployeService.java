package ma.projet.ex2.service;

import ma.projet.ex2.dao.IDao;
import ma.projet.ex2.classes.Employe;
import ma.projet.ex2.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public void create(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(employe);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Employe update(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(employe);
            tx.commit();
            return employe;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.delete(employe);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Employe findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        }
    }

    @Override
    public List<Employe> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe", Employe.class).list();
        }
    }

    // Méthode spécifique : afficher les tâches d’un employé
    public void afficherTachesEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employe emp = session.get(Employe.class, employeId);
            if (emp != null && emp.getEmployeTaches() != null) {
                emp.getEmployeTaches().forEach(et -> {
                    System.out.println(et.getTache().getNom() + " | " +
                            et.getDateDebutReelle() + " - " + et.getDateFinReelle());
                });
            }
        }
    }

    // Méthode spécifique : afficher les projets gérés par un employé
    public void afficherProjetsGeres(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employe emp = session.get(Employe.class, employeId);
            if (emp != null && emp.getProjetsGeres() != null) {
                emp.getProjetsGeres().forEach(projet -> {
                    System.out.println(projet.getNom() + " | " + projet.getDateDebut() + " - " + projet.getDateFin());
                });
            }
        }
    }
}
