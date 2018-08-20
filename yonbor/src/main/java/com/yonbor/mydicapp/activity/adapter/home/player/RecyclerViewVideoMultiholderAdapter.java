package com.yonbor.mydicapp.activity.adapter.home.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.ConstantsHttp;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/20 15:51
 */
public class RecyclerViewVideoMultiholderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public int getItemViewType(int position) {
        if (position != 4 && position != 7) {
            return 0; // 非视频
        }
        return 1; // 视频
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videoview,parent,false);
            return new VideoHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textview,parent,false);
            return  new TextHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position != 4 && position != 7) {
            TextHolder textHolder = (TextHolder) holder;
        } else {
            VideoHolder videoHolder = (VideoHolder) holder;
            videoHolder.jzVideoPlayerStandard.setUp(ConstantsHttp.videoUrls[0][position], JZVideoPlayer.SCREEN_WINDOW_LIST,ConstantsHttp.videoTitles[0][position]);
            videoHolder.jzVideoPlayerStandard.positionInList = position;
            Glide.with(videoHolder.jzVideoPlayerStandard.getContext()).load(ConstantsHttp.videoThumbs[0][position]).into(videoHolder.jzVideoPlayerStandard.thumbImageView);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {

        private JZVideoPlayerStandard jzVideoPlayerStandard;

        public VideoHolder(View itemView) {
            super(itemView);
            jzVideoPlayerStandard = itemView.findViewById(R.id.videoplayer);
        }
    }

    public static class TextHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public TextHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }




}
