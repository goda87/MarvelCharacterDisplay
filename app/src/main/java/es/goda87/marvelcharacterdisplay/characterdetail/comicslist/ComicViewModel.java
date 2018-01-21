package es.goda87.marvelcharacterdisplay.characterdetail.comicslist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import es.goda87.marvelcharacterdisplay.data.comics.model.Comic;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsDataSource;

/**
 * Created by goda87 on 21.01.18.
 */

public class ComicViewModel extends BaseObservable {

    protected final ObservableField<Comic> comicObservable = new ObservableField<>();

    ComicsDataSource comicsDataSource;

    private boolean mIsDataLoading;

    public ComicViewModel(ComicsDataSource comicsDataSource) {
        this.comicsDataSource = comicsDataSource;
    }

    public void setComic(Comic comic) {
        comicObservable.set(comic);
    }

    @Bindable
    public String getName() {
        return comicObservable.get().getName();
    }

    @Bindable
    public String getDescription() {
        return comicObservable.get().getDescription();
    }

    @Bindable
    public String getThumbnail() {
        return comicObservable.get().thumbnailPath();
    }
}
