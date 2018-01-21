package es.goda87.marvelcharacterdisplay.characterlist;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersDataSource;
import es.goda87.marvelcharacterdisplay.databinding.ListItemCharacterBinding;

public class CharactersAdapter  extends RecyclerView.Adapter<CharactersAdapter.Holder> {

    @Nullable private CharacterDetailNavigator navigator;
    private final CharactersDataSource provider;

    @Inject
    CharactersAdapter(CharactersDataSource provider, CharacterDetailNavigator navigator) {
        this.provider = provider;
        this.navigator = navigator;
    }

    private final List<Character> items = new ArrayList<>();

    public void addItems(List<Character> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemCharacterBinding binding = ListItemCharacterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ItemViewModel itemViewModel = new ItemViewModel(
                provider, navigator);
        itemViewModel.setCharacter(items.get(position));
        holder.bind(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private final ListItemCharacterBinding binding;

        Holder(ListItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ItemViewModel viewModel) {
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }
}
