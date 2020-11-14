package com.example.list;


import androidx.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

public class FilmeFlexibleAdapter extends FlexibleAdapter<FilmeViewItem>{

    public FilmeFlexibleAdapter(@Nullable List<FilmeViewItem> items, @Nullable Object listeners) {
        super(items, listeners, true);
    }

    @Override
    public String onCreateBubbleText(int position) {
        FilmeViewItem item = getItem(position);

        if(item != null){
            return item.getTitulo().substring(0, 1).toUpperCase();
        }else{
            return "";
        }
    }
}
