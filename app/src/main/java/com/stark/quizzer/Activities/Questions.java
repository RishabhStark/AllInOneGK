package com.stark.quizzer.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stark.quizzer.ModelClasses.QuestionModel;
import com.stark.quizzer.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Questions extends AppCompatActivity {
    public static final String FILE_NAME="QUIZZER";
    public static final String KEY_NAME="QUESTIONS";
private Toolbar toolbar;
private SharedPreferences preferences;
private SharedPreferences.Editor editor;
private Gson gson;
public static List<QuestionModel> bookmarksList;
private Dialog dialog;
private int score=0;
private int position=0;
private Button share,next;
private TextView question,noindicator;
private FloatingActionButton fab;
private LinearLayout optionscontainer;
private int count=0;
private List<QuestionModel> list;
private String setId;
private int matchedQuestionPosition;
private String category;
private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.loadingdialog);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();
        question=findViewById(R.id.question);
        noindicator=findViewById(R.id.score);
        fab=findViewById(R.id.fab);
        share=findViewById(R.id.button5);
        next=findViewById(R.id.button7);
        setId=getIntent().getStringExtra("setId");
        getBookmarks();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Challenge sent from Quizzer App by Stark inc.. \n"+"Question: "+list.get(position).getQuestion()
                +"\n"+"a. "+list.get(position).getA()
                        +"\n"+"b."+list.get(position).getB()
                        +"\n"+"c. "+list.get(position).getC()
                        +"\n"+"d. "+list.get(position).getD());
                startActivity(shareIntent.createChooser(shareIntent,"Choose App to share"));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(modelMatch())
                {
                  bookmarksList.remove(matchedQuestionPosition);
                  //change bookmark drawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getDrawable(R.drawable.ic_bookmark_border));                    }

                }
                else
                {
                    bookmarksList.add(list.get(position));
                    //change bookmark drawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getDrawable(R.drawable.bookmarksolid));
                    }
                }
            }
        });
        category=getIntent().getStringExtra("category");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        dialog.show();
        databaseReference.child("SETS").child(setId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String question=snapshot.child("question").getValue().toString();
                    String A=snapshot.child("optionA").getValue().toString();
                    String B=snapshot.child("optionB").getValue().toString();
                    String C=snapshot.child("optionC").getValue().toString();
                    String D=snapshot.child("optionD").getValue().toString();
                    String correctANS=snapshot.child("correctAnswer").getValue().toString();
                    String id=snapshot.getKey();
                    list.add(new QuestionModel(id,question,A,B,C,D,correctANS,setId) );
                }
                if(list.size()>0)
                {

                   // System.out.println("hello"+list.get(0).getAnswer()());

                    for(int i=0;i<4;i++)
                    {
                        optionscontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);

                            }
                        });
                    }
                    playAnim(question,0,list.get(position).getQuestion());
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            enableOption(true);
                            //next click listner
                            if(position==list.size()-1)
                            {
                                // intent for score activity...
                                Intent scoreIntent=new Intent(Questions.this,ScoreActivity.class);
                                scoreIntent.putExtra("score",score);
                                scoreIntent.putExtra("total",list.size());
                                startActivity(scoreIntent);
                                finish();
                                return;
                            }
                            next.setEnabled(false);
                            next.setAlpha(0.7f);
                            position++;
                            count=0;
                            playAnim(question,0,list.get(position).getQuestion());
                        }
                    });

                }
                else
                {
                    finish();
                    Toast.makeText(Questions.this,"No Questions in "+category,Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Questions.this, "DataBase Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();

            }
        });
        optionscontainer=findViewById(R.id.button_container);
        // questionlist

        list=new ArrayList<>();




    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();

    }

    private void playAnim(final View view, final int value, final String data)
    {
        for(int i=0;i<4;i++)
        {
            optionscontainer.getChildAt(i).setBackground(null);
        }

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value==0 && count<4)
                { String option="";
                    if(count==0)
                        option=list.get(position).getA();
                    else if(count==1)
                        option=list.get(position).getB();
                    else if(count==2)
                        option=list.get(position).getC();
                    else if(count==3)
                        option=list.get(position).getD();


                    playAnim(optionscontainer.getChildAt(count),0,option);
                      count++;
                }


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // data change


                if(value==0)
                {   try {
                    ((TextView) view).setText(data);
                    noindicator.setText(position+1+"/"+list.size());
                    if(modelMatch())
                    {

                        //change bookmark drawable as question is already present
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fab.setImageDrawable(getDrawable(R.drawable.bookmarksolid));
                        }


                    }
                    else
                    {
                        //change bookmark drawable
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            fab.setImageDrawable(getDrawable(R.drawable.ic_bookmark_border));
                        }



                    }


                }
                catch (ClassCastException c)
                {

                    ((Button) view).setText(data);

                }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
                else
                {
                    enableOption(true);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void checkAnswer(Button  selectedOption)
    {
        enableOption(false); // disabling all buttons
        next.setEnabled(true); // disabling next button
        next.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getAnswer()))
        {
            //correct
            selectedOption.setBackground(getResources().getDrawable(R.drawable.correct));
            score++;

        }
        else
        {
            //incorrect
            selectedOption.setBackground(getResources().getDrawable(R.drawable.incorrect));
          Button correctOption=(Button)  optionscontainer.findViewWithTag(list.get(position).getAnswer());
          // shows correct answer after user clicks on wrong answer...
          correctOption.setBackground(getResources().getDrawable(R.drawable.correct));

        }



    }
    private void enableOption(boolean enable)
    {
        // disable all other options if user clicks on one option
        for(int i=0;i<4;i++)
        {
            if(enable==true)
            {optionscontainer.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.options_border));

            }

            optionscontainer.getChildAt(i).setEnabled(enable);

        }
    }
    private void getBookmarks()
    {
        String json=preferences.getString(KEY_NAME,"");
        Type type=new TypeToken<List<QuestionModel>>(){}.getType();
        bookmarksList=gson.fromJson(json,type);
        if(bookmarksList==null)
        {
            bookmarksList=new ArrayList<>();
        }

    }
    private void storeBookmarks()
    {
        String json=gson.toJson(bookmarksList);
        editor.putString(KEY_NAME,json);
        editor.commit();
    }
    private boolean modelMatch()
    {
        boolean matched =false;
        int i=0;
        for(QuestionModel model:bookmarksList)
        {
            if(model.getQuestion().equals(list.get(position).getQuestion()) && model.getAnswer().equals(list.get(position).getAnswer()) && model.getSet().equals(list.get(position).getSet()))
            {
                matched=true;
                matchedQuestionPosition=i;
            }
            i++;
        }
        return  matched;

    }
}
