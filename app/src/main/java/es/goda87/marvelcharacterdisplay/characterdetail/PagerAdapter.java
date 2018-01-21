package es.goda87.marvelcharacterdisplay.characterdetail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import es.goda87.marvelcharacterdisplay.R;

/**
 * Created by goda87 on 21.01.18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<CharSequence> titles;
    private Fragment[] fragmentsList;

    public PagerAdapter(FragmentManager fm, List<CharSequence> titles, Fragment... fragments) {
        super(fm);
        this.titles = titles;
        this.fragmentsList = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentsList[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.length;
    }
}
