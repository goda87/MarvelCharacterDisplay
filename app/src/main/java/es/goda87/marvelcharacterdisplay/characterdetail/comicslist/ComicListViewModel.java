package es.goda87.marvelcharacterdisplay.characterdetail.comicslist;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by goda87 on 21.01.18.
 */

public class ComicListViewModel extends BaseObservable {

    public final ObservableList<Comic> items = new ObservableArrayList<>();

    private ComicsRepository manager;
    private Disposable disposable = null;

    @Inject
    public ComicListViewModel(ComicsRepository comicsRepository) {
        this.manager = comicsRepository;
    }

    void onActivityDestroyed() {
        // Clear references to avoid potential memory leaks.
        if (disposable != null) {
            disposable.dispose();
        }
    }

    void getComics(Observable<Integer> scrollObservable) {
        disposable = manager.getComics(scrollObservable.startWith(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(@NonNull List<Comic> comics) throws Exception {
                        updateList(comics);
                    }
                });
    }

    private synchronized void updateList(@NonNull List<Comic> comics) {

        for (Comic c : comics) {
            int index = items.indexOf(c);
            if (index > -1) {
                items.remove(index);
                items.add(c);
            } else {
                items.add(c);
            }
        }
    }
}
