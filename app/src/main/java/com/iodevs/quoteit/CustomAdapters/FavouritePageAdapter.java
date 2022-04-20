package com.iodevs.quoteit.CustomAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iodevs.quoteit.Fragments.FavouriteImageQuote;
import com.iodevs.quoteit.Fragments.FavouriteTextQuote;

/**
 * Created by Touseef Rao on 9/18/2018.
 */

public class FavouritePageAdapter extends FragmentStatePagerAdapter {

    int mNoOfQuoteit;

    public FavouritePageAdapter(FragmentManager fm, int NumOfQuoteit)
    {
        super(fm);
        this.mNoOfQuoteit = NumOfQuoteit;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                FavouriteTextQuote favouriteTextQuote = new FavouriteTextQuote();
                return favouriteTextQuote;
            case 1:
                FavouriteImageQuote favouriteImageQuote = new FavouriteImageQuote();
                return favouriteImageQuote;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }}