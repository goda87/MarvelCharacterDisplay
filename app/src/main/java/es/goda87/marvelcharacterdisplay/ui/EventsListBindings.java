package es.goda87.marvelcharacterdisplay.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import es.goda87.marvelcharacterdisplay.characterdetail.eventslist.EventsAdapter;
import es.goda87.marvelcharacterdisplay.data.events.model.Event;

/**
 * Created by goda87 on 21.01.18.
 */

public class EventsListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static void setItems(RecyclerView listView, List<Event> items) {
        EventsAdapter adapter = (EventsAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.addItems(items);
        }
    }
}
