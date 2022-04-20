package com.iodevs.quoteit.Activities;

import android.app.FragmentManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.iodevs.quoteit.CustomAdapters.FavouritePageAdapter;
import com.iodevs.quoteit.CustomAdapters.QuotePageAdapter;
import com.iodevs.quoteit.Fragments.ImageQuote;
import com.iodevs.quoteit.Fragments.TextQuote;
import com.iodevs.quoteit.R;

public class Quote extends AppCompatActivity implements TextQuote.OnFragmentInteractionListener,ImageQuote.OnFragmentInteractionListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private QuotePageAdapter adapter;

    private  static android.support.v4.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        String categoryName = getIntent().getStringExtra("category_Name");
        String searchtag=getIntent().getStringExtra("searchquote");
        Toolbar toolbar = (Toolbar) findViewById(R.id.quote_toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.quote_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Text"));
        tabLayout.addTab(tabLayout.newTab().setText("Image"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new QuotePageAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),categoryName,searchtag);
        viewPager = (ViewPager)findViewById(R.id.quote_category);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(adapter);



        //now replace the






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
