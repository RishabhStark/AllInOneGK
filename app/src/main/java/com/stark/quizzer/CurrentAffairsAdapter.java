package com.stark.quizzer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CurrentAffairsAdapter extends RecyclerView.Adapter<CurrentAffairsAdapter.CAViewHolder> {
    List<CurrentAffairs_Model> list;

    public CurrentAffairsAdapter(List<CurrentAffairs_Model> calist) {
        list=calist;
    }

    @NonNull
    @Override
    public CAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.caffairs,parent,false);
        CAViewHolder holder=new CAViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CAViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.body.setText(list.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CAViewHolder extends RecyclerView.ViewHolder {
        TextView title,body;
        public CAViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            body=itemView.findViewById(R.id.body);

        }
    }

   }
