package es.goda87.marvelcharacterdisplay.characterlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import es.goda87.marvelcharacterdisplay.MarvelCharactersApplication;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.base.ViewModelHolder;
import es.goda87.marvelcharacterdisplay.characterdetail.DetailActivity;
import es.goda87.marvelcharacterdisplay.data.characters.source.CharactersRepository;

public class ListActivity extends AppCompatActivity implements CharacterDetailNavigator {

    private static final String FRAGMENT_LIST = "List";
    private static final String FRAGMENT_LIST_VIEWMODEL = "List_ViewModel";

    @Inject ListViewModel listViewModel;
    @Inject CharactersRepository charactersRepository;
    @Inject CharacterDetailNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerListComponent.builder()
                .charactersDataComponent(((MarvelCharactersApplication) getApplication()).getDataComponent())
                .characterDetailNavigatorModule(new CharacterDetailNavigatorModule(this))
                .build().inject(this);

        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment listFragment = fragmentManager.findFragmentByTag(FRAGMENT_LIST);

        if (listFragment == null) {
            listFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .add(R.id.content_frame, listFragment, FRAGMENT_LIST)
                    .commit();
        }

        // Link View and ViewModel
        ((ListFragment) listFragment).setViewModel(findOrCreateViewModel());
        ((ListFragment) listFragment).setDataRepository(charactersRepository);
        ((ListFragment) listFragment).setDetailNavigator(navigator);
    }

    @Override
    protected void onDestroy() {
        listViewModel.onActivityDestroyed();
        super.onDestroy();
    }

    @Override
    public void openItemDetailsPage(int id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_CHARACTER_ID, id);
        startActivity(intent);
    }

    private ListViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<ListViewModel> retainedViewModel =
                (ViewModelHolder<ListViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(FRAGMENT_LIST_VIEWMODEL);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            ListViewModel viewModel = listViewModel;
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(ViewModelHolder.createContainer(viewModel), FRAGMENT_LIST_VIEWMODEL);
            transaction.commit();

            return viewModel;
        }
    }
}
