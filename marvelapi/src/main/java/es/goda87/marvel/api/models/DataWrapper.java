package es.goda87.marvel.api.models;

/**
 * Created by goda87 on 20.01.18.
 */

public class DataWrapper<T extends DataContainer> {
    private Integer code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributioonHTML;
    private T data;
    private String etag;

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public String getAttributioonHTML() {
        return attributioonHTML;
    }

    public T getData() {
        return data;
    }

    public String getEtag() {
        return etag;
    }
}
