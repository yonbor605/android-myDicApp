package com.yonbor.mydicapp.activity.adapter.home.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yonbor.mydicapp.R;
import com.yonbor.mydicapp.app.ConstantsHttp;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @Description:
 * @Author: YinYongbo
 * @Time: 2018/8/20 11:21
 */
public class RecyclerViewVideoAdapter extends RecyclerView.Adapter<RecyclerViewVideoAdapter.MyViewHolder> {


    private static final String TAG = "RecyclerViewVideoAdapte";
    private int[] videoIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Context context;

    public RecyclerViewVideoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewVideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_videoview, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewVideoAdapter.MyViewHolder holder, int position) {
        holder.jzVideoPlayerStandard.setUp(ConstantsHttp.videoUrls[0][position], JZVideoPlayer.SCREEN_WINDOW_LIST, ConstantsHttp.videoTitles[0][position]);
        Glide.with(holder.jzVideoPlayerStandard.getContext()).load(ConstantsHttp.videoThumbs[0][position]).into(holder.jzVideoPlayerStandard.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return videoIndex.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private JZVideoPlayerStandard jzVideoPlayerStandard;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzVideoPlayerStandard = itemView.findViewById(R.id.videoplayer);
        }
    }
}
