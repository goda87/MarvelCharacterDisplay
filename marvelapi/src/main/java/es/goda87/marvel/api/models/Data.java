package es.goda87.marvel.api.models;

import java.util.List;

/**
 * Created by goda87 on 20.01.18.
 */

public class Data {

    private Integer id;
    private String description;
    private String resourceURI;
    private List<Url> urls;
    private Image thumbnail;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public Image getThumbnail() {
        return thumbnail;
    }
}
