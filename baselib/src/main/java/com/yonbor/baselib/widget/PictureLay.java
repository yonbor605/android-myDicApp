package com.yonbor.baselib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.yonbor.baselib.R;
import com.yonbor.baselib.utils.DensityUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/15.
 */

public class PictureLay extends LinearLineWrapLayout {

    private static final int TAG_HAS_PIC = R.id.tag_has_pic;//是否有图片标记
    private static final int TAG_INDEX = R.id.tag_index;//位置标记
    
    Context context;
    int maxNum;//最大数量
    int picMargin;//图片间距
    int picHeight;
    int picWidth;
    
    int addDrawable;
    int delDrawable;
    Listener listener;

    ArrayList<ImageView> imageViews = new ArrayList<>();
    ArrayList<ImageView> deleteViews = new ArrayList<>();
    SparseArray<String> localPathMap = new SparseArray();//压缩后图片完整路径,用于上传
    SparseArray<Uri> localUriMap = new SparseArray();//原图片uri，用于判断图片是否重复

    ImageView curPicView;
    ImageView curDelView;
    int mIndex;//位置
    
    public PictureLay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initDefault();
    }

    public PictureLay(Context context) {
        super(context);
        this.context = context;
        initDefault();
    }

    private void initDefault() {
        maxNum = 3;
        picMargin = DensityUtil.dip2px(5);
        picHeight = DensityUtil.dip2px(60);
        picWidth = DensityUtil.dip2px(60);
    }
    
    public ImageView getCurPicView(){
        return curPicView;
    }

    public ImageView getCurDelView() {
        return curDelView;
    }

    public void setDefault(int addDrawable, int delDrawable, Listener listener){
        this.addDrawable = addDrawable;
        this.delDrawable = delDrawable;
        this.listener = listener;
    }
    public void setDefault(int maxNum, int picMargin, int picHeight, int picWidth, int addDrawable, int delDrawable, Listener listener){
        this.maxNum = maxNum;
        this.picMargin = picMargin;
        this.picHeight = picHeight;
        this.picWidth = picWidth;
        this.addDrawable = addDrawable;
        this.delDrawable = delDrawable;
        this.listener = listener;
    }

    public void addDefPic(){
        RelativeLayout layout = new RelativeLayout(context);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                picWidth, picHeight);
        LinearLineWrapLayout.LayoutParams layoutParams = new LinearLineWrapLayout.LayoutParams(
                picWidth, picHeight);
        layoutParams.setMargins(picMargin, 0, 0, 0);
        layout.setLayoutParams(layoutParams);

        final ImageView picView = new ImageView(context);
        picView.setTag(TAG_INDEX, mIndex++);
        picView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RelativeLayout.LayoutParams lpPic = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpPic.addRule(RelativeLayout.CENTER_IN_PARENT);
        int padding = DensityUtil.dip2px(10);
        lpPic.width = picWidth - padding;
        lpPic.height = picHeight - padding;
        picView.setLayoutParams(lpPic);
        picView.setImageResource(addDrawable);
        picView.setTag(TAG_HAS_PIC, false);
        layout.addView(picView);
        imageViews.add(picView);
        
        //删除按钮
        final ImageView delView = new ImageView(context);
        delView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RelativeLayout.LayoutParams lpEel=new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpEel.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpEel.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        delView.setLayoutParams(lpEel);
        delView.setImageResource(delDrawable);
        delView.setVisibility(View.GONE);
        layout.addView(delView);
        deleteViews.add(delView);


        picView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curPicView = picView;
                curDelView = delView;
                if(listener == null) return;
                listener.onPicClick(getSelf(), picView, delView);
            }
        });
        delView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                curPicView = picView;
                curDelView = delView;
                if(listener == null) return;
                listener.onDelClick(getSelf(), picView, delView);
            }
        });
        this.addView(layout);
    }

    /**
     * 设置选择当前imageview回来的图片
     * @param pic
     * @param path 压缩后图片完整路径,用于上传
     * @param uri 原图片uri，用于判断图片是否重复
     */
    public void setPic(Bitmap pic, String path, Uri uri){
        if(curPicView == null || curDelView == null) return;
        int indexOf = (int) curPicView.getTag(TAG_INDEX);
        if(indexOf == -1) return;
        curPicView.setImageBitmap(pic);
        curPicView.setTag(TAG_HAS_PIC, true);
        curDelView.setVisibility(View.VISIBLE);
        localPathMap.put(indexOf, path);
        localUriMap.put(indexOf, uri);
        if(this.getChildCount() < maxNum){
            addDefPic();
        }
    }
    
    /**
     * 删除当前imageview图片
     */
    public void deletePic(){
        if(curPicView == null || curDelView == null) return;
        int viewIndex = imageViews.indexOf(curPicView);
        int indexOf = (int) curPicView.getTag(TAG_INDEX);
        if(viewIndex == -1) return;
        imageViews.remove(curPicView);
        localPathMap.remove(indexOf);
        localUriMap.remove(indexOf);
        this.removeViewAt(viewIndex);
        recycleBitmap(curPicView);
        if(endPicHasData()){
            addDefPic();
        }
        
    }

    /**
     * 判断当前imageview是否有数据
     * @return
     */
    public boolean hasCurData(){
        if(curPicView == null || curDelView == null) return false;
        
        return (boolean) curPicView.getTag(TAG_HAS_PIC);
    }

    /**
     * 判断是否重复
     * @param uri
     * @return
     */
    public boolean isRepeat(Uri uri){

       if(uri == null) return false;
        
        for(int i = 0; i < localUriMap.size(); i++){
            if(TextUtils.equals(localUriMap.valueAt(i).toString(), uri.toString())){
                return true;
            }
        }
        
        return false;
    }

    /**
     * 获取本地图片路径
     * @return
     */
    public ArrayList<String> getLocalPaths(){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < localPathMap.size(); i++){
            list.add(localPathMap.valueAt(i));
        }
        return list;
    }
    
    public void clear(){
        for(ImageView imageView : imageViews){
            if(imageView != imageViews.get(imageViews.size()-1))
                recycleBitmap(imageView);
        }
    }
    
    private void recycleBitmap(ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        if (null != bmp && !bmp.isRecycled()){
            bmp.recycle();
            bmp = null;
        }
    }
    
    private PictureLay getSelf(){
        return this;
    }

    public int getPictureCount() {
        if(endPicHasData()){
            return imageViews.size();
        }
        return imageViews.size() - 1;
    }

    /**
     * 判断最后一个imageview是否选择了图片
     * @return
     */
    private boolean endPicHasData(){
        return !imageViews.isEmpty() && (Boolean) imageViews.get(imageViews.size()-1).getTag(TAG_HAS_PIC);
    }

    public int getPicWidth() {
        return picWidth;
    }

    public interface Listener{
        void onPicClick(PictureLay pictureLay, ImageView picView, ImageView delView);
        void onDelClick(PictureLay pictureLay, ImageView picView, ImageView delView);
    }
}
