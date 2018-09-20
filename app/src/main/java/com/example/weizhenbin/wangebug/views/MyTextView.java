package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by weizhenbin on 2018/9/20.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
  /*  @Override
    public void setText(CharSequence text, BufferType type) {


        //空格拆分 换行重组
        Log.d("MyTextView", "text:" + text);
        StringBuilder stringBuilder=new StringBuilder();

        String[] strings=getStrings(text.toString());

        int lineWidth=0;
        //多加一个字的宽度 避开恰好到了text自动换行 变成了双换行
        for (String s: strings) {
            if ((lineWidth+getPaint().measureText(s+"字"))>getWidth()){
               lineWidth= 0;
               stringBuilder.append("\n");
            }
            stringBuilder.append(s).append(" ");
            lineWidth+=getPaint().measureText(s+" ");
        }

        SpannableString spannableString = new SpannableString(stringBuilder);

        int startIndex=0;
        for (int i = 0; i < strings.length; i++) {
            MyClickableSpan span=new MyClickableSpan(i,strings[i],startIndex,startIndex+strings[i].length());
            startIndex=startIndex+strings[i].length()+1;
            spannableString.setSpan(span,span.startIndex,span.endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setMovementMethod(LinkMovementMethod.getInstance());

        super.setText(spannableString, type);
    }*/


    private class MyClickableSpan extends ClickableSpan{
        int position;

        String str;

        int startIndex;

        int endIndex;

        public MyClickableSpan(int position, String str, int startIndex, int endIndex) {
            this.position = position;
            this.str = str;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void onClick(View widget) {

        }
    }



    private String[] getStrings(String text){
      return  text.split(" ");
    }



}
