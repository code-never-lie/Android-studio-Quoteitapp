package com.iodevs.quoteit.CustomAdapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.iodevs.quoteit.Fragments.FavouriteImageQuote;
import com.iodevs.quoteit.Fragments.FavouriteTextQuote;
import com.iodevs.quoteit.Fragments.ImageQuote;
import com.iodevs.quoteit.Fragments.TextQuote;
import com.iodevs.quoteit.R;

/**
 * Created by Touseef Rao on 9/19/2018.
 */

public class QuotePageAdapter extends FragmentStatePagerAdapter {

    int mNoOfQuoteit;
    String Category;
    String check;
    Bundle bundle = new Bundle();
    Bundle checkBundle=new Bundle();

    public QuotePageAdapter(FragmentManager fm, int NumOfQuoteit,String Category,String check)
    {
        super(fm);
        this.mNoOfQuoteit = NumOfQuoteit;
        this.Category=Category;
        this.check=check;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                TextQuote textQuote = new TextQuote();


                Log.d("check_dataq",Category);
                bundle.putString("category", Category );
                bundle.putString("check",check);
                textQuote.setArguments(bundle);

                //fragmentManager.beginTransaction().replace(R.id.fragment_text_quote, fragInfo).commit();
                return textQuote;
            case 1:
                ImageQuote imageQuote = new ImageQuote();
                bundle.putString("category", Category );

                imageQuote.setArguments(bundle);
                return imageQuote;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }}

