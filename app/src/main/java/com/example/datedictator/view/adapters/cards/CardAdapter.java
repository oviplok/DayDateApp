package com.example.datedictator.view.adapters.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.datedictator.R;
import com.example.datedictator.repository.model.Card;

import java.util.List;



public class CardAdapter extends ArrayAdapter<Card> {
    private Context context;


    public CardAdapter(Context context, int resourceId, List<Card> items){
        super(context, resourceId, items);

    }
    public View getView(int position, View convertView, ViewGroup parent){
        Card card_item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        TextView textView = convertView.findViewById(R.id.helloText);
        ImageView image = convertView.findViewById(R.id.profileImageView);

        textView.setText(card_item.getName());
        Glide.with(getContext()).load(card_item.getProfileImageUrl()).into(image);
        //image.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
