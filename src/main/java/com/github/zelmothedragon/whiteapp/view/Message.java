package com.github.zelmothedragon.whiteapp.view;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utilitaire pour l'internationalisation des messages.
 *
 * @author MOSELLE Maxime
 */
public final class Message {

    /**
     * Nom de base du fichier de traduction.
     */
    private static final String BASE_NAME = "Messages";

    /**
     * Constructeur interne.
     */
    private Message() {
        // Pas d'instanciation.
    }

    /**
     * Récupérer un message internationalisé.
     *
     * @param key Clef du message du fichier de traduction
     * @param lang Langue
     * @return Le message
     */
    public static String get(final String key, final Locale lang) {
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, lang);
        final String message;
        if (bundle.containsKey(key)) {
            message = bundle.getString(key);
        } else {
            message = String.format("??%s??", key);
        }
        return message;
    }
}
