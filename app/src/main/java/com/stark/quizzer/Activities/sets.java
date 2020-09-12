package com.stark.quizzer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.stark.quizzer.Fragments.Home_Fragment;
import com.stark.quizzer.R;
import com.stark.quizzer.adapters.GridAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class sets extends AppCompatActivity {
private Toolbar toolbar;
private GridView gridView;
private GridAdapter adapter;
private Intent intent;
private List<String> sets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent=getIntent();
        sets= Home_Fragment.categoriesModelList.get(intent.getIntExtra("position",0)).getSets();
        getSupportActionBar().setTitle(intent.getStringExtra("title"));
        gridView=findViewById(R.id.grid);
        adapter=new GridAdapter(sets,intent.getStringExtra("title"));
        gridView.setAdapter(adapter);


    }
}
