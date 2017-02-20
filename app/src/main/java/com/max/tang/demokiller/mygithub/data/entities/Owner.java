package com.max.tang.demokiller.mygithub.data.entities;

public class Owner {
    private String received_events_url;

    private String organizations_url;

    private String avatar_url;

    private String gravatar_id;

    private String gists_url;

    private String starred_url;

    private boolean site_admin;

    private String type;

    private String url;

    private long id;

    private String html_url;

    private String following_url;

    private String events_url;

    private String login;

    private String subscriptions_url;

    private String repos_url;

    private String followers_url;

    @Override public String toString() {
        return "ClassPojo [received_events_url = "
            + received_events_url
            + ", organizations_url = "
            + organizations_url
            + ", avatar_url = "
            + avatar_url
            + ", gravatar_id = "
            + gravatar_id
            + ", gists_url = "
            + gists_url
            + ", starred_url = "
            + starred_url
            + ", site_admin = "
            + site_admin
            + ", type = "
            + type
            + ", url = "
            + url
            + ", id = "
            + id
            + ", html_url = "
            + html_url
            + ", following_url = "
            + following_url
            + ", events_url = "
            + events_url
            + ", login = "
            + login
            + ", subscriptions_url = "
            + subscriptions_url
            + ", repos_url = "
            + repos_url
            + ", followers_url = "
            + followers_url
            + "]";
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public long getId() {
        return id;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getLogin() {
        return login;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }
}