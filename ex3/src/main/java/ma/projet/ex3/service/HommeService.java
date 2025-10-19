package ma.projet.ex3.service;


import jakarta.persistence.criteria.*;
import ma.projet.ex3.beans.Homme;
import ma.projet.ex3.beans.Mariage;
import ma.projet.ex3.dao.IDao;
import ma.projet.ex3.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public void create(Homme homme) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(homme);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Homme homme) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(homme);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Homme homme) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(homme);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Homme findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Homme.class, id);
        }
    }

    @Override
    public List<Homme> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Homme", Homme.class).list();
        }
    }

    // Méthode spécifique : afficher les épouses d’un homme entre deux dates
    public List<Mariage> getEpousesBetweenDates(int hommeId, LocalDate d1, LocalDate d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Mariage m WHERE m.homme.id = :id AND m.dateDebut BETWEEN :d1 AND :d2";
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("id", hommeId);
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            return query.list();
        }
    }

    // nbr des hommes marié a 3 femmes
    public Long countHommesMarriedToFourFemmes(LocalDate d1, LocalDate d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> root = cq.from(Mariage.class);
            cq.select(cb.countDistinct(root.get("homme")))
                    .where(cb.between(root.get("dateDebut"), d1, d2));
            return session.createQuery(cq).getSingleResult();
        }
    }

    public List<Homme> getHommesMarriedToFourFemmes(LocalDate d1, LocalDate d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Homme> cq = cb.createQuery(Homme.class);
            Root<Mariage> root = cq.from(Mariage.class);
            Join<Mariage, Homme> hommeJoin = root.join("homme");
            Predicate datePredicate = cb.between(root.get("dateDebut"), d1, d2);
            cq.groupBy(hommeJoin.get("id"), hommeJoin.get("nom"), hommeJoin.get("prenom"));
            cq.having(cb.equal(cb.count(root.get("femme")), 4L));
            cq.select(hommeJoin).where(datePredicate);

            return session.createQuery(cq).getResultList();
        }
    }


}
