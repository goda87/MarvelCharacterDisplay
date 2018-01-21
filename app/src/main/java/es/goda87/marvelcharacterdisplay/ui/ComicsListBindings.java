package es.goda87.marvelcharacterdisplay.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import es.goda87.marvelcharacterdisplay.characterdetail.comicslist.ComicsAdapter;
import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;

/**
 * Created by goda87 on 21.01.18.
 */

public class ComicsListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static void setItems(RecyclerView listView, List<Comic> items) {
        ComicsAdapter adapter = (ComicsAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.addItems(items);
        }
    }
}
