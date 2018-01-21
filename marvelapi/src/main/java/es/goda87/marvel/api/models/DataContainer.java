package es.goda87.marvel.api.models;

import java.util.List;

/**
 * Created by goda87 on 20.01.18.
 */

public class DataContainer<T extends Data> {
    private Integer offset;
    private Integer limit;
    private Integer total;
    private Integer count;
    private List<T> results;

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getCount() {
        return count;
    }

    public List<T> getResults() {
        return results;
    }
}
