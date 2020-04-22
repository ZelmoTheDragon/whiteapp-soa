package fr.moselleacademy.whiteapp.view;

import fr.moselleacademy.whiteapp.controller.CustomerController;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validateur du champ <i>email</i>. Vérifie que l'adresse de courriel n'est pas
 * déjà utilisé.
 *
 * @author MOSELLE Maxime
 */
@FacesValidator(managed = true)
public class EmailExistValidator implements Validator<String> {

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de Java EE.
     */
    public EmailExistValidator() {
        // Ne pas appeler explicitement.
    }

    @Override
    public void validate(
            final FacesContext context,
            final UIComponent component,
            final String value) throws ValidatorException {

        var controller = CDI.current().select(CustomerController.class).get();
        if (controller.emailExists(value)) {
            var lang = context.getViewRoot().getLocale();
            var message = new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    Message.get("customer.emailExist", lang),
                    ""
            );

            context.addMessage(null, message);
            throw new ValidatorException(message);
        }
    }

}
