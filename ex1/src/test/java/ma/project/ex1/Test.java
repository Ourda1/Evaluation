package ma.project.ex1;

import ma.project.ex1.classes.*;
import ma.project.ex1.dao.*;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        CategorieDao categorieDao = new CategorieDao();
        ProduitDao produitDao = new ProduitDao();
        CommandeDao commandeDao = new CommandeDao();
        LigneCommandeDao ligneDao = new LigneCommandeDao();

        // Créer une catégorie
        Categorie cat = new Categorie("Informatique");
        categorieDao.create(cat);

        // Créer un produit
        Produit p = new Produit("Ordinateur HP", 7500.0, cat);
        produitDao.create(p);

        // Créer une commande
        Commande cmd = new Commande(new Date());
        commandeDao.create(cmd);

        // Ajouter une ligne de commande
        LigneCommande ligne = new LigneCommande(2, p, cmd);
        ligneDao.create(ligne);

        System.out.println("✅ Données ajoutées avec succès !");
    }
}
