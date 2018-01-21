package es.goda87.marvelcharacterdisplay.characterdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.goda87.marvelcharacterdisplay.MarvelCharactersApplication;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.characterdetail.comicslist.ComicListFragment;
import es.goda87.marvelcharacterdisplay.characterdetail.comicslist.ComicListViewModel;
import es.goda87.marvelcharacterdisplay.characterdetail.eventslist.EventListFragment;
import es.goda87.marvelcharacterdisplay.characterdetail.eventslist.EventListViewModel;
import es.goda87.marvelcharacterdisplay.data.comics.source.ComicsRepository;
import es.goda87.marvelcharacterdisplay.data.comics.source.remote.MarvelApiComicsDataSource;
import es.goda87.marvelcharacterdisplay.data.events.source.EventsRepository;
import es.goda87.marvelcharacterdisplay.data.events.source.remote.MarvelApiEventsDataSource;


public class DetailActivity extends AppCompatActivity implements WebNavigator {

    public static final String EXTRA_CHARACTER_ID = "CHARACTER_ID";

    public static final String CHARACTERDETAIL_VIEWMODEL_TAG = "CHARACTERDETAIL_VIEWMODEL_TAG";

    @Inject DetailViewModel detailViewModel;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setupToolbar();


        DaggerDetailComponent.builder()
                .charactersDataComponent(((MarvelCharactersApplication) getApplication()).getDataComponent())
                .webNavigatorModule(new WebNavigatorModule(this))
                .build().inject(this);

        DetailFragment detailFragment = findOrCreateViewFragment();

        setupViewPager();
        //TODO bind tabs to viewmodel

        // Link View and ViewModel
        detailFragment.setViewModel(detailViewModel);

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
    }

    private void setupViewPager() {

        int characterId = getIntent().getIntExtra(EXTRA_CHARACTER_ID, 0);
        String publicKey = getResources().getString(R.string.MARVEL_PUBLICKEY);
        String privateKey = getResources().getString(R.string.MARVEL_PRIVATEKEY);

        MarvelApiComicsDataSource apiComic = new MarvelApiComicsDataSource(publicKey, privateKey);
        apiComic.setCharacterId(characterId);
        ComicsRepository comicsRepository = new ComicsRepository(apiComic);
        ComicListViewModel comicListViewModel = new ComicListViewModel(comicsRepository);
        ComicListFragment comicListFragment = new ComicListFragment();
        comicListFragment.setRepository(comicsRepository);
        comicListFragment.setComicListViewModel(comicListViewModel);

        MarvelApiEventsDataSource apiEvent = new MarvelApiEventsDataSource(publicKey, privateKey);
        apiEvent.setCharacterId(characterId);
        EventsRepository eventsRepository = new EventsRepository(apiEvent);
        EventListViewModel eventListViewModel = new EventListViewModel(eventsRepository);
        EventListFragment eventListFragment = new EventListFragment();
        eventListFragment.setRepository(eventsRepository);
        eventListFragment.setEventListViewModel(eventListViewModel);

        detailViewModel.startSync(characterId);

        List<CharSequence> titles = new ArrayList<>(2);
        titles.add(getResources().getString(R.string.details_tabs_Comics, detailViewModel.getNumberOfComics()));
        titles.add(getResources().getString(R.string.details_tabs_Events, detailViewModel.getNumberOfEvents()));

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), titles, comicListFragment, eventListFragment);
        pager.setAdapter(adapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(pager);
    }

    @NonNull
    private DetailFragment findOrCreateViewFragment() {
        // Get the requested task id
        int characterId = getIntent().getIntExtra(EXTRA_CHARACTER_ID, 0);

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance(characterId);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, detailFragment);
            transaction.commit();
        }
        return detailFragment;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void openWeb(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.d("GODA",  "GODA URL " + url);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
