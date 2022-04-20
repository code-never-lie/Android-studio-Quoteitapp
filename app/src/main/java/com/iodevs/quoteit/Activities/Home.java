package com.iodevs.quoteit.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iodevs.quoteit.CustomAdapters.ImageQuoteAdapter;
import com.iodevs.quoteit.CustomAdapters.TextQuoteHomeAdapter;
import com.iodevs.quoteit.Model.Category;
import com.iodevs.quoteit.Model.ImageQuote;
import com.iodevs.quoteit.Model.TextQuote;
import com.iodevs.quoteit.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView textQuoteRecycler;
    private RecyclerView imageQuoteRecycler;

    private ArrayList<TextQuote> textQuotesList;
    private ArrayList<ImageQuote> imageQuoteList;
    private  ArrayList<TextQuote> favouriteTextList;


    private TextQuoteHomeAdapter textQuoteHomeAdapter;

    private Button latestTextMoreBtn,latestImageMoreBtn;
    private ImageButton search_bar_btn;
    private int incremenButton=0;
    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth mAuth;
    private FirebaseUser user;




    //Firebase
   private DatabaseReference myRootRef;
    private DatabaseReference textQuoteref;
    private DatabaseReference imageQuoteRef;
    private DatabaseReference categoryRef;
    private DatabaseReference topFourRef;
    private DatabaseReference favouriteQuoteRef;
  //  private ArrayList <TextQuote> list;

    private ImageView image1,image2,image3,image4;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.forget_Password_toolbar);
        setSupportActionBar(toolbar);
        //UI Intilaze
        mAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();

        initliazeAllUI();

        //RetrivingtextQuote
        retrivingTextQuote();
        //sendData();


      //  retrivingTextQuote();
        retrivingImageQuote();


        latestTextMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,LatestTextQuote.class);
                startActivity(intent);
            }
        });
        search_bar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,SearchQuote.class);
                startActivity(intent);
            }
        });



        latestImageMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //incremenButton++;
              //  sendData();
              // sendCategory();
               // sendImageQuote();

                top4Data();
                Intent intent=new Intent(Home.this,LatestImageQuote.class);
                startActivity(intent);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void top4Data() {
        ImageQuote temp=new ImageQuote();
        switch (incremenButton)
        {
            case 1:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/wonderful-inspirational-quotes-pictures-7-f35db3be.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushTop4(temp);
            }
            case 2:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/inspirational-art-quotes-images-for-tumblr-1-cea39e1a.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("best,Coach");
                pushTop4(temp);            }
            case 3:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/victory-quotes-pictures-9-c0a3bd9d.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushTop4(temp);            }

            case 4:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/retirement-quotes-wishes-and-sayings-pics-3-08efde8b.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushTop4(temp);
            }

    }
    }

    private void pushTop4(ImageQuote temp) {
        String pushKey=topFourRef.push().getKey();
        temp.setQuoteId(pushKey);

        topFourRef.child(pushKey).setValue(temp);

    }



    private void retrivingImageQuote() {

       final ChildEventListener imageQuote=new ChildEventListener() {
           ImageQuote imageQuote;

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    imageQuote = snapshot.getValue(ImageQuote.class);
                    imageQuoteList.add(imageQuote);
                    if (imageQuoteList.size() == 4) {
                        setImageQuote();
                    }
                    //  counter++;
                    Log.d("image_url", imageQuote.getImageUrl());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }




        };
        imageQuoteRef.orderByKey().limitToFirst(4).addChildEventListener(imageQuote);
    }
    public void setImageQuote()
    {

        ConvertImageIntoDrawable(0);
       ConvertImageIntoDrawable(1);
        ConvertImageIntoDrawable(2);
       ConvertImageIntoDrawable(3);

    }
    public void ConvertImageIntoDrawable(final int position)
    {
        Picasso.get().load(imageQuoteList.get(position).getImageUrl()).into(new Target() {
            @Override

            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Drawable drawImage = new BitmapDrawable(Home.this.getResources(),bitmap);

                switch (position)
                {
                    case 0:
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            image1.setBackground(drawImage);
                        }
                        break;
                    }
                    case 1:
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            image2.setBackground(drawImage);
                        }
                        break;
                    }
                    case 2:
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            image3.setBackground(drawImage);
                        }
                        break;
                    }
                    case 3:
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            image4.setBackground(drawImage);
                        }
                        break;
                    }
                }



            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }
    private void sendImageQuote() {
        ImageQuote temp=new ImageQuote();
        switch (incremenButton)
        {
            case 1:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/wonderful-inspirational-quotes-pictures-7-f35db3be.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushImageData(temp);
            }
            case 2:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/inspirational-art-quotes-images-for-tumblr-1-cea39e1a.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("best,Coach");
                pushImageData(temp);
            }
            case 3:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/victory-quotes-pictures-9-c0a3bd9d.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushImageData(temp);
            }

            case 4:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/retirement-quotes-wishes-and-sayings-pics-3-08efde8b.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushImageData(temp);
            }

            case 5:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/womderful-character-quotes-images-4-ef191f2f.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushImageData(temp);
            }

            case 6:
            {
                temp.setImageUrl("http://www.bestsayingsquotes.com/files/learning-growth-quotes-pictures-8-6cd7f606.jpg");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushImageData(temp);
            }

        }







    }

    private void pushImageData(ImageQuote imageQuote) {

        String pushKey=textQuoteref.push().getKey();
        imageQuote.setQuoteId(pushKey);

        imageQuoteRef.child(imageQuote.getCategoryName()).child(pushKey).setValue(imageQuote);
    }

    private void sendData() {
        TextQuote temp = new TextQuote();
        switch (incremenButton) {
            case 1:
                {

                temp.setQuoteText("There are times in my life when I just want to be by myself.");
                temp.setAuthorName("Aaliya");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }
            case 2:
            {

                temp.setQuoteText("It is useless to tell one not to reason but to believe - you might as well tell a man not to wake but sleep");
                temp.setAuthorName("willim");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }
            case 3:
            {

                temp.setQuoteText("Lovers may be - and indeed generally are - enemies, but they never can be friends, because there must always be a spice of jealousy and a something of Self in all their speculations");
                temp.setAuthorName("John");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }
            case 4:
            {

                temp.setQuoteText("People wonder why first-time directors can make a brilliant picture, then suck on the second one. It's because they're a little terrified the first time. So they listen to all the experts around them");
                temp.setAuthorName("Byron");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }
            case 5:
            {

                temp.setQuoteText("To me, theater is the mecca; if you really love to act, that's where it's the most fun, by a long shot.\n");
                temp.setAuthorName("Caan");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }
            case 6:
            {

                temp.setQuoteText("Building art is a synthesis of life in materialised form. We should try to bring in under the same hat not a splintered way of thinking, but all in harmony together.\n");
                temp.setAuthorName("Aames");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }
            case 7:
            {

                temp.setQuoteText("I'm cleaning toilets for $30 a day, because I needed that $30, and people are pointing at me, saying, Look at the big movie star. Look where he is now. I just said, I'm where God put me.\n");
                temp.setAuthorName("Ibrahim");
                temp.setCategoryName("Sports");
                temp.setTags("Action,war,Coach");
                pushData(temp);
            }

        }


      //  temp.setTags(sampletags);


    }

    private void pushData(TextQuote temp) {
        String pushKey=textQuoteref.push().getKey();
        temp.setQuoteId(pushKey);

        textQuoteref.child(temp.getCategoryName()).child(pushKey).setValue(temp);
        Log.d("pushkey_id",pushKey);

    }

    private void retrivingTextQuote() {


         final ChildEventListener textquotes= new ChildEventListener() {
             TextQuote temp;
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                     for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                         temp = snapshot.getValue(TextQuote.class);
                         textQuotesList.add(temp);



//               Log.d("mycheck",temp.getQuoteId());


                 }
                 favouriteQuoteRef.child(user.getUid()).child("Text Quote").addChildEventListener(new ChildEventListener() {
                     TextQuote text;
                     @Override
                     public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                         text=dataSnapshot.getValue(TextQuote.class);
                         favouriteTextList.add(text);
                         textQuoteHomeAdapter.notifyDataSetChanged();
                     }

                     @Override
                     public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                     }

                     @Override
                     public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                     }

                     @Override
                     public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });


             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         };

         textQuoteref.orderByKey().limitToFirst(1).addChildEventListener(textquotes);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(user.isAnonymous())
        {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.logout).setVisible(false);

        }


    }

    private void initliazeAllUI() {

        //textQuote RecyclerView
        textQuoteRecycler=(RecyclerView) findViewById(R.id.text_quotation_recyclerview);
        textQuotesList=new ArrayList <TextQuote>();
        favouriteTextList=new ArrayList <TextQuote>();
        textQuoteHomeAdapter=new TextQuoteHomeAdapter(this,textQuotesList,true,favouriteTextList);
        textQuoteRecycler.setLayoutManager(new LinearLayoutManager(this));
        textQuoteRecycler.setAdapter(textQuoteHomeAdapter);

        latestImageMoreBtn=(Button)findViewById(R.id.image_quote_more_btn);
        search_bar_btn=findViewById(R.id.search_bar_home_btn);

        //RecyclerView Fo Image Quote

       // imageQuoteRecycler=(RecyclerView)findViewById(R.id.imageQuoteRecycler);
        imageQuoteList=new ArrayList <ImageQuote>();
        //imageQuoteAdapter=new ImageQuoteAdapter(this,imageQuoteList);
        //imageQuoteRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       // imageQuoteRecycler.setAdapter(imageQuoteAdapter);

        //Buttons
        latestTextMoreBtn=(Button)findViewById(R.id.textQuote_more_btn) ;

        image1=(ImageView) findViewById(R.id.imageQuote_1);
        image2=(ImageView) findViewById(R.id.imageQuote_2);
        image3=(ImageView) findViewById(R.id.imageQuote_3);
        image4=(ImageView) findViewById(R.id.imageQuote_4);


        //Firebase
        myRootRef= FirebaseDatabase.getInstance().getReference();
        textQuoteref=myRootRef.child("Text Quote");
        imageQuoteRef=myRootRef.child("Image Quote");
        categoryRef=myRootRef.child("Category");
        topFourRef=myRootRef.child("Top 4");
        favouriteQuoteRef=myRootRef.child("Favourite Quote");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) ) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id)
        {
            case R.id.logout:
            {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Home.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

            }
            case R.id.favourite:
            {
                Intent intent=new Intent(Home.this,Favourite.class);
                startActivity(intent);
                break;
            }
            case  R.id.category:
            {
                Intent intent=new Intent(Home.this,AllCategory.class);
                startActivity(intent);


                break;

            }
            case R.id.notification:
            {
                Intent intent=new Intent(Home.this,Notification.class);
                startActivity(intent);


                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void sendCategory() {
        Category category =new Category();
        switch (incremenButton)
        {
            case 1:
            {
                category.setName("Motivational");
                category.setImage("https://images.yourstory.com/2018/02/motivation.jpg?auto=compress");
                PushCategory(category);
            }
            case 2:
            {
                category.setName("Joy");
                category.setImage("");
                PushCategory(category);
            }
            case 3:
            {
                category.setName("Sports");
                category.setImage("https://ichef.bbci.co.uk/images/ic/480xn/p06jb0m6.jpg");
                PushCategory(category);
            }
            case 4:
            {
                category.setName("Sea");
                category.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSw6UAlhjHj9payX_9OA1JK4lop7pYAbal89F3abRCxdb_NZpbH");
                PushCategory(category);
            }
            case 5:
            {
                category.setName("Motivational");
                category.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfV2pvr_lxRbsVYej6Eo-998kUE8UM0S10gD-a4n0bKAsw_piq");
                PushCategory(category);
            }
            case 6:
            {
                category.setName("Hard Working");
                category.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4fROy13cLDl60yzHEYdkWWPI5o_b-s_38qE4IHPJzLQGvv9vn");
                PushCategory(category);
            }
            case 7:
            {
                category.setName("Sad");
                category.setImage("");
                PushCategory(category);
            }
            case 8:
            {
                category.setName("Love");
                category.setImage("");
                PushCategory(category);
            }
            case 9:
            {
                category.setName("Friends");
                category.setImage("");
                PushCategory(category);
            }
            case 10:
            {
                category.setName("Health");
                category.setImage("");
                PushCategory(category);

            }

        }



    }
    public void PushCategory(Category category)
    {
        categoryRef.child(category.getName()).setValue(category);

    }
    public  void favourite(int position)
    {

        textQuotesList.remove(position);
        textQuoteHomeAdapter.notifyDataSetChanged();
    }
}
