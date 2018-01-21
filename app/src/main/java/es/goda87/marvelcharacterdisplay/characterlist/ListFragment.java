package es.goda87.marvelcharacterdisplay.characterlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersDataSource;
import es.goda87.marvelcharacterdisplay.databinding.FragmentListBinding;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class ListFragment extends Fragment {

    private FragmentListBinding fragmentListBinding;

    @BindView(R.id.list_view)
    RecyclerView listView;

    @BindView(R.id.search_box)
    EditText searchBox;

    private ListViewModel listViewModel;
    private CharactersDataSource repository;
    private CharacterDetailNavigator navigator;
    private CharactersAdapter adapter;
    private Disposable disposable = null;

    private ObservableEmitter<Integer> scrollObservableEmitter;

    void setViewModel(ListViewModel viewModel) {
        this.listViewModel = viewModel;
    }

    void setDetailNavigator(CharacterDetailNavigator navigator) {
        this.navigator = navigator;
    }

    void setDataRepository(CharactersDataSource repository) {
        this.repository = repository;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
    }

    private void setupListAdapter() {
        adapter = new CharactersAdapter(
                this.repository,
                this.navigator);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentListBinding = fragmentListBinding.inflate(inflater, container, false);
        fragmentListBinding.setView(this);
        fragmentListBinding.setViewmodel(listViewModel);

        View rootView = fragmentListBinding.getRoot();
        ButterKnife.bind(this, rootView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);

        Observable<Integer> o = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> observableEmitter) throws Exception {
                scrollObservableEmitter = observableEmitter;
                listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int lastPosition = layoutManager.findLastVisibleItemPosition();
                        observableEmitter.onNext(lastPosition);
                    }
                });
            }
        });


        Observable<String> searchObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
                searchBox.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        observableEmitter.onNext(s.toString());
                    }
                });
            }
        });

        searchObservable.debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                listViewModel.setSearch(s);
                scrollObservableEmitter.onNext(0);
            }
        });

        listViewModel.getCharacters(o);
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
