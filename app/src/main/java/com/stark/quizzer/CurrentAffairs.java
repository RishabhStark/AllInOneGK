package com.stark.quizzer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentAffairs extends Fragment implements DatePickerDialog.OnDateSetListener {
    DatabaseReference databaseReference;
    List<CurrentAffairs_Model> caList;
    RecyclerView recyclerView;
    CurrentAffairsAdapter currentAffairsAdapter;
    TextView date;
    Date datee=new Date();
    SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
    String strDate=format.format(datee);
    CoordinatorLayout coordinatorLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.current_affairs_fragment,container,false);
        date=v.findViewById(R.id.date);
        date.setText(strDate);
        coordinatorLayout=v.findViewById(R.id.cd);
        recyclerView=v.findViewById(R.id.recyclerview_caffairs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        currentAffairsAdapter=new CurrentAffairsAdapter(caList);
        recyclerView.setAdapter(currentAffairsAdapter);
         date.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DialogFragment datePicker=new com.stark.quizzer.DatePicker();
                 datePicker.show(getFragmentManager(),"date_picker");
             }
         });
        return v;
          }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData(strDate.replaceAll("-","xx"));

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
       // String currentDateString=DateFormat.getDateInstance().format(c.getTime());
        String currentDateString=new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
        date.setText(currentDateString);
        getData(currentDateString.replaceAll("-","xx"));



    }
    public void getData(final String strDate) {
        databaseReference= FirebaseDatabase.getInstance().getReference();
       if(caList==null) {
           caList=new ArrayList<>();
       }
       else {
           caList.clear();
       }

        databaseReference.child("CurrentAffairs").child(strDate)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            caList.add(new CurrentAffairs_Model(snapshot.child("title").getValue().toString(),snapshot.child("body").getValue().toString()));
                        }
                        if(caList.isEmpty()) {
                        Snackbar.make(coordinatorLayout,"Oops! Nothing to show...", Snackbar.LENGTH_INDEFINITE).show();

                        }
                       currentAffairsAdapter.notifyDataSetChanged();








                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),"Database Error",Toast.LENGTH_SHORT).show();



                    }
                });


    }


}
