package com.ua.viktor.github.model;

/**
 * Created by viktor on 28.01.16.
 */
public class Event {
    private String type;
    private String id;
    private Actor actor;
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Actor getActor() {
        return actor;
    }

    public static class Actor {

        private String login;
        private String url;
        private String avatar_url;

        public String getLogin() {
            return login;
        }

        public String getUrl() {
            return url;
        }

        public String getAvatar_url() {
            return avatar_url;
        }
    }

    public static class Payload {
        private String head;

        public String getHead() {
            return head;
        }
    }

}
