package ma.project.ex1;

import ma.project.ex1.classes.*;
import ma.project.ex1.service.*;
import java.util.Date;
import java.util.List;

public class TestService {
    public static void main(String[] args) {
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // Afficher produits d'une catégorie
        System.out.println("=== Produits Informatique ===");
        List<Produit> produitsCat = produitService.getProduitsByCategorie("Informatique");
        produitsCat.forEach(p -> System.out.println(p.getNom() + " - " + p.getPrix()));

        // Produits entre deux dates
        System.out.println("\n=== Produits commandés entre deux dates ===");
        Date d1 = new Date(2023 - 1900, 0, 1);
        Date d2 = new Date(2025 - 1900, 11, 31);
        produitService.getProduitsCommandesEntreDates(d1, d2)
                .forEach(p -> System.out.println(p.getNom()));

        // Produits d'une commande
        System.out.println("\n=== Produits de la commande ID = 1 ===");
        List<LigneCommande> lignes = produitService.getProduitsByCommande(1L);
        for (LigneCommande l : lignes) {
            System.out.println("Produit : " + l.getProduit().getNom() +
                    " | Prix : " + l.getProduit().getPrix() +
                    " | Quantité : " + l.getQuantite());
        }

        // Produits prix > 100
        System.out.println("\n=== Produits prix > 100 DH ===");
        produitService.getProduitsPrixSuperieur100()
                .forEach(p -> System.out.println(p.getNom() + " - " + p.getPrix()));
    }
}

