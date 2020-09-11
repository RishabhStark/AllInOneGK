package com.stark.quizzer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class bookmark extends Fragment {
    public String tag="bookmark";
    public static final String FILE_NAME="QUIZZER";
    public static final String KEY_NAME="QUESTIONS";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    public static List<QuestionModel> bookmarksList;
    private List<QuestionModel> list;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_bookmark,container,false);
        recyclerView= v.findViewById(R.id.bookmaek);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences=getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();
        getBookmarks();
         list=new ArrayList<>(bookmarksList);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final BookmarkAdapter bookmarkAdapter=new BookmarkAdapter(list);
        recyclerView.setAdapter(bookmarkAdapter);
        bookmarkAdapter.setOnDelteListner(new BookmarkAdapter.onDeleteListner() {
            @Override
            public void onCLick(int position, List<QuestionModel> list) {
                list.remove(position);
                Log.d("position","position"+position);
                bookmarkAdapter.notifyItemRemoved(position);
                String json=gson.toJson(list);
                editor.putString(KEY_NAME,json);
                editor.apply();

            }
        });


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

//    @Override
//    public void onPause() {
//        super.onPause();
//        storeBookmarks();
//    }
}
