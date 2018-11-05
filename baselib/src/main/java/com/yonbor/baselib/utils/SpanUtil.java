package com.yonbor.baselib.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2017/5/9.
 * SpannableString
 */

public class SpanUtil {
      /*
     * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式
     * Spannable.SPAN_EXCLUSIVE_INCLUSIVE ：前面不包括，后面包括。即仅在范围字符的后面插入新字符时会应用新样式
     * Spannable.SPAN_INCLUSIVE_EXCLUSIVE ：前面包括，后面不包括。
     * Spannable.SPAN_INCLUSIVE_INCLUSIVE ：前后都包括。
     */
      
      public static SpannableString getSS(String value, String target, int color){
          if(value == null) value = "";
          SpannableString sp = new SpannableString(value);
          if(TextUtils.isEmpty(value) || TextUtils.isEmpty(target)) return sp;
          
//          int index = value.indexOf(target);
//          if(index != -1) {
//              ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
//              sp.setSpan(colorSpan, index, index + target.length(), 
//                      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//          }
          
          ArrayList<Integer> indexList = matchIndexs(value, target, true);
          if(indexList != null && !indexList.isEmpty()){
              for(int indexOf : indexList) {
                  ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                  sp.setSpan(colorSpan, indexOf, indexOf + target.length(),
                          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
              }
          }
          return sp;
      }


    /**
     * 匹配获取所有匹配结果位置
     * @param content
     * @param key
     * @param isCase 忽略大小写
     * @return
     */
    public static ArrayList<Integer> matchIndexs(String content, String key, boolean isCase){
        ArrayList<Integer> list = new ArrayList<>();
        if(content == null || key == null) return list;
        String wordReg = isCase ? "(?i)" + key : key;//用(?i)来忽略大小写
        Matcher matcher = Pattern.compile(wordReg).matcher(content);
        while (matcher.find()) {
            list.add(matcher.start());
        }
        return list;
    }
    
}
