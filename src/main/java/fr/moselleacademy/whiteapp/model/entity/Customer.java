package fr.moselleacademy.whiteapp.model.entity;

import java.time.LocalDate;
import java.util.Comparator;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * Un client. Cette entité est injectable.
 *
 * @author MOSELLE Maxime
 */
@Dependent
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity implements Comparable<Customer> {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Prénom.
     */
    @Size(max = 255)
    @Column(name = "given_name")
    private String givenName;

    /**
     * Nom de famille.
     */
    @Size(max = 255)
    @Column(name = "family_name")
    private String familyName;

    /**
     * Adresse de courriel. Cet attribut est une clef fonctionnelle.
     */
    @NotBlank
    @Size(max = 255)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Date de naissance.
     */
    @Past
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public Customer() {
        super();
        // Ne pas appeler explicitement.
    }

    /**
     * Construire une nouvelle instance de cette classe. Comme cette classe est
     * injectable, il est nécessaire de passer par <i>CDI</i> pour récupérer une
     * instance. Cette méthode de fabrique permet de faciliter cet appel.
     *
     * @return Une nouvelle instance vierge
     */
    public static Customer of() {
        return CDI.current().select(Customer.class).get();
    }

    @Override
    public int compareTo(final Customer other) {
        return Comparator
                .comparing(Customer::getFamilyName)
                .thenComparing(Customer::getGivenName)
                .thenComparing(Customer::getEmail)
                .compare(this, other);
    }

    // ------------------------------
    // Accesseurs & Muttateurs
    // ------------------------------
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
