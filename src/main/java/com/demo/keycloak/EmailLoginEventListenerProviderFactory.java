package com.demo.keycloak;

import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class EmailLoginEventListenerProviderFactory implements EventListenerProviderFactory {

    public static final String ID = "email-login-listener";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new EmailLoginEventListenerProvider(session);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {}

    @Override
    public String getId() {
        return ID;
    }
}
