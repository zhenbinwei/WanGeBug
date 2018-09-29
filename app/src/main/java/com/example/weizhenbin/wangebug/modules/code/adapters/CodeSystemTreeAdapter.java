package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter;
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean;
import com.example.weizhenbin.wangebug.modules.code.uis.CodeSystemTreeActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/30.
 */

public class CodeSystemTreeAdapter extends BaseSimpleAdapter<SystemTreeDataBean.DataBean,BaseViewHolder> {


    public CodeSystemTreeAdapter(@Nullable Context context,@Nullable List<SystemTreeDataBean.DataBean> data) {
        super(context,R.layout.system_tree_list_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SystemTreeDataBean.DataBean item) {
        helper.setText(R.id.tv_tree_name,item.getName());
        TagFlowLayout tagFlowLayout=helper.getView(R.id.tfl_tree_datas);
        tagFlowLayout.setAdapter(new CodeSystemTreeFlowAdapter(item.getChildren()));
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                CodeSystemTreeActivity.startActivity(parent.getContext(),item,position);
                return true;
            }
        });
       /* final TextView tvTags=helper.getView(R.id.tv_tags);
        tvTags.setText("");
        final List<SystemTreeDataBean.DataBean.ChildrenBean> childrenBeen= item.getChildren();
        tvTags.post(new Runnable() {
            @Override
            public void run() {
                int lineWidth=0;
                //多加一个字的宽度 避开恰好到了text自动换行 变成了双换行\
                for (int i = 0; i < childrenBeen.size(); i++) {
                    String s=childrenBeen.get(i).getName();
                    if ((lineWidth+tvTags.getPaint().measureText(s+"字"))>tvTags.getWidth()){
                        lineWidth= 0;
                        tvTags.append("\n");
                    }
                    MyClickableSpan span=new MyClickableSpan(i,s,0,s.length());
                    span.setItem(item);
                    SpannableString spannableString=new SpannableString(s);
                    spannableString.setSpan(span,span.startIndex,span.endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvTags.append(spannableString);
                    tvTags.append("  ");
                    lineWidth+=tvTags.getPaint().measureText(s+"  ");
                }
                tvTags.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });*/
    }


    private class MyClickableSpan extends ClickableSpan {
        int position;

        String str;

        int startIndex;

        int endIndex;
        SystemTreeDataBean.DataBean item;
        public MyClickableSpan(int position, String str, int startIndex, int endIndex) {
            this.position = position;
            this.str = str;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public void setItem(SystemTreeDataBean.DataBean item) {
            this.item = item;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(App.app.getResources().getColor(R.color.colorGray5));
        }

        @Override
        public void onClick(View widget) {

            CodeSystemTreeActivity.startActivity(widget.getContext(),item,position);
        }
    }
}
