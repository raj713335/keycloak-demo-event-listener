package com.demo.keycloak;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

public class EmailLoginEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(EmailLoginEventListenerProvider.class);

    private final KeycloakSession session;
    private  final RealmProvider model;

    public EmailLoginEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
        if (event.getType() == EventType.LOGIN) {
            String email = session.users().getUserById(session.getContext().getRealm(), event.getUserId()).getEmail();
            if (email != null) {
                sendEmail(email);
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        if (ResourceType.USER.equals(adminEvent.getResourceType())
                && OperationType.CREATE.equals(adminEvent.getOperationType())) {
            RealmModel realm = this.model.getRealm(adminEvent.getRealmId());
            UserModel user = this.session.users().getUserById(realm, adminEvent.getResourcePath().substring(6));

            user.setEmail("arnab713335@gmail.com");

            System.out.println("User logged in: " + user.getUsername());
        }
    }

    private void sendEmail(String recipient) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.example.com"); // SMTP server
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("your-email@example.com", "your-password"));
            email.setSSLOnConnect(true);
            email.setFrom("no-reply@example.com");
            email.setSubject("Login Alert");
            email.setMsg("You have successfully logged into the system.");
            email.addTo(recipient);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {}

}
