package fr.moselleacademy.whiteapp.model;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convertisseur d'attribut pour le type <code>java.util.UUID</code> vers
 * <code>java.lang.String</code> et vice versa pour la base de données. Cette
 * classe est invoquée automatiquement par <i>JPA</i>.
 *
 * @author MOSELLE Maxime
 */
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String> {

    /**
     * Taille de la colonne pour le stockage en base de données.
     */
    public static final int UUID_SIZE = 36;

    /**
     * Type de la colonne pour le stockage en base de données.
     */
    public static final String UUID_COLUMN = "VARCHAR(36)";

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public UUIDConverter() {
        // Ne pas appeler explicitement.
    }

    /**
     * Convertir un attribut de type <code>java.util.UUID</code> vers un type
     * <code>java.lang.String</code>.
     *
     * @param attribute Attribut d'une entité persistante
     * @return La représentation textuelle d'un type <code>java.util.UUID</code>
     * ou <code>null</code>.
     */
    @Override
    public String convertToDatabaseColumn(final UUID attribute) {
        final String dbData;
        if (Objects.nonNull(attribute)) {
            dbData = attribute.toString();
        } else {
            dbData = null;
        }
        return dbData;
    }

    /**
     * Convertir une colonne de type assignable à <code>java.lang.String</code>
     * vers un type <code>java.util.UUID</code>.
     *
     * @param dbData Colonne d'une table en base de données
     * @return La représentation d'un type <code>java.util.UUID</code> ou
     * <code>null</code>.
     */
    @Override
    public UUID convertToEntityAttribute(final String dbData) {
        final UUID attribute;
        if (Objects.nonNull(dbData)) {
            attribute = UUID.fromString(dbData);
        } else {
            attribute = null;
        }
        return attribute;
    }

}
