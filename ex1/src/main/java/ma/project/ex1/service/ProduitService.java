package ma.project.ex1.service;

import ma.project.ex1.classes.Produit;
import ma.project.ex1.classes.Commande;
import ma.project.ex1.classes.LigneCommande;
import ma.project.ex1.util.HibernateUtil;
import ma.project.ex1.dao.IDao;
import org.hibernate.Session;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Produit getById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Produit p = session.get(Produit.class, id);
        session.close();
        return p;
    }

    @Override
    public List<Produit> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = session.createQuery("from Produit", Produit.class).list();
        session.close();
        return list;
    }

    // 🔹 Afficher la liste des produits par catégorie
    public List<Produit> getProduitsByCategorie(String nomCategorie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery(
                        "from Produit p where p.categorie.nom = :nomCategorie", Produit.class)
                .setParameter("nomCategorie", nomCategorie)
                .list();
        session.close();
        return produits;
    }

    // 🔹 Produits commandés entre deux dates
    public List<Produit> getProduitsCommandesEntreDates(Date date1, Date date2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery(
                        "select distinct l.produit from LigneCommande l " +
                                "where l.commande.dateCommande between :d1 and :d2", Produit.class)
                .setParameter("d1", date1)
                .setParameter("d2", date2)
                .list();
        session.close();
        return produits;
    }

    // 🔹 Produits d'une commande donnée
    public List<LigneCommande> getProduitsByCommande(Long idCommande) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommande> lignes = session.createQuery(
                        "from LigneCommande l where l.commande.id = :idCommande", LigneCommande.class)
                .setParameter("idCommande", idCommande)
                .list();
        session.close();
        return lignes;
    }

    // 🔹 Produits dont le prix > 100 (requête nommée)
    public List<Produit> getProduitsPrixSuperieur100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createNamedQuery("Produit.findPrixSuperieur100", Produit.class).list();
        session.close();
        return produits;
    }
}
