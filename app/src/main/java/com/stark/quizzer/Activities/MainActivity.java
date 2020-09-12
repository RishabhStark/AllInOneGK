package com.stark.quizzer.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.stark.quizzer.Fragments.CurrentAffairs;
import com.stark.quizzer.Fragments.Home_Fragment;
import com.stark.quizzer.Fragments.bookmark;
import com.stark.quizzer.Fragments.news_fragment;
import com.stark.quizzer.R;
import com.stark.quizzer.utility.NewsApi;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    public static NewsApi newsApi;
    public static Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        // bottomnavigation view item selected listner defined
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Home_Fragment home_fragment = new Home_Fragment();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, home_fragment).commit();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsApi = retrofit.create(NewsApi.class);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.gradientactionbar));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog)
                    .setTitle("Logout").setMessage("Are you sure,you want to Logout?").setPositiveButton("Logout"
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog1, int which) {


                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(MainActivity.this, Quizzer_signup.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("Cancel", null).show();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NotNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                getSupportActionBar().setTitle("Categories");
                selectedFragment = new Home_Fragment();
                break;

            case R.id.bm:
                getSupportActionBar().setTitle("Bookmarks");
                selectedFragment = new bookmark();
                break;
            case R.id.ca:
                getSupportActionBar().setTitle("CurrentAffairs");
                selectedFragment = new CurrentAffairs();
                break;
            case R.id.news:
                getSupportActionBar().setTitle("News");
                selectedFragment = new news_fragment();
                break;
            // Toast.makeText(this, "Available soon", Toast.LENGTH_SHORT).show();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (selectedFragment != null) {
            transaction.replace(R.id.frame, selectedFragment, "FTAG").commit();
        } else {
            Toast.makeText(this, "This feature will be available soon", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
