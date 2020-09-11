package com.stark.quizzer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.stark.quizzer.MainActivity.newsApi;

public class news_fragment extends Fragment {
    String key="e2ef015020784d15a53c5bd9255488de";
    RecyclerView newsrecyclerView;
    List<Articles> articlesList=new ArrayList<>();
    News_Adapter newsAdapter;
    Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.news_fragment,container,false);
        newsrecyclerView=v.findViewById(R.id.rv_news);
         newsAdapter=new News_Adapter(articlesList,getActivity());
        newsrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsrecyclerView.setAdapter(newsAdapter);
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.loadingdialog);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
        Call<news_data> newsDataCall=newsApi.getNews("in",key);
        newsDataCall.enqueue(new Callback<news_data>() {

            @Override
            public void onResponse(Call<news_data> call, Response<news_data> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                news_data newsData=response.body();
                int size=newsData.articles.length;
                for(int i=0;i<size;i++) {
                    articlesList.add(newsData.articles[i]);
                }
                newsAdapter.notifyDataSetChanged();
                dialog.dismiss();


            }

            @Override
            public void onFailure(Call<news_data> call, Throwable t) {
                Toast.makeText(getActivity(), "Couldn't fetch news!", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
