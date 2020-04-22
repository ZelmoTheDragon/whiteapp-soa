package fr.moselleacademy.whiteapp.model.entity;


import fr.moselleacademy.whiteapp.model.UUIDConverter;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Classe mère pour les entités persistante.
 *
 * @author MOSELLE Maxime
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clef primaire.
     */
    @NotNull
    @Id
    @Column(
            name = "id",
            nullable = false,
            unique = true,
            columnDefinition = UUIDConverter.UUID_COLUMN,
            length = UUIDConverter.UUID_SIZE
    )
    protected UUID id;

    /**
     * Version de l'objet. Ce champ est alimenté automatiquement par <i>JPA</i>
     * au moment de la persistance. Il permet d'éviter les problèmes de mise à
     * jour en cas d'accès concurrent.
     */
    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    protected AbstractEntity() {
        // Ne pas appeler explicitement.
        this.id = UUID.randomUUID();
        this.version = 0L;
    }

    @Override
    public int hashCode() {
        // Java propose une méthode native pour le calcul du code de hachage.
        // Pourquoi faire autrement ?
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        // Calcul de l'égalité de l'objet.
        // Basé sur la clef primaire, cet méthode est suffisante 
        // pour la plupart des entités.
        // Comme l'attribut 'id' est généré à l'instanciation de l'objet
        // le calcul de cette méthode renvera toujours un résultat cohérent
        // car jamais 'id' ne pourra prendre la valeur 'null'.
        final boolean eq;
        if (this == obj) {
            eq = true;
        } else if (!(obj instanceof AbstractEntity)) {
            eq = false;
        } else {
            AbstractEntity other = (AbstractEntity) obj;
            eq = Objects.equals(id, other.id);
        }
        return eq;
    }

    @Override
    public String toString() {
        return String.format(
                "%s[%s]{version=%s}",
                getClass().getName(),
                id,
                version
        );
    }

    // ------------------------------
    // Accesseurs & Muttateurs
    // ------------------------------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
