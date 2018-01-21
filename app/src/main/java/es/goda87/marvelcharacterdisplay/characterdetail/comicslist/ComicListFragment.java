package es.goda87.marvelcharacterdisplay.characterdetail.comicslist;

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
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsDataSource;
import es.goda87.marvelcharacterdisplay.databinding.FragmentComicListBinding;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by goda87 on 21.01.18.
 */

public class ComicListFragment extends Fragment {

    private FragmentComicListBinding fragmentComicListBinding;

    @BindView(R.id.list_view)
    RecyclerView listView;

    private ComicListViewModel comicListViewModel;
    private ComicsDataSource repository;
    private ComicsAdapter adapter;
    private Disposable disposable = null;

    public void setComicListViewModel(ComicListViewModel comicListViewModel) {
        this.comicListViewModel = comicListViewModel;
    }

    public void setRepository(ComicsDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        adapter = new ComicsAdapter(
                this.repository);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentComicListBinding = FragmentComicListBinding.inflate(inflater, container, false);
        fragmentComicListBinding.setView(this);
        fragmentComicListBinding.setViewmodel(comicListViewModel);

        View rootView = fragmentComicListBinding.getRoot();
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

        comicListViewModel.getComics(o);
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
