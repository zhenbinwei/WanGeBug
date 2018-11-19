package com.example.weizhenbin.wangebug.modules.code.adapters

import android.content.Context
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean
import com.example.weizhenbin.wangebug.modules.code.uis.CodeSystemTreeActivity
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Created by weizhenbin on 2018/8/30.
 */

class CodeSystemTreeAdapter(context: Context?, data: List<SystemTreeDataBean.DataBean>?) : BaseSimpleAdapter<SystemTreeDataBean.DataBean, BaseViewHolder>(context, R.layout.system_tree_list_item, data) {

    override fun convert(helper: BaseViewHolder, item: SystemTreeDataBean.DataBean) {
        helper.setText(R.id.tv_tree_name, item.name)
        val tagFlowLayout = helper.getView<TagFlowLayout>(R.id.tfl_tree_datas)
        tagFlowLayout.adapter = CodeSystemTreeFlowAdapter(item.children)
        tagFlowLayout.setOnTagClickListener { _, position, parent ->
            CodeSystemTreeActivity.startActivity(parent.context, item, position)
            true
        }
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

  /*  private inner class MyClickableSpan(internal var position: Int, internal var str: String, internal var startIndex: Int, internal var endIndex: Int) : ClickableSpan() {
         var item: SystemTreeDataBean.DataBean? = null

       *//* fun setItem(item: SystemTreeDataBean.DataBean) {
            this.item = item
        }*//*

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = App.app.resources.getColor(R.color.colorGray5)
        }

        override fun onClick(widget: View) {

            CodeSystemTreeActivity.startActivity(widget.context, item, position)
        }
    }*/
}
