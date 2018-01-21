package es.goda87.marvelcharacterdisplay.data.events.source;

import java.util.List;

import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by goda87 on 20.01.18.
 */

public interface EventsDataSource {

    Flowable<List<Event>> getEvents(Observable<Integer> lastItemShown);

    void setCharacterId(int characterId);
}
