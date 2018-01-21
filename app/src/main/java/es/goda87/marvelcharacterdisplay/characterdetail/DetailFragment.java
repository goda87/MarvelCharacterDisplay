package es.goda87.marvelcharacterdisplay.characterdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import es.goda87.marvelcharacterdisplay.R;
import es.goda87.marvelcharacterdisplay.databinding.FragmentDetailBinding;


public class DetailFragment extends Fragment {

    public static final String ARGUMENT_CHARACTER_ID = "CHARACTER_ID";

    private DetailViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        FragmentDetailBinding viewDataBinding = FragmentDetailBinding.bind(rootView);
        viewDataBinding.setViewmodel(viewModel);

        return rootView;
    }

    public static DetailFragment newInstance(int characterId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_CHARACTER_ID, characterId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public void setViewModel(DetailViewModel detailViewModel) {
        viewModel = detailViewModel;
    }



    @Override
    public void onResume() {
        super.onResume();
        viewModel.start(getArguments().getInt(ARGUMENT_CHARACTER_ID));
    }
}
