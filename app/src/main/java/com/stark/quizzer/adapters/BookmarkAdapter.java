package com.stark.quizzer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stark.quizzer.ModelClasses.QuestionModel;
import com.stark.quizzer.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarkAdapter  extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {
    private List<QuestionModel> list;
    private onDeleteListner mlistner;

    public BookmarkAdapter(List<QuestionModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarl_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.question.setText(list.get(position).getQuestion());
        holder.answer.setText(list.get(position).getAnswer());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

        class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView question,answer;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            delete=itemView.findViewById(R.id.delete);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistner.onCLick(getAdapterPosition(),list);
                }
            });

         }}
    public interface onDeleteListner
    {
        void onCLick(int position,List<QuestionModel> list);
    }
    public void setOnDelteListner(onDeleteListner listner)
    {
        mlistner=listner;
    }



}
