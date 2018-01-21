package es.goda87.marvelcharacterdisplay.characterdetail.eventslist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsDataSource;

/**
 * Created by goda87 on 21.01.18.
 */

public class EventViewModel extends BaseObservable {

    protected final ObservableField<Event> eventObservable = new ObservableField<>();

    EventsDataSource eventsDataSource;

    private boolean mIsDataLoading;

    public EventViewModel(EventsDataSource eventsDataSource) {
        this.eventsDataSource = eventsDataSource;
    }

    public void setEvent(Event event) {
        eventObservable.set(event);
    }

    @Bindable
    public String getName() {
        return eventObservable.get().getName();
    }

    @Bindable
    public String getDescription() {
        return eventObservable.get().getDescription();
    }

    @Bindable
    public String getThumbnail() {
        return eventObservable.get().thumbnailPath();
    }
}
