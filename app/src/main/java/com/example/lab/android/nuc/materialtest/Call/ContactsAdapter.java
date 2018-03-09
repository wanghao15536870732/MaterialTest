package com.example.lab.android.nuc.materialtest.Call;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.lab.android.nuc.materialtest.Activity.CallActivity;
import com.example.lab.android.nuc.materialtest.R;

import java.util.List;
import java.util.Random;

/**
 * Created by 王浩 on 2018/3/9.
 */

public class ContactsAdapter extends ArrayAdapter<Contacts>{

    private int resourceId;

    public ContactsAdapter(Context context, int textVIewResourceId, List<Contacts> objects){
        super(context,textVIewResourceId,objects);

        resourceId = textVIewResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contacts contacts = getItem(position);//获取当前项的contacts实例

        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.contactsView = (TextView) view.findViewById(R.id.textView);
            viewHolder.fruitName = (ImageView) view.findViewById(R.id.ImageView);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.contactsView.setText(contacts.getContactName() +"\n" +  contacts.getContactNumber());
        viewHolder.fruitName.setImageResource(contacts.getImageId());
        return view;
    }
    class ViewHolder{
        TextView contactsView;
        ImageView fruitName;
    }

}

