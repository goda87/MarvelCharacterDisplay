package es.goda87.marvel.api.models;

import java.util.Date;
import java.util.List;


public class Character extends Data {

    private String name;
    private ComicList comics;
    private EventList events;

    public String getName() {
        return name;
    }

    public ComicList getComics() {
        return comics;
    }

    public EventList getEvents() {
        return events;
    }
}
