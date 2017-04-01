package com.example.wxx.apostil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by wxx on 2017/2/28.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private Context mContext;
    private List<HashMap<String,String>> mapList;

    public SimpleAdapter(Context mContext, List<HashMap<String, String>> mapList) {
        this.mContext = mContext;
        this.mapList = mapList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleText;
        public TextView dateText;
        public TextView contentText;
        public CardView cardView;
        public ViewHolder(View v){
            super(v);
            titleText = (TextView) v.findViewById(R.id.title);
            dateText = (TextView) v.findViewById(R.id.date);
            contentText = (TextView) v.findViewById(R.id.content);
            cardView= (CardView) v.findViewById(R.id.card_view);
        }
    }
    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_main,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SimpleAdapter.ViewHolder holder, int position) {
        
        final int i=position;
        holder.titleText.setText(mapList.get(position).get(MyConstants.TITLE));
        holder.dateText.setText(mapList.get(position).get(MyConstants.DATE));
        holder.contentText.setText(mapList.get(position).get(MyConstants.CONTENT));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,DetailActivity.class);
                intent.putExtra(MyConstants.TITLE,mapList.get(i).get(MyConstants.TITLE));
                intent.putExtra(MyConstants.DATE,mapList.get(i).get(MyConstants.DATE));
                intent.putExtra(MyConstants.CONTENT,mapList.get(i).get(MyConstants.CONTENT));
                intent.putExtra(MyConstants.ID,mapList.get(i).get(MyConstants.ID));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapList == null?0:mapList.size();
    }
}
