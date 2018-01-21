package es.goda87.marvelcharacterdisplay.characterdetail.eventslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsDataSource;
import es.goda87.marvelcharacterdisplay.databinding.FragmentEventListBinding;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by goda87 on 21.01.18.
 */

public class EventListFragment extends Fragment {

    private FragmentEventListBinding fragmentEventListBinding;

    @BindView(R.id.list_view)
    RecyclerView listView;

    private EventListViewModel eventListViewModel;
    private EventsDataSource repository;
    private EventsAdapter adapter;
    private Disposable disposable = null;

    public void setEventListViewModel(EventListViewModel eventListViewModel) {
        this.eventListViewModel = eventListViewModel;
    }

    public void setRepository(EventsDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        adapter = new EventsAdapter(
                this.repository);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentEventListBinding = FragmentEventListBinding.inflate(inflater, container, false);
        fragmentEventListBinding.setView(this);
        fragmentEventListBinding.setViewmodel(eventListViewModel);

        View rootView = fragmentEventListBinding.getRoot();
        ButterKnife.bind(this, rootView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);

        Observable<Integer> o = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> observableEmitter) throws Exception {
                listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int lastPosition = layoutManager.findLastVisibleItemPosition();
                        observableEmitter.onNext(lastPosition);
                    }
                });
            }
        }).distinct();

        eventListViewModel.getEvents(o);
        return rootView;
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
