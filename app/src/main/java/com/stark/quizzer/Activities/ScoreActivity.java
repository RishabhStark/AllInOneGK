package com.stark.quizzer.Activities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stark.quizzer.R;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {
    private TextView score,total;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score=findViewById(R.id.text_view3);
        total=findViewById(R.id.text_view4);
        done=findViewById(R.id.done);
        score.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText(String.valueOf("OUT OF"+" "+getIntent().getIntExtra("total",0)));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
