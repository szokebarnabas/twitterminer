package com.bs.twitterminer.analytics.domain;

public class Client {
    private final String sessionId;
    private final String clientId;

    public Client(String sessionId) {
        this.sessionId = sessionId;
        this.clientId = null;
    }

    public Client(String sessionId, String clientId) {
        this.sessionId = sessionId;
        this.clientId = clientId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getClientId() {
        return clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!sessionId.equals(client.sessionId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }
}
