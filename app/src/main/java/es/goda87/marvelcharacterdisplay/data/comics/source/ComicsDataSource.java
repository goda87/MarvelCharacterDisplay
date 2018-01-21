package es.goda87.marvelcharacterdisplay.data.comics.source;

import java.util.List;

import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by goda87 on 20.01.18.
 */

public interface ComicsDataSource {

    Flowable<List<Comic>> getComics(Observable<Integer> lastItemShown);

    void setCharacterId(int characterId);
}
