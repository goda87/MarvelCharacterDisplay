package es.goda87.marvelcharacterdisplay.ui;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import es.goda87.marvelcharacterdisplay.characterlist.CharactersAdapter;

public class CharactersListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static void setItems(RecyclerView listView, List<Character> items) {
        CharactersAdapter adapter = (CharactersAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.addItems(items);
        }
    }
}
