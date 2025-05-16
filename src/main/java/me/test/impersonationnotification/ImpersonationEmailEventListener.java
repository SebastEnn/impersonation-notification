package me.test.impersonationnotification;

import org.jboss.logging.Logger;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.email.freemarker.beans.EventBean;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.HashMap;
import java.util.Map;

public class ImpersonationEmailEventListener implements EventListenerProvider {

    private final KeycloakSession session;
    private final Logger logger = Logger.getLogger(ImpersonationEmailEventListener.class);

    public ImpersonationEmailEventListener(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.IMPERSONATE) {
            handleImpersonateEvent(event);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
    }

    private void handleImpersonateEvent(Event event) {
        String userRealmId = event.getRealmId();
        String userId = event.getUserId();
        RealmModel realm = session.realms().getRealm(userRealmId);
        UserModel user = session.users().getUserById(realm, userId);
        if (realm == null || user == null) return;
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.isEmailVerified()) return;

        String impersonatorRealmName = event.getDetails().get("impersonator_realm");
        String impersonatorName = event.getDetails().get("impersonator");
        RealmModel impersonatorRealm = session.realms().getRealmByName(impersonatorRealmName);
        UserModel impersonator = session.users().getUserByUsername(impersonatorRealm, impersonatorName);
        if (impersonatorRealm == null || impersonator == null) return;

        try {
            EmailTemplateProvider emailTemplateProvider = session
                    .getProvider(EmailTemplateProvider.class)
                    .setRealm(realm)
                    .setUser(user);

            String subjectKey = "impersonationNotificationSubject";
            String bodyTemplate = "impersonation-notification.ftl";

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("event", new EventBean((event)));
            attributes.put("impersonator", impersonator);

            emailTemplateProvider.send(subjectKey, bodyTemplate, attributes);

           logger.info("Impersonation notification sent to " + user.getUsername() + " (" + user.getFirstName() + " " + user.getLastName() + ").");
        } catch (EmailException e) {
            logger.error("Could not send a impersonation notification to " + user.getEmail(), e);
        }
    }

    @Override
    public void close() {
    }
}
