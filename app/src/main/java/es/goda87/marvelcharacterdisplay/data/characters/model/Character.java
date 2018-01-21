package es.goda87.marvelcharacterdisplay.data.characters.model;


import es.goda87.marvelcharacterdisplay.data.DataModel;

public class Character extends DataModel {

    private String urlDetail, urlWiki, urlCommics;
    private int availableComics, availableEvents;

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public String getUrlWiki() {
        return urlWiki;
    }

    public void setUrlWiki(String urlWiki) {
        this.urlWiki = urlWiki;
    }

    public String getUrlComics() {
        return urlCommics;
    }

    public void setUrlCommics(String urlCommics) {
        this.urlCommics = urlCommics;
    }

    public int getAvailableComics() {
        return availableComics;
    }

    public void setAvailableComics(int availableComics) {
        this.availableComics = availableComics;
    }

    public int getAvailableEvents() {
        return availableEvents;
    }

    public void setAvailableEvents(int availableEvents) {
        this.availableEvents = availableEvents;
    }
}
