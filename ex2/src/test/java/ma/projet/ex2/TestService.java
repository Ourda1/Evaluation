package ma.projet.ex2;

import ma.projet.ex2.classes.Tache;
import ma.projet.ex2.service.EmployeService;
import ma.projet.ex2.service.ProjetService;
import ma.projet.ex2.service.TacheService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestService {

    public static void main(String[] args) throws Exception {

        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // ===== 1. Tâches réalisées par un employé =====
        System.out.println("===== Tâches réalisées par l'employé 1 =====");
        employeService.afficherTachesEmploye(1);

        // ===== 2. Projets gérés par un employé =====
        System.out.println("\n===== Projets gérés par l'employé 1 =====");
        employeService.afficherProjetsGeres(1);

        // ===== 3. Tâches planifiées pour un projet =====
        System.out.println("\n===== Tâches planifiées pour le projet 1 =====");
        projetService.afficherTachesProjet(1);

        // ===== 4. Tâches réalisées avec dates réelles =====
        System.out.println("\n===== Tâches réalisées avec dates réelles pour le projet 1 =====");
        projetService.afficherTachesRealisees(1);

        // ===== 5. Tâches dont le prix est supérieur à 1000 DH =====
        System.out.println("\n===== Tâches dont le prix est supérieur à 1000 DH =====");
        List<Tache> tachesChere = tacheService.tachesPrixSuperieur(1000);
        tachesChere.forEach(t -> {
            System.out.println("ID: " + t.getId() + " | Nom: " + t.getNom() + " | Prix: " + t.getPrix());
        });

        // ===== 6. Tâches réalisées entre deux dates =====
        System.out.println("\n===== Tâches réalisées entre le 01/02/2025 et le 30/04/2025 =====");
        Date dateDebut = sdf.parse("01/02/2025");
        Date dateFin = sdf.parse("30/04/2025");
        List<Tache> tachesEntreDates = tacheService.tachesRealiseesEntre(dateDebut, dateFin);
        tachesEntreDates.forEach(t -> {
            t.getEmployeTaches().forEach(et -> {
                System.out.println("ID: " + t.getId() + " | Nom: " + t.getNom() +
                        " | Date Début Réelle: " + sdf.format(et.getDateDebutReelle()) +
                        " | Date Fin Réelle: " + sdf.format(et.getDateFinReelle()));
            });
        });
    }
}
