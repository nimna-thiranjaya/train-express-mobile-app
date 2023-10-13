package com.example.trainbookingapp;

public class MostViewedDomain {
    private String title;
    private String subtitle;
    private String url;

    private String id;

    public MostViewedDomain(String title, String subtitle, String url, String id) {
        this.title = title;
        this.subtitle = subtitle;
        this.url = url;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
