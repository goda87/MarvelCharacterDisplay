package es.goda87.marvelcharacterdisplay.characterdetail.eventslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.events.model.Event;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsDataSource;
import es.goda87.marvelcharacterdisplay.databinding.ListItemEventBinding;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.Holder>{

    private final EventsDataSource provider;

    @Inject
    EventsAdapter(EventsDataSource provider) {
        this.provider = provider;
    }

    private final List<Event> items = new ArrayList<>();

    public void addItems(List<Event> newItems) {
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
        ListItemEventBinding binding = ListItemEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        EventViewModel itemViewModel = new EventViewModel(provider);
        itemViewModel.setEvent(items.get(position));
        holder.bind(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private final ListItemEventBinding binding;

        Holder(ListItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventViewModel viewModel) {
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }
}
