package com.iodevs.quoteit.Activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.iodevs.quoteit.CustomAdapters.FavouritePageAdapter;
import com.iodevs.quoteit.Fragments.FavouriteImageQuote;
import com.iodevs.quoteit.Fragments.FavouriteTextQuote;
import com.iodevs.quoteit.R;

public class Favourite extends AppCompatActivity implements FavouriteImageQuote.OnFragmentInteractionListener,FavouriteTextQuote.OnFragmentInteractionListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FavouritePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);



        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Favourite_toolbar);
        toolbar.setTitle("Favourite");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Text"));
        tabLayout.addTab(tabLayout.newTab().setText("Image"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new FavouritePageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager = (ViewPager)findViewById(R.id.favourite_pager);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);




        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });














    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
