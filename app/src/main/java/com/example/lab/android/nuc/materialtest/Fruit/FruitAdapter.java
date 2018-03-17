package com.example.lab.android.nuc.materialtest.Fruit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.materialtest.Activity.FruitActivity;
import com.example.lab.android.nuc.materialtest.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {


    private Context mContext;

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        ImageView fruitView;

        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            fruitView= (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);


        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext,FruitActivity.class);
                //将点击到的水果数据传送到每个水果点击开的Activity
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());

        //使用Glide来加载图片
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitView);
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}
