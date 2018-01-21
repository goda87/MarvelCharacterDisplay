package es.goda87.marvelcharacterdisplay.characterdetail.comicslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsDataSource;
import es.goda87.marvelcharacterdisplay.databinding.ListItemComicBinding;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.Holder>{

    private final ComicsDataSource provider;

    @Inject
    ComicsAdapter(ComicsDataSource provider) {
        this.provider = provider;
    }

    private final List<Comic> items = new ArrayList<>();

    public void addItems(List<Comic> newItems) {
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
        ListItemComicBinding binding = ListItemComicBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ComicViewModel itemViewModel = new ComicViewModel(provider);
        itemViewModel.setComic(items.get(position));
        holder.bind(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private final ListItemComicBinding binding;

        Holder(ListItemComicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ComicViewModel viewModel) {
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }
}
