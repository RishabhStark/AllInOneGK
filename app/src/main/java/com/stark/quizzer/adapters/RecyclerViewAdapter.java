package com.stark.quizzer.adapters;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stark.quizzer.ModelClasses.CategoriesModel;
import com.stark.quizzer.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
   static private List<CategoriesModel> ctitles;
     private onItemClickListner mlistner;
     private int sets;


    public  interface onItemClickListner
 {


     void onClick(int position, String title,int sets);


 }


 public void setOnItemClickListner(onItemClickListner listner)
 {
     mlistner=listner;
 }

    public RecyclerViewAdapter(List<CategoriesModel> categories) {
        ctitles = categories;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categoris,viewGroup,false);
       MyViewHolder holder=new MyViewHolder(v,mlistner);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
     String title=ctitles.get(i).getName();

     myViewHolder.textView.setText(title);
        Picasso.get().load(ctitles.get(i).getImage()).resize(290,290).into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return ctitles.size() ;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageView;
        TextView textView;



        public MyViewHolder(@NonNull final View itemView, final onItemClickListner listner)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.circleimage);
            textView=itemView.findViewById(R.id.ctext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION)
                        {
                            listner.onClick(position,ctitles.get(getAdapterPosition()).getName(),ctitles.get(getAdapterPosition()).getSets().size());
                        }
                    }
                      }
            });
        }
    }


}
