package com.yonbor.baselib.recyclerviewadapter.wrapper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonbor.baselib.R;


/**
 * Created by Administrator on 2016/11/29.
 */
public class LoadMoreView extends LinearLayout {
    public static final int LOAD = 1;
    public static final int ERROR = 2;
    public static final int END = 3;
    public static final int NOR = 4;
    private static final int ERROR_CLICK = 5;
    
    public int curState = NOR;
    Context context;
    View mainView;

    public TextView getTvLoading() {
        return tvLoading;
    }

    public TextView getTvError() {
        return tvError;
    }

    public TextView getTvEnd() {
        return tvEnd;
    }

    public View getLayLoading() {
        return layLoading;
    }

    public View getLayError() {
        return layError;
    }

    public View getLayEnd() {
        return layEnd;
    }

    TextView tvLoading, tvError, tvEnd;
    View layLoading, layError, layEnd;
    
    
    public LoadMoreView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(R.layout.view_load_more, null, false);
        
        tvLoading = (TextView) mainView.findViewById(R.id.tvLoading);
        tvError = (TextView) mainView.findViewById(R.id.tvError);
        tvEnd = (TextView) mainView.findViewById(R.id.tvEnd);
        
        layLoading = mainView.findViewById(R.id.layLoading);
        layError = mainView.findViewById(R.id.layError);
        layEnd = mainView.findViewById(R.id.layEnd);
        
        layLoading.setVisibility(View.GONE);
        layError.setVisibility(View.GONE);
        layEnd.setVisibility(View.GONE);
        
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mainView, layoutParams);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
    }

    public void setErrorListener(final OnErrorListener listener) {
        if (listener != null)
            layError.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setState(ERROR_CLICK);
                    listener.onErrorListener();
                }
            });
    }
    
    public void setState(int state){
        if(curState == state) return;
        switch (state){
            case LOAD:
                layLoading.setVisibility(View.VISIBLE);
                layError.setVisibility(View.GONE);
                layEnd.setVisibility(View.GONE);
                curState = LOAD;
                break;
            case ERROR_CLICK:
                layLoading.setVisibility(View.VISIBLE);
                layError.setVisibility(View.GONE);
                layEnd.setVisibility(View.GONE);
                curState = ERROR_CLICK;
                break;
            case ERROR:
                layLoading.setVisibility(View.GONE);
                layError.setVisibility(View.VISIBLE);
                layEnd.setVisibility(View.GONE);
                curState = ERROR;
                break;
            case END:
                layLoading.setVisibility(View.GONE);
                layError.setVisibility(View.GONE);
                layEnd.setVisibility(View.VISIBLE);
                curState = END;
                break;
            default:
                curState = NOR;
                break;
        }
    }
    
    public boolean canLoad(){
        return curState == LOAD;
    }
    
    public interface OnErrorListener{
        void onErrorListener();
    }
    
    
    


}
