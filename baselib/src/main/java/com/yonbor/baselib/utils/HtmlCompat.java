package com.yonbor.baselib.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by Administrator on 2017/8/22.
 */

public class HtmlCompat {
    public static Spanned fromHtml(String source){
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        }else{
            return Html.fromHtml(source);
        }
    }
}
