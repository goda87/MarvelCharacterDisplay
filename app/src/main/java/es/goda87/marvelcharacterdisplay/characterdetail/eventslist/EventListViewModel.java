package es.goda87.marvelcharacterdisplay.characterdetail.eventslist;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsRepository;
import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by goda87 on 21.01.18.
 */

public class EventListViewModel extends BaseObservable {

    public final ObservableList<Event> items = new ObservableArrayList<>();

    private EventsRepository manager;
    private Disposable disposable = null;

    @Inject
    public EventListViewModel(EventsRepository eventsRepository) {
        this.manager = eventsRepository;
    }

    void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void getEvents(Observable<Integer> scrollObservable) {
        disposable = manager.getEvents(scrollObservable.startWith(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(@NonNull List<Event> events) throws Exception {
                        updateList(events);
                    }
                });
    }

    private synchronized void updateList(@NonNull List<Event> events) {

        for (Event c : events) {
            int index = items.indexOf(c);
            if (index > -1) {
                items.remove(index);
                items.add(c);
            } else {
                items.add(c);
            }
        }
    }
}
