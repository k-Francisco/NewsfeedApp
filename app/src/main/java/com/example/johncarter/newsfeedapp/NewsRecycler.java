package com.example.johncarter.newsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by john carter on 2/12/2017.
 */

public class NewsRecycler extends RecyclerView.Adapter<NewsRecycler.ViewHolder> {

    private Context mContext;
    private ArrayList<Article> articleArrayList;

    public NewsRecycler(Context mContext, ArrayList<Article> articleArrayList) {
        this.mContext = mContext;
        this.articleArrayList = articleArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(mContext).load(articleArrayList.get(position).getUrlToImage()).into(holder.thumbnail);
        holder.headline.setText(articleArrayList.get(position).getTitle());
        holder.body.setText(articleArrayList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView headline;
        private TextView body;
        private TextView more;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.news_thumbnail);
            headline = (TextView) itemView.findViewById(R.id.tvHeadline);
            body = (TextView) itemView.findViewById(R.id.tvBody);
            more = (TextView) itemView.findViewById(R.id.tvMore);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri newsUri = Uri.parse(articleArrayList.get(getAdapterPosition()).getUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW,newsUri);
                    mContext.startActivity(websiteIntent);
                }
            });


        }
    }
}
