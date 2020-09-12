package com.stark.quizzer.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.quizzer.ModelClasses.CategoriesModel;
import com.stark.quizzer.R;
import com.stark.quizzer.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home_Fragment extends Fragment
{   RecyclerView recyclerView;
private Dialog dialog;
public String tag="home";
public static List<CategoriesModel> categoriesModelList;
RecyclerViewAdapter recyclerViewAdapter;
DatabaseReference databaseReference;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.home_fragment,container,false);
       recyclerView=v.findViewById(R.id.recyclerview_home);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.loadingdialog);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
       dialog.setCancelable(false);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        categoriesModelList=new ArrayList<>();
        dialog.show();
        databaseReference.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<String> sets=new ArrayList<>();
                    for(DataSnapshot snapshot1:snapshot.child("sets").getChildren())
                    {
                        sets.add(snapshot1.getKey());
                    }
                    categoriesModelList.add(new CategoriesModel(snapshot.child("name").getValue().toString(),sets,
                            snapshot.child("image").getValue().toString(), snapshot.getKey()));

                }
                recyclerViewAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Database Error",Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerViewAdapter=new RecyclerViewAdapter(categoriesModelList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListner(new RecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onClick(int position,String title,int sets) {
                Intent intent=new Intent(getActivity(), com.stark.quizzer.Activities.sets.class);
                intent.putExtra("title",title);
                intent.putExtra("position",position);
                startActivity(intent);
               // Toast.makeText(getActivity(),title,Toast.LENGTH_SHORT).show();
            }
        });



    }
}
