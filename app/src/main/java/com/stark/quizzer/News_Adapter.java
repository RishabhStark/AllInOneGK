package com.stark.quizzer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.MyNewsViewHolder> implements View.OnClickListener {
    List<Articles> news;
    Context mcontext;

    public News_Adapter(List<Articles> news, Context mcontext) {
        this.news = news;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.news_item,parent,false);
        News_Adapter.MyNewsViewHolder holder=new News_Adapter.MyNewsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewsViewHolder holder, int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.body.setText(news.get(position).getDescription());
        holder.link.setText(news.get(position).getUrl());
        holder.source.setText("Source: "+news.get(position).getSource().getName());

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onClick(View v) {

    }

    class MyNewsViewHolder extends RecyclerView.ViewHolder {
       TextView title,body,link,source;
        public MyNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            body=itemView.findViewById(R.id.body);
            link=itemView.findViewById(R.id.link);
            source=itemView.findViewById(R.id.source);
        }
    }
}
