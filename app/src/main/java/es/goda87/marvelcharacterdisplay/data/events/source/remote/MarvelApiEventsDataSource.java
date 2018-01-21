package es.goda87.marvelcharacterdisplay.data.events.source.remote;

import java.util.ArrayList;
import java.util.List;

import es.goda87.marvel.api.MarvelApi;
import es.goda87.marvel.api.models.EventDataWrapper;
import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsDataSource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by goda87 on 20.01.18.
 */

public class MarvelApiEventsDataSource implements EventsDataSource {

    private MarvelApi api;
    private int characterId = 1009610;

    private List<Event> events = new ArrayList<>();

    public MarvelApiEventsDataSource(String publicKey, String privateKey) {
        this.api = new MarvelApi(publicKey, privateKey);
    }

    @Override
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    @Override
    public Flowable<List<Event>> getEvents(Observable<Integer> lastItemShown) {
        return lastItemShown
                .observeOn(Schedulers.computation())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer lastItemShown) throws Exception {
                        return lastItemShown >= events.size() - 15;
                    }
                })
                .distinctUntilChanged()
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        return events.size();
                    }
                })
                .distinct()
                .observeOn(Schedulers.io())
                .map(new Function<Integer, Observable<EventDataWrapper>>() {
                    @Override
                    public Observable<EventDataWrapper> apply(@NonNull Integer lastItem) throws Exception {
                        return api.getEvents(characterId, lastItem);
                    }
                })
                .map(new Function<Observable<EventDataWrapper>, EventDataWrapper>() {
                    @Override
                    public EventDataWrapper apply(@NonNull Observable<EventDataWrapper> listFlowable) throws Exception {
                        return listFlowable.blockingFirst(null);
                    }
                })
                .map(new Function<EventDataWrapper, List<Event>>() {
                    @Override
                    public List<Event> apply(@NonNull EventDataWrapper eventDataWrapper) throws Exception {
                        List<es.goda87.marvel.api.models.Event> result = eventDataWrapper.getData().getResults();

                        if (result == null) { return events; }

                        for (es.goda87.marvel.api.models.Event apiEvent : result) {
                            Event event = apiToCoreEvent(apiEvent);
                            events.add(event);
                        }
                        return events;
                    }
                })
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    private Event apiToCoreEvent(es.goda87.marvel.api.models.Event apiEvent) {
        Event event = new Event();
        event.setId(apiEvent.getId());
        event.setName(apiEvent.getTitle());
        event.setDescription(apiEvent.getDescription());
        event.setImagePath(apiEvent.getThumbnail().getPath());
        event.setImageExtension(apiEvent.getThumbnail().getExtension());
        return event;
    }
}
