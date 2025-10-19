package ma.projet.ex3.beans;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name="Femme.nbEnfantsBetween",
                query="SELECT SUM(m.nbrEnfant) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :d1 AND :d2"
        ),
        @NamedQuery(
                name="Femme.marriedAtLeastTwice",
                query="SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
        )
})
public class Femme extends Personne {
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {
    }


    public Femme(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}

