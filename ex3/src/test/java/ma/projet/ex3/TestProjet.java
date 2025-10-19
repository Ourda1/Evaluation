package ma.projet.ex3;

import ma.projet.ex3.beans.Femme;
import ma.projet.ex3.beans.Homme;
import ma.projet.ex3.beans.Mariage;
import ma.projet.ex3.service.FemmeService;
import ma.projet.ex3.service.HommeService;
import ma.projet.ex3.service.MariageService;

import java.time.LocalDate;
import java.util.List;

public class TestProjet {
    public static void main(String[] args) {
        FemmeService femmeService = new FemmeService();
        HommeService hommeService = new HommeService();
        MariageService mariageService = new MariageService();

        //  Création de 10 femmes
        Femme f1 = new Femme("SALIMA", "RAMI", LocalDate.of(1965, 3, 12));
        Femme f2 = new Femme("AMAL", "ALI", LocalDate.of(1970, 5, 20));
        Femme f3 = new Femme("WAFA", "ALAOUI", LocalDate.of(1975, 1, 15));
        Femme f4 = new Femme("KARIMA", "ALAMI", LocalDate.of(1960, 7, 30));
        Femme f5 = new Femme("NOURA", "BENJELLOUN", LocalDate.of(1980, 4, 10));
        Femme f6 = new Femme("HANANE", "LAHLOU", LocalDate.of(1982, 2, 25));
        Femme f7 = new Femme("IMANE", "CHAOUI", LocalDate.of(1978, 11, 5));
        Femme f8 = new Femme("SAIDA", "BOUCHRA", LocalDate.of(1985, 6, 18));
        Femme f9 = new Femme("LATIFA", "FARSI", LocalDate.of(1973, 9, 9));
        Femme f10 = new Femme("SANA", "MAZOUZI", LocalDate.of(1990, 12, 1));

        femmeService.create(f1); femmeService.create(f2); femmeService.create(f3);
        femmeService.create(f4); femmeService.create(f5); femmeService.create(f6);
        femmeService.create(f7); femmeService.create(f8); femmeService.create(f9);
        femmeService.create(f10);

        //  Création de 5 hommes
        Homme h1 = new Homme("SAID", "SAFI", LocalDate.of(1960, 8, 15));
        Homme h2 = new Homme("MOHAMED", "RAHMI", LocalDate.of(1962, 9, 21));
        Homme h3 = new Homme("YASSIN", "ELHADI", LocalDate.of(1970, 3, 3));
        Homme h4 = new Homme("ADIL", "KARIM", LocalDate.of(1975, 1, 10));
        Homme h5 = new Homme("RACHID", "BENALI", LocalDate.of(1980, 5, 5));

        hommeService.create(h1); hommeService.create(h2); hommeService.create(h3);
        hommeService.create(h4); hommeService.create(h5);

        //  Création des mariages
        mariageService.create(new Mariage(h1, f1, LocalDate.of(1990, 9, 3), null, 4));
        mariageService.create(new Mariage(h1, f2, LocalDate.of(1995, 9, 3), null, 2));
        mariageService.create(new Mariage(h1, f3, LocalDate.of(2000, 11, 4), null, 3));
        mariageService.create(new Mariage(h1, f4, LocalDate.of(1989, 9, 3), LocalDate.of(1990, 9, 3), 0));

        mariageService.create(new Mariage(h2, f5, LocalDate.of(1992, 3, 10), null, 1));
        mariageService.create(new Mariage(h3, f6, LocalDate.of(1998, 6, 15), null, 2));
        mariageService.create(new Mariage(h4, f7, LocalDate.of(2001, 2, 20), null, 1));
        mariageService.create(new Mariage(h5, f8, LocalDate.of(2005, 7, 10), null, 3));
        mariageService.create(new Mariage(h2, f9, LocalDate.of(1990, 1, 5), null, 2));
        mariageService.create(new Mariage(h2, f10, LocalDate.of(1995, 5, 1), null, 1));

        // Afficher la liste des femmes
        System.out.println("=== Liste des Femmes ===");
        List<Femme> femmes = femmeService.findAll();
        femmes.forEach(f -> System.out.println(f.getNom() + " " + f.getPrenom() + " - " + f.getDateNaissance()));

        //  Afficher la femme la plus âgée
        Femme oldest = femmes.stream().min((a, b) -> a.getDateNaissance().compareTo(b.getDateNaissance())).orElse(null);
        System.out.println("\nFemme la plus âgée : " + oldest.getNom() + " " + oldest.getPrenom());

        // Afficher les épouses d’un homme donné
        System.out.println("\nÉpouses de SAID SAFI :");
        List<Mariage> epousesH1 = hommeService.getEpousesBetweenDates(h1.getId(),
                LocalDate.of(1980,1,1), LocalDate.of(2025,12,31));
        int i = 1;
        for(Mariage m : epousesH1){
            System.out.println(i++ + ". " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                    " Date Début : " + m.getDateDebut() + " Nbr Enfants : " + m.getNbrEnfant());
        }

        //  Nombre d'enfants d'une femme entre deux dates
        Long nbEnfantsFemme = femmeService.getNbEnfantsBetween(f1.getId(), "1980-01-01", "2000-12-31");
        System.out.println("\nNombre d'enfants de " + f1.getNom() + " entre 1980 et 2000 : " + nbEnfantsFemme);

        // Femmes mariées deux fois ou plus
        System.out.println("\nFemmes mariées au moins deux fois :");
        List<Femme> femmes2x = femmeService.getMarriedAtLeastTwice();
        femmes2x.forEach(f -> System.out.println(f.getNom() + " " + f.getPrenom()));

        //  Hommes mariés à quatre femmes entre deux dates
        Long nbHommes4Femmes = hommeService.countHommesMarriedToFourFemmes(
                LocalDate.of(1980,1,1), LocalDate.of(2025,12,31));
        System.out.println("\nNombre d'hommes mariés à quatre femmes entre 1980 et 2025 : " + nbHommes4Femmes);

        // nom des Hommes mariés à quatre femmes entre deux dates
        List<Homme> hommes4Femmes = hommeService.getHommesMarriedToFourFemmes(
                LocalDate.of(1980, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        System.out.println("\nHommes mariés à quatre femmes entre 1980 et 2025 :");
        if (hommes4Femmes.isEmpty()) {
            System.out.println("Aucun homme trouvé.");
        } else {
            for (Homme h : hommes4Femmes) {
                System.out.println(h.getNom() + " " + h.getPrenom());
            }
        }


        // Afficher les mariages d’un homme avec tous les détails
        System.out.println("\nMariages de SAID SAFI :");
        List<Mariage> mariagesH1 = mariageService.getMariagesOfHomme(h1.getId());
        int j = 1;
        for(Mariage m : mariagesH1){
            System.out.println(j++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                    " Date Début : " + m.getDateDebut() +
                    (m.getDateFin() != null ? " Date Fin : " + m.getDateFin() : " En Cours") +
                    " Nbr Enfants : " + m.getNbrEnfant());
        }
    }
}
